package com.yiting.toeflvoc.utils;

public class ResourceDuplicatedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -441742464487695825L;

	public ResourceDuplicatedException(String msg) {
		super(msg);
	}
	
	public static ResourceDuplicatedException error(Class model, Integer id) {
		String msg = String.format("Failed adding new %s because it is duplicated, the original resource id is %s", model.getSimpleName(), id);
		return new ResourceDuplicatedException(msg);
	}

}
