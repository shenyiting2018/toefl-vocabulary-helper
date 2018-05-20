package com.yiting.toeflvoc.daos;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yiting.toeflvoc.models.Root;
import com.yiting.toeflvoc.models.Word;
import com.yiting.toeflvoc.models.WordRootMap;
import com.yiting.toeflvoc.repos.WordRootMapRepositoryInterface;

@Repository
public class WordRootMapDAO {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private WordRootMapRepositoryInterface wordRootMapRepo;

	private static final String GET_WORD_ROOT_MAP_BY_WORD_STRING = "from WordRootMap where word.wordString = :wordString";	
	private static final String GET_WORD_ROOT_MAP_BY_ROOT_STRING = "from WordRootMap where root.rootString = :rootString";
	private static final String GET_WORD_ROOT_MAP_BY_WORD_ID = "from WordRootMap where word.id = :wordId";
	private static final String GET_WORD_ROOT_MAP_BY_WORD_ID_AND_ROOT_ID = "from WordRootMap where word.id = :wordId and root.id = :rootId";
	
	public WordRootMap getWordRootMapByWordIdAndRoot(Integer wordId, Integer rootId) throws NoResultException {
		return (WordRootMap) this.entityManager.createQuery(GET_WORD_ROOT_MAP_BY_WORD_ID_AND_ROOT_ID)
				.setParameter("wordId", wordId)
				.setParameter("rootId", rootId)
				.getSingleResult();
	}
	
	public WordRootMap addWord(Word word, Root root, String description) {
		WordRootMap wordRootMap = new WordRootMap();
		wordRootMap.setWord(word);
		wordRootMap.setRoot(root);
		
		this.wordRootMapRepo.save(wordRootMap);
		return wordRootMap;
	}
}