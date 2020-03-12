package com.aroha.kams.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aroha.kams.model.FileDetailsEntity;
import com.aroha.kams.model.UserEntity;
import com.aroha.kams.repository.FileDetailsRepository;
import com.aroha.kams.repository.UserRepository;

@Service
public class UserDBService {

	@Autowired
	FileDetailsRepository fileDetailsRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	HttpSession session;

	// Save FileDetails
	public boolean saveFileDetails(FileDetailsEntity fileDetails) {
		boolean flag = false;
		try {
			fileDetailsRepository.save(fileDetails);
			flag = true;
		} catch (Exception ex) {
			System.out.println("Error in saveing");
		}
		return flag;
	}

	// Get ALl FILE
	public List<FileDetailsEntity> getAllFile(String getEMailId) {
		UserEntity user = (UserEntity) userRepository.findByeMail(getEMailId);
		String team = user.getUserTeamName();
		String companyName = user.getUserCompany();
		String deptartmentName = user.getUserdepartment();
		String projectName = user.getUserProjectName();
		List<FileDetailsEntity> filelist = fileDetailsRepository.findAll();

		// Create ArrayList To Add Elements
		List<FileDetailsEntity> filelistForUser = new ArrayList<FileDetailsEntity>();
		int listSize = filelist.size();
		String role = user.getUserRole().toUpperCase();

		// Check Role
		// If Role Is user Provide Access To All Document in current Team
		if (role.equalsIgnoreCase("ROLE_USER")) {
			for (int leng = 0; leng < listSize; leng++) {

				System.out.println("comapre " + filelist.get(leng).getDocTeam() + "   " + team);
				if (filelist.get(leng).getDocCompany().equalsIgnoreCase(companyName)
						&& filelist.get(leng).getDocDepartment().equalsIgnoreCase(deptartmentName)
						&& filelist.get(leng).getDocProject().equalsIgnoreCase(projectName)
						&& filelist.get(leng).getDocTeam().equalsIgnoreCase(team)) {
					filelistForUser.add(filelist.get(leng));
				}
			}
		}

		// If Role is Team Lead then team and project document can be Accessed;
		else if (role.equalsIgnoreCase("ROLE_TEAM")) {
			for (int leng = 0; leng < listSize; leng++) {
				if (filelist.get(leng).getDocCompany().equalsIgnoreCase(companyName)
						&& filelist.get(leng).getDocDepartment().equalsIgnoreCase(deptartmentName)
						&& filelist.get(leng).getDocProject().equalsIgnoreCase(projectName)) {
					filelistForUser.add(filelist.get(leng));
				}
			}
		}
		// If Role is Manager then team, project And Department document can be
		// Accessed;
		else if (role.equalsIgnoreCase("ROLE_MANAGER")) {
			for (int leng = 0; leng < listSize; leng++) {
				if (filelist.get(leng).getDocCompany().equalsIgnoreCase(companyName)
						&& filelist.get(leng).getDocDepartment().equalsIgnoreCase(deptartmentName)) {
					filelistForUser.add(filelist.get(leng));
				}
			}
		}
		return filelistForUser;
	}

	// Get File By Type
	public List<FileDetailsEntity> getAllFileByType(String docType, String getEMailId) {
		UserEntity user = userRepository.findById(getEMailId).orElse(new UserEntity());
		List<FileDetailsEntity> filelist = fileDetailsRepository.findAll();
		// Create ArrayList To Add Elements
		List<FileDetailsEntity> filelistForUser = new ArrayList<FileDetailsEntity>();
		int listSize = filelist.size();

		String role = user.getUserRole().toUpperCase();

		// Check Role
		// If Role Is user Provide All Document in current Team with team
		if (role.equalsIgnoreCase("ROLE_USER")) {
			for (int leng = 0; leng < listSize; leng++) {
				System.out.println("File Name " + filelist.get(leng).getFileName());
				String team = user.getUserTeamName();
				if (docType.equalsIgnoreCase("png")) {
					if (filelist.get(leng).getDocTeam().equalsIgnoreCase(team)
							&& filelist.get(leng).getFileName().contains("." + docType)
							|| filelist.get(leng).getFileName().contains(".jpeg")) {
						filelistForUser.add(filelist.get(leng));
					}
				} else if (filelist.get(leng).getDocTeam().equalsIgnoreCase(team)
						&& filelist.get(leng).getFileName().contains("." + docType)) {
					filelistForUser.add(filelist.get(leng));
				}
			}
			System.out.println("Array " + filelistForUser.size());
		}

		// If Role is Team Lead then team and project document can be Accessed;
		else if (role.equalsIgnoreCase("ROLE_TEAM")) {
			System.out.println("inside Team Lead Role");
			for (int leng = 0; leng < listSize; leng++) {
				String projectName = user.getUserProjectName();
				if (filelist.get(leng).getDocProject().equalsIgnoreCase(projectName)
						&& filelist.get(leng).getFileName().contains("." + docType)) {
					filelistForUser.add(filelist.get(leng));
				}
			}
		}
		// If Role is Manager then team, project And Department document can be
		// Accessed;
		else if (role.equalsIgnoreCase("ROLE_MANAGER")) {
			for (int leng = 0; leng < listSize; leng++) {
				String department = user.getUserdepartment();
				if (filelist.get(leng).getDocDepartment().equalsIgnoreCase(department)
						&& filelist.get(leng).getFileName().contains("." + docType)) {
					filelistForUser.add(filelist.get(leng));
				}
			}
		}
		return filelistForUser;
	}

	// Get File By Tags
	public List<FileDetailsEntity> getAllFileByTag(String tag, String getEMailId) {
		UserEntity user = userRepository.findById(getEMailId).orElse(new UserEntity());
		List<FileDetailsEntity> filelist = fileDetailsRepository.findAll();

		// Create ArrayList To Add Elements
		List<FileDetailsEntity> filelistForUser = new ArrayList<FileDetailsEntity>();
		int listSize = filelist.size();
		String role = user.getUserRole().toUpperCase();

		// Check Role
		// If Role Is user Provide All Document in current Team with tag
		if (role.equalsIgnoreCase("ROLE_USER")) {
			for (int leng = 0; leng < listSize; leng++) {
				String team = user.getUserTeamName();
				if (filelist.get(leng).getDocTeam().equalsIgnoreCase(team)
						&& filelist.get(leng).getTag().contains(tag)) {
					filelistForUser.add(filelist.get(leng));
				}
			}
		}

		// If Role is Team Lead then team and project document can be Accessed;
		else if (role.equalsIgnoreCase("ROLE_TEAM")) {
			for (int leng = 0; leng < listSize; leng++) {
				String projectName = user.getUserProjectName();
				if (filelist.get(leng).getDocProject().equalsIgnoreCase(projectName)
						&& filelist.get(leng).getTag().contains(tag)) {
					filelistForUser.add(filelist.get(leng));
				}
			}
		}
		// If Role is Manager then team, project And Department document can be
		// Accessed;
		else if (role.equalsIgnoreCase("ROLE_MANAGER")) {
			for (int leng = 0; leng < listSize; leng++) {
				String department = user.getUserdepartment();
				if (filelist.get(leng).getDocDepartment().equalsIgnoreCase(department)
						&& filelist.get(leng).getTag().contains(tag)) {
					filelistForUser.add(filelist.get(leng));
				}
			}
		}
		return filelistForUser;
	}

	public Set<String> findAllTag() {
		List<FileDetailsEntity> filelist = fileDetailsRepository.findAll();
		List<String> tagName = new ArrayList<>();
		int tagNameSize = filelist.size();
		for (int leng = 0; leng < tagNameSize; leng++) {
			tagName.add(filelist.get(leng).getTag());
		}
		Set<String> tagSet = new HashSet<String>(tagName);
		return tagSet;
	}

}
