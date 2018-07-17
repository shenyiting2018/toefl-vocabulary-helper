package com.yiting.toeflvoc.beans;

import com.yiting.toeflvoc.models.Category;
import com.yiting.toeflvoc.models.Word;

public class UserWordBean extends Word {
	private int proficiency;
	
	private int listNumber;

	private Category category;
	
	public UserWordBean() {};
	
	public UserWordBean(Word word, Category category, int proficiency, int listNumber) {
		super();
		this.id = word.getId();
		this.category = category;
		this.meanings = word.getMeanings();
		this.wordString = word.getWordString();
		this.proficiency = proficiency;
		this.listNumber = listNumber;
	}

	public int getProficiency() {
		return proficiency;
	}

	public int getListNumber() {
		return listNumber;
	}

	public void setProficiency(int proficiency) {
		this.proficiency = proficiency;
	}

	public void setListNumber(int listNumber) {
		this.listNumber = listNumber;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
