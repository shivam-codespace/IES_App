package com.ies.requ;

import java.util.List;


import com.ies.binding.Child;
import lombok.Data;

@Data
public class ChildReq {
	
	private Integer caseNo;
	
	private List<Child> child;

	public Integer getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(Integer caseNo) {
		this.caseNo = caseNo;
	}

	public List<Child> getChild() {
		return child;
	}

	public void setChild(List<Child> child) {
		this.child = child;
	}
	
	
}
