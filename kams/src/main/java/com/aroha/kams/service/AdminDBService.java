package com.aroha.kams.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aroha.kams.model.CompanyEntity;
import com.aroha.kams.model.DepartmentEntity;
import com.aroha.kams.model.ProjectEntity;
import com.aroha.kams.model.TeamEntity;
import com.aroha.kams.model.UserEntity;
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
public class AdminDBService {

	@Autowired
	SendEmailService sendEmailService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	OtpService otpservice;

	// Create Company In DataBase
	public CompanyPayload createCompany(CompanyPayload companyPayload) {
		CompanyEntity company = new CompanyEntity();
		company.setCompanyName(companyPayload.getCompanyName());
		try {
			companyRepository.save(company);
			companyPayload.setStatus("Success");

		} catch (Exception e) {
			companyPayload.setStatus("Error");
		}

		return companyPayload;
	}

	// Create Department In Data Base
	public DepartmentPayload createDepartment(DepartmentPayload departmentPayload) {
		DepartmentEntity department = new DepartmentEntity();
		department.setCompanyName(departmentPayload.getCompanyName());
		department.setDepartmentName(departmentPayload.getDepartmentName());
		try {
			departmentRepository.save(department);
			departmentPayload.setStatus("Success");
		} catch (Exception e) {
			departmentPayload.setStatus("Error");
		}
		return departmentPayload;
	}

	// Create Project In DataBase
	public ProjectPayload createProject(ProjectPayload projectPayload) {
		ProjectEntity project = new ProjectEntity();
		project.setCompanyName(projectPayload.getCompanyName());
		project.setDepartmentName(projectPayload.getDepartmentName());
		project.setProjectName(projectPayload.getProjectName());
		try {
			projectRepository.save(project);
			projectPayload.setStatus("Success");
		} catch (Exception e) {
			projectPayload.setStatus("Error");
		}
		return projectPayload;
	}

	// Create Team In DataBase
	public TeamPayload createTeam(TeamPayload teamPayload) {
		TeamEntity team = new TeamEntity();
		team.setCompanyName(teamPayload.getCompanyName());
		team.setDepartmentName(teamPayload.getDepartmentName());
		team.setProjectName(teamPayload.getProjectName());
		team.setTeamName(teamPayload.getTeamName());

		try {
			teamRepository.save(team);
			teamPayload.setStatus("Success");
		} catch (Exception e) {
			teamPayload.setStatus("Error");
		}
		return teamPayload;
	}

	// Create User In DataBase
	public UserPayload createUser(UserPayload userPayload) {
		UserEntity user = new UserEntity();
		user.setUserCompany(userPayload.getUserCompany());
		user.setUserdepartment(userPayload.getUserdepartment());
		user.setUserProjectName(userPayload.getUserProjectName());
		user.setUserTeamName(userPayload.getUserTeamName());
		user.setUserName(userPayload.getUserName());
		user.setUserPwd(userPayload.getUserPwd());
		user.setUserRole(userPayload.getUserRole());
		user.seteMail(userPayload.geteMail());
		String pwd=otpservice.CreatePwd(userPayload);
		user.setUserPwd(pwd);
		System.out.println("Password Is "+pwd);
		try {
			userRepository.save(user);
			boolean otpstatus = sendEmailService.sendEmail(user);
			if (otpstatus) {
				userPayload.setStatus("Success");
				userPayload.setMsg("User Created Password Sent On Your Registered Email");
				return userPayload;
			} else {
				userPayload.setStatus("Error");
				userPayload.setMsg("Error In Sending Mail");
				return userPayload;
			}
		} catch (Exception e) {
			userPayload.setStatus("Error");
			userPayload.setMsg("User Not Created");
		}
		return userPayload;
	}
}
