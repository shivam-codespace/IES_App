package com.ies.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="ELIG_DTLS")
public class EligEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edgTraceId")
    private Integer edgTraceId;
    
    @Column(name = "plan_status")
    private String planStatus;
    
    @Column(name = "benifit_amount")
    private Double benefitAmt;

	public Integer getEdgTraceId() {
		return edgTraceId;
	}

	public void setEdgTraceId(Integer edgTraceId) {
		this.edgTraceId = edgTraceId;
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	public Double getBenefitAmt() {
		return benefitAmt;
	}

	public void setBenefitAmt(Double benefitAmt) {
		this.benefitAmt = benefitAmt;
	}
    
    
}
