package com.yiting.toeflvoc.beans;

import java.util.List;

import com.yiting.toeflvoc.models.Alias;
import com.yiting.toeflvoc.models.RootAliasMap;

public class AliasBean {
	private Alias alias;
	
	private List<RootAliasMap> rootAliasMaps;

	public AliasBean(Alias alias, List<RootAliasMap> rootAliasMaps) {
		super();
		this.alias = alias;
		this.rootAliasMaps = rootAliasMaps;
	}

	public AliasBean() {
		super();
	}

	public Alias getAlias() {
		return alias;
	}

	public void setAlias(Alias alias) {
		this.alias = alias;
	}

	public List<RootAliasMap> getRootAliasMaps() {
		return rootAliasMaps;
	}

	public void setRootAliasMaps(List<RootAliasMap> rootAliasMaps) {
		this.rootAliasMaps = rootAliasMaps;
	}
}
