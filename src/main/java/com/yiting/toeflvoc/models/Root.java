package com.yiting.toeflvoc.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="root")
public class Root {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@NotNull
	@Size(max = 255)
	@Column(name="root_string")
	private String rootString;
	
	@NotNull
	@ElementCollection
	@Column(name="meaning")
	private List<String> meaning;

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

	public List<String> getMeaning() {
		return meaning;
	}

	public void setMeaning(List<String> meaning) {
		this.meaning = meaning;
	}

}
