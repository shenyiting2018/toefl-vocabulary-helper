package com.yiting.toeflvoc.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yiting.toeflvoc.services.CSVImporter;
import com.yiting.toeflvoc.utils.AjaxResponse;

@RestController
@RequestMapping(path="/misc")
public class MiscController {
	@Autowired
	private CSVImporter csvImporter;
	
	@RequestMapping(path="/", method=RequestMethod.GET)
	public AjaxResponse importGRECSV() {
		try {
			csvImporter.importGRERootCSV();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
