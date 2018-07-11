package com.yiting.toeflvoc.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yiting.toeflvoc.services.RootCSVImporter;
import com.yiting.toeflvoc.services.StandardWordCSVImporter;
import com.yiting.toeflvoc.utils.AjaxResponse;
import com.yiting.toeflvoc.utils.ResourceDuplicatedException;
import com.yiting.toeflvoc.utils.ResourceNotFoundException;

@RestController
@RequestMapping(path="/batch")
public class BatchController {
	@Autowired
	private RootCSVImporter rootCsvImporter;
	
	@Autowired
	private StandardWordCSVImporter wordCsvImporter;
	
	@RequestMapping(path="/rootCSV/{fileName}", method=RequestMethod.POST)
	public AjaxResponse importRootCSV(@PathVariable String fileName) throws ResourceDuplicatedException, ResourceNotFoundException {
		try {
			int count = rootCsvImporter.importGRERootCSV(fileName);
			return AjaxResponse.successResponseWithMsg(String.format("Imported successfully, %s entries processed", count));
		} catch (IOException e) {
			return AjaxResponse.errorResponseWithMsg(e.getMessage());
		}
	}
	
	@RequestMapping(path="/greCSV/{fileName}/{categoryName}/{replaceWordMeaning}", method=RequestMethod.POST)
	public AjaxResponse importGRECSV(@PathVariable String fileName, @PathVariable String categoryName, @PathVariable String replaceWordMeaning) throws ResourceDuplicatedException, ResourceNotFoundException {
		try {
			int count = wordCsvImporter.importWordCSV(fileName, categoryName, "true".equals(replaceWordMeaning));
			return AjaxResponse.successResponseWithMsg(String.format("Imported successfully, %s entries processed", count));
		} catch (IOException e) {
			return AjaxResponse.errorResponseWithMsg(e.getMessage());
		}
	}
}
