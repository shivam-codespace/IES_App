package com.ies.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "CASE_WORKER")
public class CaseWorker {

	@Id
	@GeneratedValue
	@Column(name = "CASE_WORKER_ID")
	private Integer caseWorkerId;

	@Column(name = "CASE_WORKER_NAME")
	private String caseWorkerName;

	@Column(name = "CASE_WORKER_EMAIL")
	private String caseWorkerEmail;

	@Column(name = "CASE_WORKER_PWD")
	private String caseWorkerPwd;

	@Column(name = "CASE_WORKER_MOB")
	private Long caseWorkerMob;

	@Column(name = "CASE_WORKER_GENDER")
	private String caseWorkerGender;
	
	@Column(name = "CASE_WORKER_DOB")
	private LocalDate caseWorkerDOB;

	@Column(name = "CASE_WORKER_SSN")
	private Long caseWorkerSSN;
	
	@Column(name="ACTIVE_SW")
	private String activeSw="Y";
	
	@Column(name = "ACC_STATUS")
	private String accStatus="LOCKED";

	@Column(name = "CREADTED_DATE", updatable = false)
	@CreationTimestamp
	private LocalDate createDate;
	
	@Column(name = "UPDATE_DATE", insertable = false)
	@UpdateTimestamp
	private LocalDate updateDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	public Integer getCaseWorkerId() {
		return caseWorkerId;
	}

	public void setCaseWorkerId(Integer caseWorkerId) {
		this.caseWorkerId = caseWorkerId;
	}

	public String getCaseWorkerName() {
		return caseWorkerName;
	}

	public void setCaseWorkerName(String caseWorkerName) {
		this.caseWorkerName = caseWorkerName;
	}

	public String getCaseWorkerEmail() {
		return caseWorkerEmail;
	}

	public void setCaseWorkerEmail(String caseWorkerEmail) {
		this.caseWorkerEmail = caseWorkerEmail;
	}

	public String getCaseWorkerPwd() {
		return caseWorkerPwd;
	}

	public void setCaseWorkerPwd(String caseWorkerPwd) {
		this.caseWorkerPwd = caseWorkerPwd;
	}

	public Long getCaseWorkerMob() {
		return caseWorkerMob;
	}

	public void setCaseWorkerMob(Long caseWorkerMob) {
		this.caseWorkerMob = caseWorkerMob;
	}

	public String getCaseWorkerGender() {
		return caseWorkerGender;
	}

	public void setCaseWorkerGender(String caseWorkerGender) {
		this.caseWorkerGender = caseWorkerGender;
	}

	public LocalDate getCaseWorkerDOB() {
		return caseWorkerDOB;
	}

	public void setCaseWorkerDOB(LocalDate caseWorkerDOB) {
		this.caseWorkerDOB = caseWorkerDOB;
	}

	public Long getCaseWorkerSSN() {
		return caseWorkerSSN;
	}

	public void setCaseWorkerSSN(Long caseWorkerSSN) {
		this.caseWorkerSSN = caseWorkerSSN;
	}

	public String getActiveSw() {
		return activeSw;
	}

	public void setActiveSw(String activeSw) {
		this.activeSw = activeSw;
	}

	public String getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public LocalDate getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	

 }
