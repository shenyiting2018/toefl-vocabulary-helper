package com.yiting.toeflvoc.utils;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

import com.yiting.toeflvoc.beans.UserBean;

public class RequestUtils {

	public static UserBean getUserBeanFromRequest(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		UserBean user = (UserBean) ((Authentication)principal).getPrincipal();
		
		return user;
	}
}
