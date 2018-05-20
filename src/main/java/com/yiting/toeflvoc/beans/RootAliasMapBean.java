package com.yiting.toeflvoc.beans;

import java.util.List;

public class RootAliasMapBean {

	private int aliasId;

	private int rootId;

	private String aliasString;

	private String rootString;

	private List<String> rootMeaning;

	private String description;

	public RootAliasMapBean() {};

	public RootAliasMapBean(int aliasId, int rootId, String aliasString, String rootString, List<String> rootMeaning,
			String description) {
		super();
		this.aliasId = aliasId;
		this.rootId = rootId;
		this.aliasString = aliasString;
		this.rootString = rootString;
		this.rootMeaning = rootMeaning;
		this.description = description;
	}

	public int getAliasId() {
		return aliasId;
	}

	public void setAliasId(int aliasId) {
		this.aliasId = aliasId;
	}

	public int getRootId() {
		return rootId;
	}

	public void setRootId(int rootId) {
		this.rootId = rootId;
	}

	public String getAliasString() {
		return aliasString;
	}

	public void setAliasString(String aliasString) {
		this.aliasString = aliasString;
	}

	public String getRootString() {
		return rootString;
	}

	public void setRootString(String rootString) {
		this.rootString = rootString;
	}

	public List<String> getRootMeaning() {
		return rootMeaning;
	}

	public void setRootMeaning(List<String> rootMeaning) {
		this.rootMeaning = rootMeaning;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
