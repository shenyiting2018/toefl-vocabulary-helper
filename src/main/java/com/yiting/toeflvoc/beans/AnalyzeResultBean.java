package com.yiting.toeflvoc.beans;

import java.util.List;

public class AnalyzeResultBean {
	private int rootId;

	private String aliasString;

	private String rootString;

	private List<String> rootMeanings;

	private String description;

	private boolean verified;

	public AnalyzeResultBean(int rootId, String aliasString, String rootString, List<String> rootMeaning,
			String description, boolean verified) {
		super();
		this.rootId = rootId;
		this.aliasString = aliasString;
		this.rootString = rootString;
		this.rootMeanings = rootMeaning;
		this.description = description;
		this.verified = verified;
	}

	public AnalyzeResultBean() {
		super();
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

	public List<String> getRootMeanings() {
		return rootMeanings;
	}

	public void setRootMeanings(List<String> rootMeanings) {
		this.rootMeanings = rootMeanings;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

}
