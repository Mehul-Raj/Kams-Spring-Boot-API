package com.aroha.kams.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aroha.kams.config.AppConfig;
import com.aroha.kams.model.CompanyEntity;
import com.aroha.kams.model.DepartmentEntity;
import com.aroha.kams.model.ProjectEntity;
import com.aroha.kams.model.TeamEntity;
import com.aroha.kams.payload.CompanyPayload;
import com.aroha.kams.payload.DepartmentPayload;
import com.aroha.kams.payload.ProjectPayload;
import com.aroha.kams.payload.TeamPayload;
import com.aroha.kams.payload.UserPayload;
import com.aroha.kams.repository.CompanyRepository;
import com.aroha.kams.repository.DepartmentRepository;
import com.aroha.kams.repository.ProjectRepository;
import com.aroha.kams.repository.TeamRepository;
import com.aroha.kams.repository.UserRepository;

@Service
public class AdminService {

	@Autowired
	AppConfig appConfig;

	@Autowired
	FileSystemCreate fileSystemCreateService;

	@Autowired
	AwsS3Create awsCreateService;

	@Autowired
	AdminDBService adminDBService;

	@Autowired
	SendEmailService sendEmailService;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	UserRepository userRepository;

	// Find Company
	public boolean findCompany(String companyName) {
		return companyRepository.existsBycompanyName(companyName);
	}

	// Check Department exits Or Not
	public boolean findDepartment(DepartmentPayload departmentPayload) {
		boolean status = false;
		boolean companyExist = departmentRepository.existsBycompanyName(departmentPayload.getCompanyName());
		boolean departmentExist = departmentRepository.existsBydepartmentName(departmentPayload.getDepartmentName());
		if (companyExist == true && departmentExist == true) {
			status = true;
		}
		return status;
	}

	// Find Project
	public boolean findProject(ProjectPayload projectPayload) {
		boolean status = false;
		boolean companyExist = projectRepository.existsBycompanyName(projectPayload.getCompanyName());
		boolean departmentExist = projectRepository.existsBydepartmentName(projectPayload.getDepartmentName());
		boolean projectExist = projectRepository.existsByprojectName(projectPayload.getProjectName());
		if (companyExist == true && departmentExist == true && projectExist == true) {
			status = true;
		}
		return status;
	}

	// Find Team
	public boolean findTeam(TeamPayload teamPayload) {
		boolean status = false;
		boolean companyExist = teamRepository.existsBycompanyName(teamPayload.getCompanyName());
		boolean departmentExist = teamRepository.existsBydepartmentName(teamPayload.getDepartmentName());
		boolean projectExist = teamRepository.existsByprojectName(teamPayload.getProjectName());
		boolean teamExist = teamRepository.existsByteamName(teamPayload.getTeamName());
		if (companyExist == true && departmentExist == true && projectExist == true && teamExist == true) {
			status = true;
		}
		return status;
	}

	// Find User
	public boolean findUser(UserPayload userPayload) {
		return userRepository.existsByeMail(userPayload.geteMail());
	}

	// Get All CompanyName
	public List<CompanyEntity> findAllCompany() {
		return companyRepository.findAll();
	}
	
	// Get All Department Name Based On CompanyName
	public List<DepartmentEntity> findAllDepartment(String companyName) {		
		return departmentRepository.findBycompanyName(companyName);
	}
	
	//Get All Project Name Based On CompanyName And Department Name
	public List<ProjectEntity> findAllProject(String companyName, String departmentName) {
		return projectRepository.findAllByCompanyNameAndDepartmentName(companyName,departmentName);
	}
	
	//Get All Project Name Based On CompanyName And Department Name
		public List<TeamEntity> findAllTeam(String companyName, String departmentName,String projectName) {
			return teamRepository.findAllByCompanyNameAndDepartmentNameAndProjectName(companyName,departmentName,projectName);
		}

	// Create User
	public UserPayload createUser(UserPayload userPayload) {
		UserPayload isUserCreated = adminDBService.createUser(userPayload);
		return isUserCreated;
	}

	// Create Company
	public CompanyPayload createCompany(CompanyPayload companyPayload) {
		// Create Company In DB
		CompanyPayload isCompanyCreated = adminDBService.createCompany(companyPayload);

		if (isCompanyCreated.getStatus().equalsIgnoreCase("Success")) {
			// Create Company In File System
			if (appConfig.getStorageName().equalsIgnoreCase("fileSystem")) {
				boolean status = fileSystemCreateService.createCompany(companyPayload);
				if (status) {
					companyPayload.setMsg("Company Created In FileSystem");
					companyPayload.setStatus("Success");
				} else {
					companyPayload.setMsg("Company Not Created In FileSystem");
					companyPayload.setStatus("Error");
				}
			}

			// Create Company In Cloud
			else if (appConfig.getStorageName().equalsIgnoreCase("AwsCloud")) {
				boolean status = awsCreateService.createCompany(companyPayload);
				if (status) {
					companyPayload.setMsg("Company Created");
					companyPayload.setStatus("Success");
				} else {
					companyPayload.setMsg("Company Not Created");
					companyPayload.setStatus("Error");
				}
			}
		} else {
			companyPayload.setMsg("Company Created");
			companyPayload.setStatus("Error");

		}
		return companyPayload;
	}

	// Create Department
	public DepartmentPayload createDepartment(DepartmentPayload departmentPayload) {

		// Create Department In Db
		DepartmentPayload isDepartmentCreated = adminDBService.createDepartment(departmentPayload);

		if (isDepartmentCreated.getStatus().equalsIgnoreCase("Success")) {
			// create Department In FileSystem
			if (appConfig.getStorageName().equalsIgnoreCase("fileSystem")) {
				boolean status = fileSystemCreateService.createDepartment(departmentPayload);
				if (status) {
					departmentPayload.setMsg("Department Created In FileSystem");
					departmentPayload.setStatus("Success");
				} else {
					departmentPayload.setMsg("Department Not Created In FileSystem");
					departmentPayload.setStatus("Error");
				}
			}
			// Create Company In Cloud
			else if (appConfig.getStorageName().equalsIgnoreCase("AwsCloud")) {
				boolean status = awsCreateService.createDepartment(departmentPayload);
				if (status) {
					departmentPayload.setMsg("Department Created In Cloud");
					departmentPayload.setStatus("Success");
				} else {
					departmentPayload.setMsg("Department Not Created In Cloud");
					departmentPayload.setStatus("Error");
				}
			}
		} else {
			departmentPayload.setMsg("Department Not Created In Cloud");
			departmentPayload.setStatus("Error");
		}
		return departmentPayload;
	}

	// Create Project
	public ProjectPayload createProject(ProjectPayload projectPayload) {

		// Create Project In DataBase
		ProjectPayload isProjectCreated = adminDBService.createProject(projectPayload);

		if (isProjectCreated.getStatus().equalsIgnoreCase("Success")) {

			// create Project In FileSystem
			if (appConfig.getStorageName().equalsIgnoreCase("fileSystem")) {
				boolean status = fileSystemCreateService.createProject(projectPayload);
				if (status) {
					projectPayload.setMsg("Project Created In FileSystem");
					projectPayload.setStatus("Success");
				} else {
					projectPayload.setMsg("Project Not Created In FileSystem");
					projectPayload.setStatus("Error");
				}
			}
			// Create Project In Cloud
			else if (appConfig.getStorageName().equalsIgnoreCase("AwsCloud")) {
				boolean status = awsCreateService.createProject(projectPayload);
				if (status) {
					projectPayload.setMsg("Project Created In Cloud");
					projectPayload.setStatus("Success");
				} else {
					projectPayload.setMsg("Project Not Created In Cloud");
					projectPayload.setStatus("Error");
				}
			}
		} else {
			projectPayload.setMsg("Project Not Created");
			projectPayload.setStatus("Error");
		}
		return projectPayload;
	}

	// Create Team
	public TeamPayload createTeam(TeamPayload teamPayload) {
		TeamPayload isTeamCreated = adminDBService.createTeam(teamPayload);
		if (isTeamCreated.getStatus().equalsIgnoreCase("Success")) {
			// create Project In FileSystem
			if (appConfig.getStorageName().equalsIgnoreCase("fileSystem")) {
				boolean status = fileSystemCreateService.createTeam(teamPayload);
				if (status) {
					teamPayload.setMsg("Project Created In FileSystem");
					teamPayload.setStatus("Success");
				} else {
					teamPayload.setMsg("Project Not Created In FileSystem");
					teamPayload.setStatus("Error");
				}
			}
			// Create Project In Cloud
			else if (appConfig.getStorageName().equalsIgnoreCase("AwsCloud")) {
				boolean status = awsCreateService.createTeam(teamPayload);
				if (status) {
					teamPayload.setMsg("Project Created In Cloud");
					teamPayload.setStatus("Success");
				} else {
					teamPayload.setMsg("Project Not Created In Cloud");
					teamPayload.setStatus("Error");
				}
			}
		} else {
			teamPayload.setMsg("Project Not Created");
			teamPayload.setStatus("Error");
		}
		return teamPayload;
	}	
}
