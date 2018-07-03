package com.yiting.toeflvoc.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yiting.toeflvoc.models.WordCategoryMap;

public interface WordCategoryMapRepositoryInterface extends JpaRepository <WordCategoryMap, Long> {

}
