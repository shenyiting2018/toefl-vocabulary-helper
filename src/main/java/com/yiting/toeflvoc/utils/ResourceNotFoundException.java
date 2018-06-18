package com.yiting.toeflvoc.utils;

public class ResourceNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7196283763508314698L;

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
	
	public static ResourceNotFoundException error(Class model, Integer id) {
		String msg = String.format("Failed retrieving %s because it does not exist", model.getSimpleName(), id);
		return new ResourceNotFoundException(msg);
	}

}
