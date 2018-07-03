package com.yiting.toeflvoc.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="word_category_map")
public class WordCategoryMap {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	

	@ManyToOne
	@JoinColumn(name="word_id")
	private Word word;
	
	public WordCategoryMap(Category category, Word word) {
		super();
		this.category = category;
		this.word = word;
	}

	public WordCategoryMap() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

}
