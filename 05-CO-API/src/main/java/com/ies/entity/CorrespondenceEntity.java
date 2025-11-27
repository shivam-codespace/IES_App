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
@Table(name = "correspondence_datails")
public class CorrespondenceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="notice_id")
	private Integer noticeId;
	
	@Column(name ="correspondance_url")
	private String url;
	@Column(name ="print_date")
	private LocalDate printDate;
	@Column(name ="notice_status")
	private String noticeStatus;
	@Column(name ="aap_case_number")
	private Integer caseNumber;
	@Column(name ="notice_url")
	private String noticeUrl;
	
	@Column(name ="create_date")
	private LocalDate createdDate;
	
	@OneToOne
	@JoinColumn(name="elig_Id")
	private EligDetailsEntity edId;
	
	@OneToOne
	@JoinColumn(name="case_number")
	private User caseNo;

	public Integer getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public LocalDate getPrintDate() {
		return printDate;
	}

	public void setPrintDate(LocalDate printDate) {
		this.printDate = printDate;
	}

	public String getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(String noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

	public Integer getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(Integer caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public EligDetailsEntity getEdId() {
		return edId;
	}

	public void setEdId(EligDetailsEntity edId) {
		this.edId = edId;
	}

	public User getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(User caseNo) {
		this.caseNo = caseNo;
	}

	
 

 
	
}
