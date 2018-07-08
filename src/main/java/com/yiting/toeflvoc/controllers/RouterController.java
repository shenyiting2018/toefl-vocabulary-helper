package com.yiting.toeflvoc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path="/")
public class RouterController {

	@RequestMapping(path="/", method=RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("name", "Shiyao");
		return "pages/search-and-analyze";
	}

	@RequestMapping(path="/search-and-analyze", method=RequestMethod.GET)
	public String searchAndAnalyze(Model model) {
		model.addAttribute("name", "Shiyao");
		return "pages/search-and-analyze";
	}

	@RequestMapping(path="/root-manager", method=RequestMethod.GET)
	public String rootManager(Model model) {
		model.addAttribute("name", "Shiyao");
		return "pages/root-manager";
	}

	@RequestMapping(path="/word-manager", method=RequestMethod.GET)
	public String wordManager(Model model) {
		model.addAttribute("name", "Shiyao");
		return "pages/word-manager";
	}

	@RequestMapping(path="/kill-3000", method=RequestMethod.GET)
	public String kill3000(Model model) {
		model.addAttribute("name", "Shiyao");
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
}
