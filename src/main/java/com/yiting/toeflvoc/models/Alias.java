package com.yiting.toeflvoc.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Alias {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name="alias_string")
	private String aliasString;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAliasString() {
		return aliasString;
	}

	public void setAliasString(String aliasString) {
		this.aliasString = aliasString;
	}
}
