package com.ies.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="Elig_Details")
public class EligDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="elig_Id")
	private Integer edId;
	@Column(name ="plan_name")
	private String planName;
	@Column(name ="plan_status")
	private String planStatus;
	
	@Column(name = "ELIG_START_DATE")
	private LocalDate eligStartDate;

	@Column(name = "ELIG_END_DATE")
	private LocalDate eligEndDate;
	@Column(name ="benifit_amount")
	private double benefitAmt;
	@Column(name ="denial_reason")
	private String denialReason;
	
	@OneToOne
	@JoinColumn(name="case_number")
	private User user;

	public Integer getEdId() {
		return edId;
	}

	public void setEdId(Integer edId) {
		this.edId = edId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	public LocalDate getEligStartDate() {
		return eligStartDate;
	}

	public void setEligStartDate(LocalDate eligStartDate) {
		this.eligStartDate = eligStartDate;
	}

	public LocalDate getEligEndDate() {
		return eligEndDate;
	}

	public void setEligEndDate(LocalDate eligEndDate) {
		this.eligEndDate = eligEndDate;
	}

	public double getBenefitAmt() {
		return benefitAmt;
	}

	public void setBenefitAmt(double benefitAmt) {
		this.benefitAmt = benefitAmt;
	}

	public String getDenialReason() {
		return denialReason;
	}

	public void setDenialReason(String denialReason) {
		this.denialReason = denialReason;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}