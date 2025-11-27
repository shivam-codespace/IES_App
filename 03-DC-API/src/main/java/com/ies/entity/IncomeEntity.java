package com.ies.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name ="Income_Dtls")
public class IncomeEntity {
	
	@Id
	@GeneratedValue
	@Column(name = "income_Id")
	private Integer incomeId;
	@Column(name = "monthly_alary_income")
	private Double monthlySalaryIncome;
	@Column(name = "property_Income")
	private Double propertyIncome;
	@Column(name = "rent_Income")
	private Double rentIncome;
	@Column(name = "app_case_number")
	private Integer caseNumber;
	
	@OneToOne
	@JoinColumn(name="case_number")
	private User user;

	public Integer getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(Integer incomeId) {
		this.incomeId = incomeId;
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
