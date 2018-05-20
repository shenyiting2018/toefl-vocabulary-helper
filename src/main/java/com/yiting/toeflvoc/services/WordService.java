package com.yiting.toeflvoc.services;

import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yiting.toeflvoc.daos.WordDAO;
import com.yiting.toeflvoc.models.Word;

@Service
public class WordService {
	@Autowired
	private WordDAO wordDAO;
    private final Logger logger = LoggerFactory.getLogger(WordService.class);

	@Transactional
	public Word getWordByWordString(String wordString) {
		Word word = null;
		try {
			word = this.wordDAO.getWordByWordString(wordString); 
		} catch (NoResultException e) {
			//TODO 
		}
		
		return word;
	}
	
	@Transactional(readOnly = false)
	public Word addWord(String wordString, List<String> meaning) {
		Word word = this.getWordByWordString(wordString);
		if (word == null) {
			word = wordDAO.addWord(wordString, meaning);
			logger.debug(String.format("Added new word %s", wordString));
		}
		return word;
	}
	
	@Transactional(readOnly = false) 
	public void save(Word word) {
		this.wordDAO.save(word);
	}
}
