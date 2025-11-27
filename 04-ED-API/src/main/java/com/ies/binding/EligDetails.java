package com.ies.binding;

import lombok.Data;

@Data
public class EligDetails {
	
	private String planName;
	
	private String planStatus;

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
	
	
}
