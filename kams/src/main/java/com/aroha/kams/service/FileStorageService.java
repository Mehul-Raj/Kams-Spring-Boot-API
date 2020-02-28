package com.aroha.kams.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.aroha.kams.exception.FileStorageException;
import com.aroha.kams.exception.MyFileNotFoundException;
import com.aroha.kams.model.FileDetailsEntity;
import com.aroha.kams.model.UserEntity;
import com.aroha.kams.properties.FileStorageProperties;
import com.aroha.kams.repository.UserRepository;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserDBService userDBservice;

	@Autowired
	public FileStorageService(FileStorageProperties fileStorageProperties) throws FileStorageException {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.");
		}
	}

	public String storeFile(MultipartFile file, String uploadTo, String documentTag, String getEmailId)
			throws FileStorageException {
		UserEntity getUser = (UserEntity) userRepository.findByeMail(getEmailId);
		String userName = getUser.getUserName();
		String companyFolder = getUser.getUserCompany();
		String departmentFolder = getUser.getUserdepartment();
		String projectFolder = getUser.getUserProjectName();
		String teamFolder = getUser.getUserTeamName();

		String fileBasePath = "";
		// Check where To Upload
		if (uploadTo.equalsIgnoreCase("Company")) {
			fileBasePath = companyFolder;
		} else if (uploadTo.equalsIgnoreCase("Department")) {
			fileBasePath = companyFolder + departmentFolder;
		} else if (uploadTo.equalsIgnoreCase("Project")) {
			fileBasePath = companyFolder + departmentFolder + projectFolder;
		} else if (uploadTo.equalsIgnoreCase("Team")) {
			fileBasePath = companyFolder + departmentFolder + projectFolder + teamFolder;
		}
		// Normalize file name
		String fileNameUri = StringUtils.cleanPath(file.getOriginalFilename());
		String tempFileName = fileNameUri;
		fileNameUri = fileBasePath + fileNameUri;
		try {
			// Check if the file's name contains invalid characters
			if (fileNameUri.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileNameUri);
			}
			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileNameUri);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
					.path(fileNameUri).toUriString();
			saveFileDetails(tempFileName, fileDownloadUri, uploadTo, documentTag, getUser, file.getContentType(),
					file.getSize());
			return tempFileName;
			
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + tempFileName + ". Please try again!");
		}
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException("File not found " + fileName, ex);
		}
	}

	public boolean saveFileDetails(String fileName, String url, String uploadTo, String documentTag, UserEntity user,
			String ContentType, Long size) {
		FileDetailsEntity fileDetails = new FileDetailsEntity();
		// Get Current Time
		Date date = new Date();
		long time = date.getTime();
		Timestamp fileUploadTimeDate = new Timestamp(time);
		try {
			fileDetails.setFileName(fileName);
			fileDetails.setFileSize(size/1024);
			fileDetails.setFileUploadTime(fileUploadTimeDate);
			fileDetails.setFileUrl(url);
			fileDetails.setUploadBy(user.getUserName());
			fileDetails.setUploadFileTo(uploadTo);
			fileDetails.setTag(documentTag);
			fileDetails.setDocCompany(user.getUserCompany());
			fileDetails.setDocDepartment(user.getUserdepartment());
			fileDetails.setDocProject(user.getUserProjectName());
			fileDetails.setDocTeam(user.getUserTeamName());
			fileDetails.setStorage("File");
			userDBservice.saveFileDetails(fileDetails);
			return true;
		} catch (Exception ex) {
			return false;
		}

	}
}
