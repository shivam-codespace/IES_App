package com.ies.service;

import com.ies.bindings.App;
import java.util.List;


public interface ArService  {
    
	String createApplication(App app);
    
	List<App> fetchApps(Integer userId);
    
	App getAppByCaseNum(Long caseNum);
    
	String updateAppStatus(Long caseNum, String status);
    
	List<App> filterApps(String status, String planName);
}
