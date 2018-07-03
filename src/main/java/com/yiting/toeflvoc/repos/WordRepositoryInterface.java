package com.yiting.toeflvoc.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yiting.toeflvoc.models.Word;

public interface WordRepositoryInterface extends JpaRepository <Word, Long> {

}
