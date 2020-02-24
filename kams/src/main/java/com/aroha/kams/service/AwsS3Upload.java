package com.aroha.kams.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.aroha.kams.config.BeanConfig;
import com.aroha.kams.model.FileDetailsEntity;
import com.aroha.kams.model.UserEntity;
import com.aroha.kams.repository.UserRepository;

@Service
public class AwsS3Upload {

	@Autowired
	BeanConfig config;

	@Autowired
	UserDBService userDBService;

	@Autowired
	UserRepository userRepository;

	FileDetailsEntity fileDetails;

	@Value("${amazonProperties.endpointUrl}")
	private String endpointUrl;

	@Value("${aws.bucket.name}")
	private String bucketName;

	static String sufix = "/";

	public String returnDate() {
		// getting current date and time using Date class
		DateTimeFormatter dateTimeFormater = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dateTimeFormater.format(now)); // 2016/11/16 12:08:43

		return dateTimeFormater.format(now);
	}

	// Convert Multipart To File
	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		System.out.println("1");
		return convFile;
	}

	// Get The File Name
	private String generateFileName(MultipartFile multiPart) {
		return multiPart.getOriginalFilename();
	}

	public String uploadFile(MultipartFile multipartFile, String uploadTo, String documentTag,String getEMailId) {
		String status = "";
		try {
			File file = convertMultiPartToFile(multipartFile);
			String fileName = generateFileName(multipartFile);
			status = uploadFileTos3bucket(fileName, file, uploadTo, documentTag,getEMailId);
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	// upload file fore User Role
	public String uploadFileTos3bucket(String fileName, File file, String uploadTo, String documentTag,String getEMailId)
			throws ParseException {
		boolean status = false;
		String fileUrl = "";
		String protocol = "https://";
		System.out.println("4");
		// getting user Date from current Session
		UserEntity getUser = (UserEntity) userRepository.findByeMail(getEMailId);
		String userName = getUser.getUserName();
		String companyFolder = getUser.getUserCompany();
		String departmentFolder = getUser.getUserdepartment();
		String projectFolder = getUser.getUserProjectName();
		String teamFolder = getUser.getUserTeamName();
		String docCompany = getUser.getUserCompany();
		String docDepartment = getUser.getUserdepartment();
		String docProject = getUser.getUserProjectName();
		String docTeam = getUser.getUserTeamName();

		// Upload To Company
		if (uploadTo.equalsIgnoreCase("Company")) {
			try {
				config.awsS3Client().putObject(new PutObjectRequest(bucketName + sufix + companyFolder, fileName, file)
						.withCannedAcl(CannedAccessControlList.PublicRead));
				fileUrl = protocol + endpointUrl + sufix + companyFolder + fileName;
				status = true;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		// Upload To Project
		if (uploadTo.equalsIgnoreCase("Department")) {
			try {
				config.awsS3Client()
						.putObject(new PutObjectRequest(bucketName + sufix + companyFolder + sufix + departmentFolder,
								fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
				fileUrl = protocol + endpointUrl + sufix + companyFolder + sufix + departmentFolder + sufix + fileName;
				status = true;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		// Upload To Department
		if (uploadTo.equalsIgnoreCase("Project")) {
			try {
				config.awsS3Client()
						.putObject(new PutObjectRequest(
								bucketName + sufix + companyFolder + sufix + departmentFolder + sufix + projectFolder,
								fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
				fileUrl = protocol + endpointUrl + sufix + companyFolder + sufix + departmentFolder + sufix
						+ projectFolder + sufix + fileName;
				status = true;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		// Upload To Team
		if (uploadTo.equalsIgnoreCase("Team")) {
			try {
				config.awsS3Client()
						.putObject(new PutObjectRequest(bucketName + sufix + companyFolder + sufix + departmentFolder
								+ sufix + projectFolder + sufix + teamFolder, fileName, file)
										.withCannedAcl(CannedAccessControlList.PublicRead));
				fileUrl = protocol + endpointUrl + sufix + companyFolder + sufix + departmentFolder + sufix
						+ projectFolder + sufix + teamFolder + sufix + fileName;
				status = true;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (status) {
			String fileUploadTimeString = returnDate();
			SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
			java.util.Date parsed = null;
			try {
				parsed = format.parse(fileUploadTimeString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			java.sql.Date fileUploadTimeDate = new java.sql.Date(parsed.getTime());

			/*
			 * String fileUploadTimeString = returnDate(); java.sql.Date fileUploadTimeDate
			 * = (Date) new SimpleDateFormat("dd/M/yyyy").parse(fileUploadTimeString);
			 * 
			 */
			FileDetailsEntity fileDetails = new FileDetailsEntity();
			fileDetails.setFileName(fileName);
			fileDetails.setFileSize(56.7);
			fileDetails.setFileUploadTime(fileUploadTimeDate);
			fileDetails.setFileUrl(fileUrl);
			fileDetails.setUploadBy(userName);
			fileDetails.setUploadFileTo(uploadTo);
			fileDetails.setTag(documentTag);
			fileDetails.setDocCompany(docCompany);
			fileDetails.setDocDepartment(docDepartment);
			fileDetails.setDocProject(docProject);
			fileDetails.setDocTeam(docTeam);
			System.out.println("5");
			System.out.println("fileDetails.getFileUrl()" + fileDetails.getFileUrl());
			userDBService.saveFileDetails(fileDetails);
		}
		System.out.println("6");
		return fileUrl;
	}
}
