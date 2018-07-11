package com.yiting.toeflvoc.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.yiting.toeflvoc.utils.StringListConverter;

@Entity
@Table(name="word")
public class Word {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Integer id;
	
	@NotNull
	@Size(max = 255)
	@Column(name="word_string", unique=true)
	protected String wordString;
	
	@NotNull
	@Column(name="meanings")
	@Convert(converter = StringListConverter.class)
	protected List<String> meanings;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWordString() {
		return wordString;
	}

	public void setWordString(String wordString) {
		this.wordString = wordString;
	}

	public List<String> getMeanings() {
		return meanings;
	}

	public void setMeanings(List<String> meanings) {
		this.meanings = meanings;
	}
}
