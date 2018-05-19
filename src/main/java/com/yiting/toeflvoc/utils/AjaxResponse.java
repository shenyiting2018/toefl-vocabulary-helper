package com.yiting.toeflvoc.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class AjaxResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	
	private final HashMap<String, Object> data = new HashMap<String, Object>();
	private String status;
	
	public static AjaxResponse successResponse() {
		AjaxResponse response = new AjaxResponse();
		response.status = AjaxResponse.SUCCESS;
		return response;
	}
	
	public static AjaxResponse errorResponse() {
		AjaxResponse response = new AjaxResponse();
		response.status = AjaxResponse.ERROR;
		return response;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public HashMap<String, Object> getData() {
		return data;
	}

	public void putData(String key, Object value) {
		this.data.put(key, value);
	}
	
	public static void main(String[] args) {
		AjaxResponse response = AjaxResponse.successResponse();
		 try {
	         FileOutputStream fileOut = new FileOutputStream("ajax.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(response);
	         out.close();
	         fileOut.close();
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
	}
}