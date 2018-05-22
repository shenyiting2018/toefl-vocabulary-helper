package com.yiting.toeflvoc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yiting.toeflvoc.beans.RootAliasMapBean;
import com.yiting.toeflvoc.models.WordRootMap;
import com.yiting.toeflvoc.services.RootAliasMapService;
import com.yiting.toeflvoc.services.RootService;
import com.yiting.toeflvoc.services.WordRootMapService;
import com.yiting.toeflvoc.utils.AjaxResponse;

@RestController
@RequestMapping(path="/root")
public class RootController {

	@Autowired
	private RootService rootService;
	
	@Autowired
	private RootAliasMapService rootAliasMapService;
	
	@Autowired
	private WordRootMapService wordRootMapService;
	
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
	
	@RequestMapping(path="/mapbeans", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse getAllRoots() {
		List<RootAliasMapBean> beans = rootAliasMapService.getAllRootAliasMapBeans();
		
		AjaxResponse resp = AjaxResponse.successResponse();
		resp.putData("data", beans);
		return resp;
	}
	
	@RequestMapping(path="/rootwords", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse getAllWordsForRoot(@RequestParam String rootIdStr) {
		List<WordRootMap> beans = wordRootMapService.getRootWords(Integer.valueOf(rootIdStr));
		
		AjaxResponse resp = AjaxResponse.successResponse();
		resp.putData("data", beans);
		return resp;
	}
}
