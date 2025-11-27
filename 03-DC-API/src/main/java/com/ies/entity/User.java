package com.ies.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name="USER_DETAILS")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "case_number")
	private Integer caseNo;
	@Column(name = "full_name")
	private String fullname;
	@Column(name = "date_of_birth")
	private String dob;
	@Column(name = "ssn_no")
	private String ssn;
	@Column(name = "gender")
	private String gender;
	@Column(name = "House_Number")
	private String houseNum;
	@Column(name = "city")
	private String city;
	@Column(name = "state_name")
	private String state;
	@Column(name = "email_id")
	private String email;
	@Column(name = "phone_number")
	private Long phone;
	@Column(name = "plan_name")
	private String planName;

	@Column(name = "CREADTED_DATE", updatable = false)
	@CreationTimestamp
	private LocalDate createDate;
	
	@ManyToOne
	@JoinColumn(name="CASE_WORKER_ID")
	private CaseWorker caseWorker;

	public Integer getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(Integer caseNo) {
		this.caseNo = caseNo;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(String houseNum) {
		this.houseNum = houseNum;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public CaseWorker getCaseWorker() {
		return caseWorker;
	}

	public void setCaseWorker(CaseWorker caseWorker) {
		this.caseWorker = caseWorker;
	}
	
	
}