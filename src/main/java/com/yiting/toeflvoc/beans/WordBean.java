package com.yiting.toeflvoc.beans;

import java.util.List;

import com.yiting.toeflvoc.models.Word;
import com.yiting.toeflvoc.models.WordCategoryMap;
import com.yiting.toeflvoc.models.WordRootMap;

public class WordBean {

	private Word word;
	
	private List<WordRootMap> wordRootMaps;
	
	private List<WordCategoryMap> wordCategoryMaps;

	public WordBean(Word word, List<WordRootMap> wordRootMaps, List<WordCategoryMap> wordCategoryMaps) {
		super();
		this.word = word;
		this.wordRootMaps = wordRootMaps;
	}

	public WordBean() {
		super();
	}
	
	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public List<WordRootMap> getWordRootMaps() {
		return wordRootMaps;
	}

	public void setWordRootMaps(List<WordRootMap> wordRootMaps) {
		this.wordRootMaps = wordRootMaps;
	}

	public List<WordCategoryMap> getWordCategoryMaps() {
		return wordCategoryMaps;
	}

	public void setWordCategoryMaps(List<WordCategoryMap> wordCategoryMaps) {
		this.wordCategoryMaps = wordCategoryMaps;
	}
}
