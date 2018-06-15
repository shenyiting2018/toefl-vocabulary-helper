package com.yiting.toeflvoc.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yiting.toeflvoc.services.CSVImporter;
import com.yiting.toeflvoc.utils.AjaxResponse;

@RestController
@RequestMapping(path="/batch")
public class BatchController {
	@Autowired
	private CSVImporter csvImporter;
	
	@RequestMapping(path="/", method=RequestMethod.GET)
	public AjaxResponse importGRECSV() {
		try {
			int count = csvImporter.importGRERootCSV();
			return AjaxResponse.successResponseWithMsg(String.format("Imported successfully, %s entries processed", count));
		} catch (IOException e) {
			return AjaxResponse.errorResponseWithMsg(e.getMessage());
		}
	}
}
