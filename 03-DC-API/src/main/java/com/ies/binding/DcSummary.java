package com.ies.binding;

import java.util.List;

import lombok.Data;

@Data
public class DcSummary {
	
	private Income income;
	
	private Education education;
	
	private List<Child> childs;

	public Income getIncome() {
		return income;
	}

	public void setIncome(Income income) {
		this.income = income;
	}

	public Education getEducation() {
		return education;
	}

	public void setEducation(Education education) {
		this.education = education;
	}

	public List<Child> getChilds() {
		return childs;
	}

	public void setChilds(List<Child> childs) {
		this.childs = childs;
	}
	
	

}
