package com.aroha.kams.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aroha.kams.config.AppConfig;
import com.aroha.kams.model.FileDetailsEntity;
import com.aroha.kams.payload.SearchByPayload;
import com.aroha.kams.service.AwsS3Upload;
import com.aroha.kams.service.FileSystemUpload;
import com.aroha.kams.service.UserDBService;

@RestController
@RequestMapping("/api/dropbox/user")
public class UserController {

	@Autowired
	AwsS3Upload awsUpload;
	
	HttpSession session;

	@Autowired
	UserDBService userDBservice;

	@Autowired
	AppConfig appConfig;

	@Autowired
	HttpServletRequest req;

	@Autowired
	FileSystemUpload fileUploadService;

	// UpLoad File
	@PostMapping("addFile")
	public ResponseEntity<?> uploadToLocalFileSystem(@RequestParam("file") MultipartFile file,
			@RequestParam("uploadTo") String uploadTo, @RequestParam("multiTag") String documentTag,
			@RequestParam("eMail") String getEmailId
			) {
		String status = "";
		if (appConfig.getStorageName().equalsIgnoreCase("fileSystem")) {
			status = fileUploadService.uploadFile(file, uploadTo, documentTag);
		} else if (appConfig.getStorageName().equalsIgnoreCase("AwsCloud")) {
			status = awsUpload.uploadFile(file, uploadTo, documentTag,getEmailId);
		}
		return ResponseEntity.ok(status);
	}

	@PostMapping("getFileList")
	public List<FileDetailsEntity> getDocument(@RequestBody String eMail) {
		return userDBservice.getAllFile(eMail);
	}

	@PostMapping("searchByTag")
	public List<FileDetailsEntity> getDocumentByTag(@RequestBody SearchByPayload searchPayload) {
	System.out.println(searchPayload.getSearchByTag()+"   "+searchPayload.geteMail());
		return userDBservice.getAllFileByTag(searchPayload.getSearchByTag(),searchPayload.geteMail());
	}

	@PostMapping("searchByType")
	public List<FileDetailsEntity> getDocumentByType(@RequestBody SearchByPayload searchPayload) {
		System.out.println(searchPayload.getSearchByType()+" "+searchPayload.geteMail());
		return userDBservice.getAllFileByType(searchPayload.getSearchByType(),searchPayload.geteMail());
	}
}
