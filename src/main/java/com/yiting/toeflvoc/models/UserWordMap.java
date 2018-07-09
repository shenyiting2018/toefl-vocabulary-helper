package com.yiting.toeflvoc.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_word_map")
public class UserWordMap {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="word_id")
	private Word word;
	
	@Column(name="proficiency")
	private Integer proficiency;
	
	@Column(name="list")
	private String list;

	public UserWordMap(User user, Word word) {
		this.user = user;
		this.word = word;
	}

	public UserWordMap() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}
	
	public Word getWord() {
		return word;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public Integer getProficiency() {
		return proficiency;
	}

	public String getList() {
		return list;
	}

	public void setProficiency(Integer proficiency) {
		this.proficiency = proficiency;
	}

	public void setList(String list) {
		this.list = list;
	}
	
	
}
