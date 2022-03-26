package uz.university.springappuniversity.model;

import java.util.List;

public class StudentDto {
	private String firstname;
	private String lastname;
	private String phoneNumber;
	private List<Integer> subjectId;
	private Integer groupId;
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public List<Integer> getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(List<Integer> subjectId) {
		this.subjectId = subjectId;
	}
	
	
}
