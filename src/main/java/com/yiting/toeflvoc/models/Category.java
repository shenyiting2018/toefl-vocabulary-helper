package com.yiting.toeflvoc.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="category")
public class Category {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
    private User user;
	
	@NotNull
	@Size(max = 255)
	@Column(name="category_name")
	private String categoryName;
	
	@Column(name="status_code")
	private int statusCode;
	
	public Category() {};
	
	public Category(User user, String categoryName) {
		this.user = user;
		this.categoryName = categoryName;
		this.statusCode = 1;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public User getUser() {
		return user;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
