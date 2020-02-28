package com.aroha.kams.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.aroha.kams.config.AppConfig;
import com.aroha.kams.exception.FileStorageException;
import com.aroha.kams.model.FileDetailsEntity;
import com.aroha.kams.model.UserEntity;
import com.aroha.kams.repository.UserRepository;

@Service
public class FileSystemUpload {

	@Autowired
	HttpSession session;

	@Autowired
	UserDBService userDBservice;
	
	@Autowired
	UserRepository userRepository;

	FileDetailsEntity fileDetails;

	@Autowired
	AppConfig appConfig;

	public String uploadFile(MultipartFile file, String uploadTo, String documentTag,String getEmailId) {
		UserEntity getUser = (UserEntity) userRepository.findByeMail(getEmailId);
		String userName = getUser.getUserName();
		String companyFolder = getUser.getUserCompany();
		String departmentFolder = getUser.getUserdepartment();
		String projectFolder = getUser.getUserProjectName();
		String teamFolder = getUser.getUserTeamName();

		String fileBasePath = "";
		// Check where To Upload
		if (uploadTo.equalsIgnoreCase("Company")) {
			fileBasePath = appConfig.getFilePath() + companyFolder + "/";
		} else if (uploadTo.equalsIgnoreCase("Department")) {
			fileBasePath = appConfig.getFilePath() + companyFolder + "/" + departmentFolder + "/";
		} else if (uploadTo.equalsIgnoreCase("Project")) {
			fileBasePath = appConfig.getFilePath() + companyFolder + "/" + departmentFolder + "/" + projectFolder
					+ "/";
		} else if (uploadTo.equalsIgnoreCase("Team")) {
			fileBasePath = appConfig.getFilePath() + companyFolder + "/" + departmentFolder + "/" + projectFolder
					+ "/" + teamFolder + "/";
		}
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = Paths.get(fileBasePath + fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path(fileBasePath)
					.path(fileName)
					.toUriString();

			// save the File Details
			String url = fileBasePath + fileName;
			//String url = "file://" + fileBasePath + fileName;
			saveFileDetails(fileName, fileDownloadUri, uploadTo, documentTag, getUser);
			System.out.println("fileDownloadUri "+fileDownloadUri);
			return fileDownloadUri;

		} catch (FileStorageException ex) {
			String msg = ex.getMessage();
			return msg;
		} catch (IOException e) {
			String msg = e.getMessage();
			return msg;
		}
	}

	public boolean saveFileDetails(String fileName, String url, String uploadTo, String documentTag, UserEntity user) {
		FileDetailsEntity fileDetails = new FileDetailsEntity();
		// Get Current Time
		Date date = new Date();
		long time = date.getTime();
		Timestamp fileUploadTimeDate = new Timestamp(time);
		try {
			fileDetails.setFileName(fileName);
			fileDetails.setFileSize(56.7);
			fileDetails.setFileUploadTime(fileUploadTimeDate);
			fileDetails.setFileUrl(url);
			fileDetails.setUploadBy(user.getUserName());
			fileDetails.setUploadFileTo(uploadTo);
			fileDetails.setTag(documentTag);
			fileDetails.setDocCompany(user.getUserCompany());
			fileDetails.setDocDepartment(user.getUserdepartment());
			fileDetails.setDocProject(user.getUserProjectName());
			fileDetails.setDocTeam(user.getUserTeamName());
			System.out.println("3");
			userDBservice.saveFileDetails(fileDetails);
			return true;
		} catch (Exception ex) {
			return false;
		}

	}
}
