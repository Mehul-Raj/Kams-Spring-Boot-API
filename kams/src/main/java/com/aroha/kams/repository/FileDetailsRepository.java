package com.aroha.kams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aroha.kams.model.FileDetailsEntity;

@Repository
public interface FileDetailsRepository extends JpaRepository<FileDetailsEntity, Integer> {

	@Query(value = "select document_tag,count(file_id) from file_details  inner join \n"
			+ "tag_entity on tag_entity.tag_name=file_details.document_tag group by document_tag ;", nativeQuery = true)
	public List<Object[]> getChartDetail();

	@Query(value = "select mime_type,count(file_id) from file_details group by mime_type ;", nativeQuery = true)
	public List<Object[]> getTypeDetail();

	@Query(value = "select document_tag,count(file_id) from file_details  inner join tag_entity on "
			+ "tag_entity.tag_name=file_details.document_tag where doc_company=?1 group by document_tag ;", nativeQuery = true)
	public List<Object[]> getCompanyChartDetails(String company);
	
	@Query(value = "select mime_type,count(file_id) from file_details where doc_company=?1 group by mime_type ;", nativeQuery = true)
	public List<Object[]> getTypeDetailCompany(String company);
	
	@Query(value = "select document_tag,count(file_id) from file_details  inner join tag_entity on "
			+ "tag_entity.tag_name=file_details.document_tag where doc_company=?1 and doc_department=?2 group by document_tag ;", nativeQuery = true)
	public List<Object[]> getDepartmentChartDetails(String company,String department);
	
	@Query(value = "select mime_type,count(file_id) from file_details where doc_company=?1 and doc_department=?2 group by mime_type ;", nativeQuery = true)
	public List<Object[]> getTypeDetailDepartment(String company,String department);
	
	@Query(value = "select document_tag,count(file_id) from file_details  inner join tag_entity on "
			+ "tag_entity.tag_name=file_details.document_tag where doc_company=?1 and doc_department=?2  and doc_project=?3 group by document_tag ;", nativeQuery = true)
	public List<Object[]> getProjectChartDetails(String company,String department,String project);
	
	@Query(value = "select mime_type,count(file_id) from file_details where doc_company=?1 and doc_department=?2 and doc_project=?3 group by mime_type ;", nativeQuery = true)
	public List<Object[]> getTypeDetailProject(String company,String department,String project);
	
}
