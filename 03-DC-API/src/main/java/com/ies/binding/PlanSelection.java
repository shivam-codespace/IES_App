package com.ies.binding;

import lombok.Data;

@Data
public class PlanSelection {
	
	private Integer caseNo;
	private String planName;
	public Integer getCaseNo() {
		return caseNo;
	}
	public void setCaseNo(Integer caseNo) {
		this.caseNo = caseNo;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	
	

}
