package com.aroha.kams.service;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aroha.kams.payload.MediaTypePayload;
import com.aroha.kams.payload.TagTypePayload;

import com.aroha.kams.repository.FileDetailsRepository;

@Service
public class ChartService {

	@Autowired
	private FileDetailsRepository fileRepository;

	// Tag Name
	public ArrayList<TagTypePayload> getChartData() {
		List<Object[]> list = fileRepository.getChartDetail();
		System.out.println(list.indexOf(1));
		ArrayList<TagTypePayload> listObj = new ArrayList<>();
		for (Object[] object : list) {
			int i = 1;
			TagTypePayload tagPayload = new TagTypePayload();
			BigInteger bigInt = (BigInteger) object[i];
			tagPayload.setTanNumber(bigInt);
			tagPayload.setTagName((String) object[i - 1]);
			listObj.add(tagPayload);
			i++;
			System.out.println(i);
		}
		return listObj;
	}

	public ArrayList<MediaTypePayload> getTypeData() {
		List<Object[]> list = fileRepository.getTypeDetail();
		System.out.println(list.indexOf(1));
		ArrayList<MediaTypePayload> listObj = new ArrayList<>();
		for (Object[] object : list) {
			int i = 1;
			MediaTypePayload typePayload = new MediaTypePayload();
			BigInteger bigInt = (BigInteger) object[i];
			typePayload.setTypeNumber(bigInt);
			typePayload.setTypeName((String) object[i - 1]);
			listObj.add(typePayload);
			i++;
		}
		return listObj;
	}

	// getTypeDetailCompany
	public ArrayList<TagTypePayload> getCompanyChartData(String companyName) {
		List<Object[]> list = fileRepository.getCompanyChartDetails(companyName);
		ArrayList<TagTypePayload> listObj = new ArrayList<>();
		for (Object[] object : list) {
			int i = 1;
			TagTypePayload tagPayload = new TagTypePayload();
			BigInteger bigInt = (BigInteger) object[i];
			tagPayload.setTanNumber(bigInt);
			tagPayload.setTagName((String) object[i - 1]);
			listObj.add(tagPayload);
			i++;
			System.out.println(i);
		}
		return listObj;
	}

	public ArrayList<MediaTypePayload> getCompanyTypeData(String company) {

		List<Object[]> list = fileRepository.getTypeDetailCompany(company);
		ArrayList<MediaTypePayload> listObj = new ArrayList<>();
		for (Object[] object : list) {
			int i = 1;
			MediaTypePayload typePayload = new MediaTypePayload();
			BigInteger bigInt = (BigInteger) object[i];
			typePayload.setTypeNumber(bigInt);
			typePayload.setTypeName((String) object[i - 1]);
			listObj.add(typePayload);
			i++;
		}
		return listObj;
	}

	public ArrayList<TagTypePayload> getDepartmentChartData(String companyName, String departmentName) {
		List<Object[]> list = fileRepository.getDepartmentChartDetails(companyName, departmentName);
		ArrayList<TagTypePayload> listObj = new ArrayList<>();
		for (Object[] object : list) {
			int i = 1;
			TagTypePayload tagPayload = new TagTypePayload();
			BigInteger bigInt = (BigInteger) object[i];
			tagPayload.setTanNumber(bigInt);
			tagPayload.setTagName((String) object[i - 1]);
			listObj.add(tagPayload);
			i++;
			System.out.println(i);
		}
		return listObj;
	}

	public ArrayList<MediaTypePayload> getDepartmentTypeData(String company, String department) {

		List<Object[]> list = fileRepository.getTypeDetailDepartment(company, department);
		ArrayList<MediaTypePayload> listObj = new ArrayList<>();
		for (Object[] object : list) {
			int i = 1;
			MediaTypePayload typePayload = new MediaTypePayload();
			BigInteger bigInt = (BigInteger) object[i];
			typePayload.setTypeNumber(bigInt);
			typePayload.setTypeName((String) object[i - 1]);
			listObj.add(typePayload);
			i++;
		}
		return listObj;
	}

	public ArrayList<TagTypePayload> getProjectChartData(String companyName, String departmentName,
			String projectName) {
		List<Object[]> list = fileRepository.getProjectChartDetails(companyName, departmentName, projectName);
		ArrayList<TagTypePayload> listObj = new ArrayList<>();
		for (Object[] object : list) {
			int i = 1;
			TagTypePayload tagPayload = new TagTypePayload();
			BigInteger bigInt = (BigInteger) object[i];
			tagPayload.setTanNumber(bigInt);
			tagPayload.setTagName((String) object[i - 1]);
			listObj.add(tagPayload);
			i++;
			System.out.println(i);
		}
		return listObj;
	}
	
	public ArrayList<MediaTypePayload> getProjectTypeData(String company, String department,String project) {

		List<Object[]> list = fileRepository.getTypeDetailProject(company, department,project);
		ArrayList<MediaTypePayload> listObj = new ArrayList<>();
		for (Object[] object : list) {
			int i = 1;
			MediaTypePayload typePayload = new MediaTypePayload();
			BigInteger bigInt = (BigInteger) object[i];
			typePayload.setTypeNumber(bigInt);
			typePayload.setTypeName((String) object[i - 1]);
			listObj.add(typePayload);
			i++;
		}
		return listObj;
	}
}
