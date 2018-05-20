package com.yiting.toeflvoc.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yiting.toeflvoc.models.Word;
import com.yiting.toeflvoc.repos.WordRepositoryInterface;

@Repository
public class WordDAO {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private WordRepositoryInterface wordRepo;

	private static final String GET_WORD_BY_ID = "SELECT Word FROM Word WHERE id = :id";	
	private static final String GET_WORD_BY_WORD_STRING = "from Word where wordString = :wordString";

	public List<Word> getAllWords() {
		List<Word> words = (List<Word>) wordRepo.findAll();
		return words;
	}

	public Word getWordById(Integer id) {
		Word word = (Word) this.entityManager.createQuery(GET_WORD_BY_ID)
				.setParameter("id", id)
				.getSingleResult();
		return word;
	}

	public Word getWordByWordString(String wordString) throws NoResultException{
		return (Word) this.entityManager.createQuery(GET_WORD_BY_WORD_STRING)
				.setParameter("wordString", wordString)
				.getSingleResult();
	}
	
	public Word addWord(String wordString, List<String> meaning) {
		Word word = new Word();
		word.setWordString(wordString);
		word.setMeaning(meaning);
		this.wordRepo.save(word);
		return word;
	}
	
	public void save(Word word) {
		this.wordRepo.save(word);
	}
}
