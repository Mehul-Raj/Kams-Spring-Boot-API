package com.aroha.kams.service;

import java.io.ByteArrayInputStream;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.aroha.kams.config.BeanConfig;
import com.aroha.kams.payload.CompanyPayload;
import com.aroha.kams.payload.DepartmentPayload;
import com.aroha.kams.payload.ProjectPayload;
import com.aroha.kams.payload.TeamPayload;

@Service
public class AwsS3Create {

	static String sufix = "/";
	@Autowired
	BeanConfig config;

	// Create Company Folder Inside AWSCloud
	public boolean createCompany(CompanyPayload companyPayload) {
		boolean status = false;
		String compName = companyPayload.getCompanyName();
		String bucketName = config.getBucketName();
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(0);
			InputStream inputStream = new ByteArrayInputStream(new byte[0]);
			config.awsS3Client().putObject(bucketName, compName + sufix, inputStream, metadata);
			status = true;
		} catch (Exception ex) {
			status = false;
		}
		return status;
	}

	// Create Department Folder Inside AWSCloud
	public boolean createDepartment(DepartmentPayload departmentPayload) {
		boolean status = false;
		String compName = departmentPayload.getCompanyName();
		String deptName = departmentPayload.getDepartmentName();
		String bucketName = config.getBucketName();
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(0);
			InputStream inputStream = new ByteArrayInputStream(new byte[0]);
			config.awsS3Client().putObject(bucketName, compName + sufix + deptName + sufix, inputStream, metadata);
			status = true;
		} catch (Exception ex) {
			status = false;
		}
		return status;
	}

	// create Project Folder Inside AWSCloud
	public boolean createProject(ProjectPayload projectPayload) {
		boolean status = false;
		String compName = projectPayload.getCompanyName();
		String deptName = projectPayload.getDepartmentName();
		String projName = projectPayload.getProjectName();
		String bucketName = config.getBucketName();
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(0);
			InputStream inputStream = new ByteArrayInputStream(new byte[0]);
			config.awsS3Client().putObject(bucketName, compName + sufix + deptName + sufix + projName + sufix,
					inputStream, metadata);
			status = true;
		} catch (Exception ex) {
			status = false;
		}
		return status;
	}

	// create Team Folder Inside AWSCloud
	public boolean createTeam(TeamPayload teamPayload) {
		boolean status = false;
		String compName = teamPayload.getCompanyName();
		String deptName = teamPayload.getDepartmentName();
		String projName = teamPayload.getProjectName();
		String teamName = teamPayload.getTeamName();
		String bucketName = config.getBucketName();
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(0);
			InputStream inputStream = new ByteArrayInputStream(new byte[0]);
			config.awsS3Client().putObject(bucketName,
					compName + sufix + deptName + sufix + projName + sufix + teamName + sufix, inputStream, metadata);
			status = true;
		} catch (Exception ex) {
			status = false;
		}
		return status;
	}

}
