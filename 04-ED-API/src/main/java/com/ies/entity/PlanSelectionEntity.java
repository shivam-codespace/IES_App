package com.ies.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "PLAN_SELECTION_TABLE")
public class PlanSelectionEntity {
	
	@Id
	@GeneratedValue
	@Column(name ="selection_id")
	private Integer selectionId;
	@Column(name ="plan_name")
	private String planName;
	
	@OneToOne
	@JoinColumn(name="case_number")
	private User caseNo;

	public Integer getSelectionId() {
		return selectionId;
	}

	public void setSelectionId(Integer selectionId) {
		this.selectionId = selectionId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public User getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(User caseNo) {
		this.caseNo = caseNo;
	}
	
	
}