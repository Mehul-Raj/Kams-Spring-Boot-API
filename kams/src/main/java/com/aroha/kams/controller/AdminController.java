package com.aroha.kams.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aroha.kams.model.CompanyEntity;
import com.aroha.kams.payload.CompanyPayload;
import com.aroha.kams.payload.DepartmentPayload;
import com.aroha.kams.payload.ProjectPayload;
import com.aroha.kams.payload.TeamPayload;
import com.aroha.kams.payload.UserPayload;
import com.aroha.kams.service.AdminService;

@RestController
@RequestMapping("/api/dropbox/admin")
public class AdminController {
	@Autowired
	AdminService adminService;

	@PostMapping("createCompany")
	public ResponseEntity<?> createCompany(@RequestBody CompanyPayload companyPayload) {
		boolean isCompanyExists = adminService.findCompany(companyPayload.getCompanyName());
		if (!isCompanyExists) {
			return ResponseEntity.ok(adminService.createCompany(companyPayload));
		} else {
			companyPayload.setMsg("Company Already Exits");
			companyPayload.setStatus("Error");
			return ResponseEntity.ok(companyPayload);
		}
	}

	@PostMapping("createDepartment")
	public ResponseEntity<?> createDepartment(@RequestBody DepartmentPayload departmentPayload) {
		boolean isDepartmentExists = adminService.findDepartment(departmentPayload);
		if (!isDepartmentExists) {
			return ResponseEntity.ok(adminService.createDepartment(departmentPayload));
		} else {
			departmentPayload.setMsg("Department Already Exits in " + departmentPayload.getCompanyName());
			departmentPayload.setStatus("Error");
			return ResponseEntity.ok(departmentPayload);
		}
	}

	@PostMapping("createProject")
	public ResponseEntity<?> createProject(@RequestBody ProjectPayload projectPayload) {
		boolean isDepartmentExists = adminService.findProject(projectPayload);
		if (!isDepartmentExists) {
			return ResponseEntity.ok(adminService.createProject(projectPayload));
		} else {
			projectPayload.setMsg("Project Already Exits in " + projectPayload.getDepartmentName());
			projectPayload.setStatus("Error");
			return ResponseEntity.ok(projectPayload);
		}
	}

	@PostMapping("createTeam")
	public ResponseEntity<?> createTeam(@RequestBody TeamPayload teamPayload) {
		boolean isProjectExists = adminService.findTeam(teamPayload);
		if (!isProjectExists) {
			return ResponseEntity.ok(adminService.createTeam(teamPayload));
		} else {
			teamPayload.setMsg("Team with name " + teamPayload.getTeamName() + " already Exits in "
					+ teamPayload.getProjectName());
			teamPayload.setStatus("Error");
			return ResponseEntity.ok(teamPayload);
		}
	}

	@PostMapping("createUser")
	public ResponseEntity<?> createUser(@RequestBody UserPayload userPayload) {
		boolean isProjectExists = adminService.findUser(userPayload);
		if (!isProjectExists) {
			return ResponseEntity.ok(adminService.createUser(userPayload));
		} else {
			userPayload.setMsg("User with " + userPayload.geteMail() + " already Exits");
			userPayload.setStatus("Error");
			return ResponseEntity.ok(userPayload);
		}
	}

	@GetMapping("getCompanyName")
	public ResponseEntity<?> getCompanyName() {
		List<CompanyEntity> companyName = adminService.findAllCompany();
		return ResponseEntity.ok(companyName);
	}

	@PostMapping("getDepartment")
	public ResponseEntity<?> getDepartmentByCompanyName(@RequestBody DepartmentPayload departmentPayload) {

		return null;

	}
}
