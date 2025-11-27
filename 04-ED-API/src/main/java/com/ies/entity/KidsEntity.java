package com.ies.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class KidsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="kids_id")
	private Integer kidsId;
	@Column(name ="kids_name")
	private String kidName;
	@Column(name ="kid_ssn_number")
	private Long kidSSN;
	@Column(name ="kid_age")
	private Integer kidAge;
	@Column(name ="app_case_number")
	private Integer caseNumber;
	
	@OneToOne
	@JoinColumn(name="case_number")
	private User user;

	public Integer getKidsId() {
		return kidsId;
	}

	public void setKidsId(Integer kidsId) {
		this.kidsId = kidsId;
	}

	public String getKidName() {
		return kidName;
	}

	public void setKidName(String kidName) {
		this.kidName = kidName;
	}

	public Long getKidSSN() {
		return kidSSN;
	}

	public void setKidSSN(Long kidSSN) {
		this.kidSSN = kidSSN;
	}

	public Integer getKidAge() {
		return kidAge;
	}

	public void setKidAge(Integer kidAge) {
		this.kidAge = kidAge;
	}

	public Integer getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(Integer caseNumber) {
		this.caseNumber = caseNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
