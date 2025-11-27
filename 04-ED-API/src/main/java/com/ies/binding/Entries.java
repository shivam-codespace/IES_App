package com.ies.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Entries {

	private Integer edId;

	private String planName;

	private String planStatus;

	private LocalDate eligStartDate;

	private LocalDate eligEndDate;

	private double benefitAmt;

	private String denialReason;

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
	
	
	

}
