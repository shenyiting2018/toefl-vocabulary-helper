package com.yiting.toeflvoc.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yiting.toeflvoc.models.Category;
import com.yiting.toeflvoc.models.Word;
import com.yiting.toeflvoc.models.WordCategoryMap;
import com.yiting.toeflvoc.repos.WordCategoryMapRepositoryInterface;

@Repository
public class WordCategoryMapDAO {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private WordCategoryMapRepositoryInterface wordCategoryMapRepo;

	private static final String GET_WORD_CATEGORY_MAP_BY_WORD_STRING = "from WordCategoryMap where word.wordString = :wordString";	
	private static final String GET_WORD_CATEGORY_MAP_BY_CATEGORY_STRING = "from WordCategoryMap where category.categoryName = :categoryName";
	private static final String GET_WORD_CATEGORY_MAP_BY_CATEGORY_ID = "from WordCategoryMap where category.id = :categoryId";
	private static final String GET_WORD_CATEGORY_MAP_BY_WORD_ID = "from WordCategoryMap where word.id = :wordId";
	private static final String GET_WORD_CATEGORY_MAP_BY_WORD_ID_AND_CATEGORY_ID = "from WordCategoryMap where word.id = :wordId and category.id = :categoryId order by word.wordString";
	
	public WordCategoryMap getWordCategoryMapByWordIdAndCategory(Integer wordId, Integer categoryId) throws NoResultException {
		return (WordCategoryMap) this.entityManager.createQuery(GET_WORD_CATEGORY_MAP_BY_WORD_ID_AND_CATEGORY_ID)
				.setParameter("wordId", wordId)
				.setParameter("categoryId", categoryId)
				.getSingleResult();
	}
	
	public WordCategoryMap addWordCategoryMap(Word word, Category category) {
		WordCategoryMap wordCategoryMap = new WordCategoryMap();
		wordCategoryMap.setWord(word);
		wordCategoryMap.setCategory(category);
		
		this.wordCategoryMapRepo.save(wordCategoryMap);
		return wordCategoryMap;
	}
	
	public WordCategoryMap addWordCategoryMap(WordCategoryMap wordCategoryMap) {
		this.wordCategoryMapRepo.save(wordCategoryMap);
		return wordCategoryMap;
	}
	
	public List<WordCategoryMap> getAllWordCategoryMaps() {
		List<WordCategoryMap> wordCategoryMap = (List<WordCategoryMap>) wordCategoryMapRepo.findAll();
		return wordCategoryMap;
	}
	
	public List<WordCategoryMap> getWordCategoryMapByCategory(Integer categoryId) {
		return this.entityManager.createQuery(GET_WORD_CATEGORY_MAP_BY_CATEGORY_ID)
				.setParameter("categoryId", categoryId)
				.getResultList();
	}
	
	public List<WordCategoryMap> getWordCategoryMapByWord(Integer wordId) {
		return this.entityManager.createQuery(GET_WORD_CATEGORY_MAP_BY_WORD_ID)
				.setParameter("wordId", wordId)
				.getResultList();
	}
	
	public void save(WordCategoryMap map) {
		this.wordCategoryMapRepo.save(map);
	}
}
