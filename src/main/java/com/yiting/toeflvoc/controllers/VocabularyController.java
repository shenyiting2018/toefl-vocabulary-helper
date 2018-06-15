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
import com.yiting.toeflvoc.services.VocabularyService;
import com.yiting.toeflvoc.utils.AjaxResponse;

@RestController
@RequestMapping(path="/root")
public class VocabularyController {

	@Autowired
	private VocabularyService vocabularyService;
	
	@RequestMapping(path="/", method=RequestMethod.POST)
	public @ResponseBody AjaxResponse addRoot(@RequestParam String rootString) {
		try {
			vocabularyService.addRoot(rootString, null);
			return AjaxResponse.successResponse();
		} catch (Exception e) {
			AjaxResponse response = AjaxResponse.errorResponse();
			response.putData("message", e.getMessage());
			return response;
		}
	}
	
	@RequestMapping(path="/mapbeans", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse getAllRoots() {
		List<RootAliasMapBean> beans = vocabularyService.getAllRootAliasMapBeans();
		
		AjaxResponse resp = AjaxResponse.successResponse();
		resp.putData("data", beans);
		return resp;
	}
	
	@RequestMapping(path="/rootwords", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse getAllWordsForRoot(@RequestParam String rootIdStr) {
		List<WordRootMap> beans = vocabularyService.getRootWords(Integer.valueOf(rootIdStr));
		
		AjaxResponse resp = AjaxResponse.successResponse();
		resp.putData("data", beans);
		return resp;
	}
	
	@RequestMapping(path="/analyze", method=RequestMethod.GET)
	public @ResponseBody AjaxResponse analyzeRootForWord(@RequestParam String wordString) {
		List<RootAliasMapBean> beans = vocabularyService.analyzeRootForWord(wordString);
		
		AjaxResponse resp = AjaxResponse.successResponse();
		resp.putData("data", beans);
		return resp;
	}
}
