package com.yiting.toeflvoc.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yiting.toeflvoc.models.Category;

public interface CategoryRepositoryInterface extends JpaRepository <Category, Long> {

}
