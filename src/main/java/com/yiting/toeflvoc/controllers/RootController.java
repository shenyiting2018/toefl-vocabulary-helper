package com.yiting.toeflvoc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yiting.toeflvoc.services.RootService;
import com.yiting.toeflvoc.utils.AjaxResponse;

@RestController
@RequestMapping(path="/root")
public class RootController {

	@Autowired
	private RootService rootService;
	
	@RequestMapping(path="/", method=RequestMethod.POST)
	public @ResponseBody AjaxResponse addRoot(@RequestParam String rootString) {
		try {
			rootService.addRoot(rootString, null);
			return AjaxResponse.successResponse();
		} catch (Exception e) {
			AjaxResponse response = AjaxResponse.errorResponse();
			response.putData("message", e.getMessage());
			return response;
		}
	}
}
