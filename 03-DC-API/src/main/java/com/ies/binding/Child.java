package com.ies.binding;

import lombok.Data;

@Data
public class Child {
	
	private String kidName;
	private Long kidSSN;
	private Integer kidAge;
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
	
	

}
