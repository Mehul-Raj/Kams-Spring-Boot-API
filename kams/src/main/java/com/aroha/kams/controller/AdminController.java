package com.aroha.kams.controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aroha.kams.model.CompanyEntity;
import com.aroha.kams.model.DepartmentEntity;
import com.aroha.kams.model.ProjectEntity;
import com.aroha.kams.model.TagEntity;
import com.aroha.kams.model.TeamEntity;
import com.aroha.kams.payload.CompanyPayload;
import com.aroha.kams.payload.DepartmentPayload;
import com.aroha.kams.payload.ProjectPayload;
import com.aroha.kams.payload.TagPayload;
import com.aroha.kams.payload.TeamPayload;
import com.aroha.kams.payload.UserPayload;
import com.aroha.kams.service.AdminService;
import com.aroha.kams.service.ChartService;

@RestController
@RequestMapping("/api/dropbox/admin")
public class AdminController {
	@Autowired
	AdminService adminService;

	@Autowired
	private ChartService chatService;

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
		System.out.println("I");
		boolean isProjectExists = adminService.findUser(userPayload);
		if (!isProjectExists) {
			return ResponseEntity.ok(adminService.createUser(userPayload));
		} else {
			userPayload.setMsg("User with " + userPayload.geteMail() + " already Exits");
			userPayload.setStatus("Error");
			System.out.println(userPayload.getMsg());
			return ResponseEntity.ok(userPayload);
		}
	}

	@GetMapping("getCompanyName")
	public ResponseEntity<?> getCompanyName() {
		List<CompanyEntity> companyNameList = adminService.findAllCompany();
		return ResponseEntity.ok(companyNameList);
	}

	@GetMapping("getDepartment/{companyName}")
	public ResponseEntity<?> getDepartmentByCompanyName(@PathVariable(value = "companyName") String companyName) {
		List<DepartmentEntity> departmentNameList = adminService.findAllDepartment(companyName);
		return ResponseEntity.ok(departmentNameList);
	}

	@GetMapping("getProject/{companyName}/{departmentName}")
	public ResponseEntity<?> getProjectByDepartmentAndCompanyName(
			@PathVariable(value = "companyName") String companyName,
			@PathVariable(value = "departmentName") String departmentName) {
		List<ProjectEntity> projectNameList = adminService.findAllProject(companyName, departmentName);
		return ResponseEntity.ok(projectNameList);
	}

	@GetMapping("getTeam/{companyName}/{departmentName}/{projectName}")
	public ResponseEntity<?> getTeamByDepartmentAndCompanyName(@PathVariable(value = "companyName") String companyName,
			@PathVariable(value = "departmentName") String departmentName,
			@PathVariable(value = "projectName") String projectName) {
		List<TeamEntity> teamNameList = adminService.findAllTeam(companyName, departmentName, projectName);
		return ResponseEntity.ok(teamNameList);
	}

	@PostMapping("createTag")
	public ResponseEntity<?> createTag(@RequestBody TagPayload tagPayload) {
		boolean isTagExists = adminService.findTag(tagPayload.getTagName());
		if (!isTagExists) {
			return ResponseEntity.ok(adminService.createTag(tagPayload));
		} else {
			tagPayload.setMsg("Company Already Exits");
			tagPayload.setStatus("Error");
			return ResponseEntity.ok(tagPayload);
		}
	}

	@GetMapping("getTagName")
	public ResponseEntity<?> getTagName() {
		List<TagEntity> tagNameList = adminService.findAllTag();
		return ResponseEntity.ok(tagNameList);
	}

	@GetMapping("/tagData")
	public ResponseEntity<?> getChartData() {
		if (chatService.getChartData().isEmpty()) {
			return ResponseEntity.ok("No Data");
		}
		return ResponseEntity.ok(chatService.getChartData());
	}

	@GetMapping("/typeData")
	public ResponseEntity<?> getTypeData() {
		if (chatService.getTypeData().isEmpty()) {
			return ResponseEntity.ok("No Data");
		}
		return ResponseEntity.ok(chatService.getTypeData());
	}

	@GetMapping("/chartCompany/{selectedCompany}")
	public ResponseEntity<?> getChartCompany(@PathVariable(value = "selectedCompany") String selectedCompany) {
		if (chatService.getCompanyChartData(selectedCompany).isEmpty()) {
			return ResponseEntity.ok("No Data");
		}
		return ResponseEntity.ok(chatService.getCompanyChartData(selectedCompany));
	}

	@GetMapping("/typeDataCompany/{selectedCompany}")
	public ResponseEntity<?> getTypeDataCompany(@PathVariable(value = "selectedCompany") String selectedCompany) {
		if (chatService.getCompanyTypeData(selectedCompany).isEmpty()) {
			return ResponseEntity.ok("No Data");
		}
		return ResponseEntity.ok(chatService.getCompanyTypeData(selectedCompany));
	}

	// Get Tag Data Based On Department And Company
	@GetMapping("/tagDepartment/{selectedCompany}/{departmentName}")
	public ResponseEntity<?> getTagDepartment(@PathVariable(value = "selectedCompany") String selectedCompany,
			@PathVariable(value = "departmentName") String departmentName) {
		if (chatService.getDepartmentChartData(selectedCompany, departmentName).isEmpty()) {
			return ResponseEntity.ok("No Data");
		}
		return ResponseEntity.ok(chatService.getDepartmentChartData(selectedCompany, departmentName));
	}

	// Get Type Data Based On Department And Company
	@GetMapping("/typeDepartment/{selectedCompany}/{departmentName}")
	public ResponseEntity<?> getTypeDepartment(@PathVariable(value = "selectedCompany") String selectedCompany,
			@PathVariable(value = "departmentName") String departmentName) {
		if (chatService.getDepartmentTypeData(selectedCompany, departmentName).isEmpty()) {
			return ResponseEntity.ok("No Data");
		}
		return ResponseEntity.ok(chatService.getDepartmentTypeData(selectedCompany, departmentName));
	}

	// Get Tag Data Based On Project,Department And Company
	@GetMapping("/tagProject/{selectedCompany}/{departmentName}/{projectName}")
	public ResponseEntity<?> getTagProject(@PathVariable(value = "selectedCompany") String selectedCompany,
			@PathVariable(value = "departmentName") String departmentName,
			@PathVariable(value = "projectName") String projectName) {
		if (chatService.getProjectChartData(selectedCompany, departmentName, projectName).isEmpty()) {
			return ResponseEntity.ok("No Data");
		}
		return ResponseEntity.ok(chatService.getProjectChartData(selectedCompany, departmentName, projectName));
	}

	// Get Type Data Based On Project,Department And Company
	@GetMapping("/typeProject/{selectedCompany}/{departmentName}/{projectName}")
	public ResponseEntity<?> getTypeProject(@PathVariable(value = "selectedCompany") String selectedCompany,
			@PathVariable(value = "departmentName") String departmentName,
			@PathVariable(value = "projectName") String projectName) {
		if (chatService.getProjectTypeData(selectedCompany, departmentName,projectName).isEmpty()) {
			return ResponseEntity.ok("No Data");
		}
		return ResponseEntity.ok(chatService.getProjectTypeData(selectedCompany, departmentName,projectName));
	}
}
