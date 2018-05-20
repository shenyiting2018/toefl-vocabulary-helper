package com.yiting.toeflvoc.repos;

import org.springframework.data.repository.CrudRepository;

import com.yiting.toeflvoc.models.Word;

public interface WordRepositoryInterface extends CrudRepository <Word, Long> {

}
