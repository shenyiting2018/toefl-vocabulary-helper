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
@Table(name="root")
public class Root {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@NotNull
	@Size(max = 255)
	@Column(name="root_string", unique=true)
	private String rootString;
	
	@NotNull
	@Column(name="meanings")
	@Convert(converter = StringListConverter.class)
	private List<String> meanings;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRootString() {
		return rootString;
	}

	public void setRootString(String rootString) {
	    this.rootString = rootString;
	}

	public List<String> getMeanings() {
		return meanings;
	}

	public void setMeanings(List<String> meanings) {
		this.meanings = meanings;
	}
}
