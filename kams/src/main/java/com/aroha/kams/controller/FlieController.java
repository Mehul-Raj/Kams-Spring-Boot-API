package com.aroha.kams.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.aroha.kams.config.AppConfig;
import com.aroha.kams.exception.FileStorageException;
import com.aroha.kams.payload.UploadFileResponse;
import com.aroha.kams.service.AwsS3Upload;
import com.aroha.kams.service.FileStorageService;
import com.aroha.kams.service.UserDBService;

@RestController
public class FlieController {
	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	AwsS3Upload awsUpload;

	@Autowired
	UserDBService userDBservice;

	@Autowired
	AppConfig appConfig;

	@PostMapping("/uploadFile")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("uploadTo") String uploadTo, @RequestParam("multiTag") String documentTag,
			@RequestParam("eMail") String getEmailId) {
		String fileName = "";
		if (appConfig.getStorageName().equalsIgnoreCase("fileSystem")) {
			try {
				fileName = fileStorageService.storeFile(file, uploadTo, documentTag, getEmailId);
			} catch (FileStorageException e) {
				e.printStackTrace();
			}

		} else if (appConfig.getStorageName().equalsIgnoreCase("AwsCloud")) {
			fileName = awsUpload.uploadFile(file, uploadTo, documentTag, getEmailId);

		}
		return ResponseEntity.ok(fileName);
	}

	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		// Load file as Resource
		Resource resource = fileStorageService.loadFileAsResource(fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			ex.getMessage();
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok(resource);

		/*
		 * return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
		 * .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
		 * resource.getFilename() + "\"") .body(resource);
		 */

	}
}
