package com.ies.binding;

import lombok.Data;

@Data
public class Income {

	private Integer caseNo;
	private Double monthlySalaryIncome;
	private Double propertyIncome;
	private Double rentIncome;
	public Integer getCaseNo() {
		return caseNo;
	}
	public void setCaseNo(Integer caseNo) {
		this.caseNo = caseNo;
	}
	public Double getMonthlySalaryIncome() {
		return monthlySalaryIncome;
	}
	public void setMonthlySalaryIncome(Double monthlySalaryIncome) {
		this.monthlySalaryIncome = monthlySalaryIncome;
	}
	public Double getPropertyIncome() {
		return propertyIncome;
	}
	public void setPropertyIncome(Double propertyIncome) {
		this.propertyIncome = propertyIncome;
	}
	public Double getRentIncome() {
		return rentIncome;
	}
	public void setRentIncome(Double rentIncome) {
		this.rentIncome = rentIncome;
	}
	
	
}
