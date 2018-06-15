package com.yiting.toeflvoc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path="/")
public class MasterController {
	
	@RequestMapping(path="/", method=RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("name", "Shiyao");
		return "index";
	}
	
	@RequestMapping(path="/form", method=RequestMethod.GET)
	public String form(Model model) {
		model.addAttribute("name", "Shiyao");
		return "pages/search-and-analyze";
	}
}
