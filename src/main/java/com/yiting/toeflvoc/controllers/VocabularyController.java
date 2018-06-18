package com.yiting.toeflvoc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yiting.toeflvoc.beans.AnalyzeResultBean;
import com.yiting.toeflvoc.beans.RootBean;
import com.yiting.toeflvoc.beans.WordBean;
import com.yiting.toeflvoc.models.RootAliasMap;
import com.yiting.toeflvoc.services.VocabularyBeanService;
import com.yiting.toeflvoc.services.VocabularyModelService;
import com.yiting.toeflvoc.utils.AjaxResponse;
import com.yiting.toeflvoc.utils.ResourceNotFoundException;

@RestController
@RequestMapping(path="/vocabularies")
public class VocabularyController {

	@Autowired
	private VocabularyBeanService beanService;
	
	@Autowired
	private VocabularyModelService modelService;
	
	@RequestMapping(path="/root", method=RequestMethod.POST)
	public @ResponseBody AjaxResponse addRoot(@RequestParam String rootString) {
		try {
			modelService.addRoot(rootString, null);
			return AjaxResponse.successResponse();
		} catch (Exception e) {
			AjaxResponse response = AjaxResponse.errorResponse();
			response.putData("message", e.getMessage());
			return response;
		}
	}
	
	@RequestMapping(path="/rootAliasMaps", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse getAllRootAliasMaps() {
		List<RootAliasMap> beans = modelService.getAllRootAliasMaps();
		
		AjaxResponse resp = AjaxResponse.successResponse();
		resp.putData("data", beans);
		return resp;
	}
	
	@RequestMapping(path="/rootBeans", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse getAllRootBeans() {
		List<RootBean> beans = beanService.getAllRootBeans();
		
		AjaxResponse resp = AjaxResponse.successResponse();
		resp.putData("data", beans);
		return resp;
	}
	
	@RequestMapping(path="/rootBean/{rootIdStr}", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse getRoodBeanByRootId(@PathVariable String rootIdStr) {
		RootBean bean;
		try {
			bean = beanService.getRootBean(Integer.valueOf(rootIdStr));
		} catch (NumberFormatException | ResourceNotFoundException e) {
			return AjaxResponse.errorResponseWithMsg(e.getMessage());

		}
		
		AjaxResponse resp = AjaxResponse.successResponse();
		resp.putData("data", bean);
		return resp;
	}
	
	
	@RequestMapping(path="/wordBeans", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse getAllWordBeans() {
		List<WordBean> beans = beanService.getAllWordBeans();
		
		AjaxResponse resp = AjaxResponse.successResponse();
		resp.putData("data", beans);
		return resp;
	}
	
	@RequestMapping(path="/analyze", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse analyzeRootForWord(@RequestParam String wordString) {
		List<AnalyzeResultBean> beans;
		try {
			beans = beanService.analyzeRootForWord(wordString);
		} catch (NumberFormatException | ResourceNotFoundException e) {
			return AjaxResponse.errorResponseWithMsg(e.getMessage());
		}
		
		AjaxResponse resp = AjaxResponse.successResponse();
		resp.putData("data", beans);
		return resp;
	}
	
}