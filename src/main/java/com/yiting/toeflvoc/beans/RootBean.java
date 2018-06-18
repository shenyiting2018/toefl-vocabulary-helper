package com.yiting.toeflvoc.beans;

import java.util.List;

import com.yiting.toeflvoc.models.Root;
import com.yiting.toeflvoc.models.RootAliasMap;
import com.yiting.toeflvoc.models.WordRootMap;

public class RootBean {
	private Root root;
	
	private List<WordRootMap> wordRootMaps;
	
	private List<RootAliasMap> rootAliasMaps;

	public RootBean() {
		super();
	}

	public RootBean(Root root, List<WordRootMap> wordRootMaps, List<RootAliasMap> rootAliasMaps) {
		super();
		this.root = root;
		this.wordRootMaps = wordRootMaps;
		this.rootAliasMaps = rootAliasMaps;
	}

	public Root getRoot() {
		return root;
	}

	public void setRoot(Root root) {
		this.root = root;
	}

	public List<WordRootMap> getWordRootMaps() {
		return wordRootMaps;
	}

	public void setWordRootMaps(List<WordRootMap> wordRootMaps) {
		this.wordRootMaps = wordRootMaps;
	}

	public List<RootAliasMap> getRootAliasMaps() {
		return rootAliasMaps;
	}

	public void setRootAliasMaps(List<RootAliasMap> rootAliasMaps) {
		this.rootAliasMaps = rootAliasMaps;
	}
}
