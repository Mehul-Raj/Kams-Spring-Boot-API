package com.aroha.kams.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aroha.kams.config.AppConfig;
import com.aroha.kams.config.BeanConfig;
import com.aroha.kams.payload.CompanyPayload;
import com.aroha.kams.payload.DepartmentPayload;
import com.aroha.kams.payload.ProjectPayload;
import com.aroha.kams.payload.TeamPayload;

@Service
public class FileSystemCreate {

	static String sufix = "/";
	@Autowired
	BeanConfig config;

	@Autowired
	AppConfig appConfig;

	// Create Company Folder
	public boolean createCompany(CompanyPayload companyPayload) {
		boolean status = false;
		String path = appConfig.getFilePath() + companyPayload.getCompanyName();
		File file = new File(path);
		if (!file.exists()) {
			if (file.mkdir()) {
				status = true;
			}
		}
		return status;
	}

	// Create Department Inside Company
	public boolean createDepartment(DepartmentPayload departmentPayload) {
		boolean status = false;
		String path = appConfig.getFilePath() + departmentPayload.getCompanyName() + "/" + departmentPayload.getDepartmentName();
		File file = new File(path);
		if (!file.exists()) {
			if (file.mkdir()) {
				status = true;
			}
		}
		return status;
	}

	// Create Project
	public boolean createProject(ProjectPayload projectPayload) {
		boolean status = false;
		String path = appConfig.getFilePath() + projectPayload.getCompanyName() + "/" + projectPayload.getDepartmentName() + "/"
				+ projectPayload.getProjectName();
		File file = new File(path);
		if (!file.exists()) {
			if (file.mkdir()) {
				status = true;
			}
		}
		return status;
	}

	// Create Team
	public boolean createTeam(TeamPayload teamPayload) {
		boolean status = false;
		String path = appConfig.getFilePath() + teamPayload.getCompanyName() + "/" + teamPayload.getDepartmentName() + "/"
				+ teamPayload.getProjectName() + "/" + teamPayload.getTeamName();
		File file = new File(path);
		if (!file.exists()) {
			if (file.mkdir()) {
				status = true;
			}
		}
		return status;
	}
}
