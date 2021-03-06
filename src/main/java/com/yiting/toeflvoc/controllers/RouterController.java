package com.yiting.toeflvoc.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yiting.toeflvoc.beans.UserBean;
import com.yiting.toeflvoc.utils.RequestUtils;

@Controller
@RequestMapping(path="/")
public class RouterController {

	@RequestMapping(path="/", method=RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		UserBean user = RequestUtils.getUserBeanFromRequest(request);
		String name = user.getFirstName();
		model.addAttribute("name", name);
		return "pages/kill-3000";
	}

	@RequestMapping(path="/search-and-analyze", method=RequestMethod.GET)
	public String searchAndAnalyze(HttpServletRequest request, Model model) {
		UserBean user = RequestUtils.getUserBeanFromRequest(request);
		String name = user.getFirstName();
		model.addAttribute("name", name);
		return "pages/search-and-analyze";
	}

	@RequestMapping(path="/root-manager", method=RequestMethod.GET)
	public String rootManager(HttpServletRequest request, Model model) {
		UserBean user = RequestUtils.getUserBeanFromRequest(request);
		String name = user.getFirstName();
		model.addAttribute("name", name);
		return "pages/root-manager";
	}

	@RequestMapping(path="/word-manager", method=RequestMethod.GET)
	public String wordManager(HttpServletRequest request, Model model) {
		UserBean user = RequestUtils.getUserBeanFromRequest(request);
		String name = user.getFirstName();
		model.addAttribute("name", name);
		return "pages/word-manager";
	}

	@RequestMapping(path="/kill-3000", method=RequestMethod.GET)
	public String kill3000(HttpServletRequest request, Model model, Authentication authentication) {
		UserBean user = RequestUtils.getUserBeanFromRequest(request);
		String name = user.getFirstName();
		
		model.addAttribute("name", name);
		return "pages/kill-3000";
	}

	@RequestMapping(path="/403", method = RequestMethod.GET)
	public String error403() {
		return "/error/403";
	}
	
	@RequestMapping(path="/login", method = RequestMethod.GET)
	public String login(){
	     return "pages/login";
	}
	
	@RequestMapping(path="/logout", method = RequestMethod.GET)
	public String logout(){
	     return "pages/logout";
	}
	
	@RequestMapping(path="/register", method = RequestMethod.GET)
	public String register(){
	     return "pages/register";
	}
}
