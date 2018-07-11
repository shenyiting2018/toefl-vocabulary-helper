package com.yiting.toeflvoc.services;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yiting.toeflvoc.daos.AliasDAO;
import com.yiting.toeflvoc.daos.CategoryDAO;
import com.yiting.toeflvoc.daos.RootAliasMapDAO;
import com.yiting.toeflvoc.daos.RootDAO;
import com.yiting.toeflvoc.daos.WordCategoryMapDAO;
import com.yiting.toeflvoc.daos.WordDAO;
import com.yiting.toeflvoc.daos.WordRootMapDAO;
import com.yiting.toeflvoc.models.Alias;
import com.yiting.toeflvoc.models.Category;
import com.yiting.toeflvoc.models.Root;
import com.yiting.toeflvoc.models.RootAliasMap;
import com.yiting.toeflvoc.models.User;
import com.yiting.toeflvoc.models.Word;
import com.yiting.toeflvoc.models.WordCategoryMap;
import com.yiting.toeflvoc.models.WordRootMap;
import com.yiting.toeflvoc.utils.ResourceDuplicatedException;
import com.yiting.toeflvoc.utils.ResourceNotFoundException;

@Service
public class VocabularyModelService {
	@Autowired
	private VocabularyBeanService beanService;
	
	@Autowired
	private AliasDAO aliasDAO;

	@Autowired
	private RootDAO rootDAO;

	@Autowired
	private WordDAO wordDAO;

	@Autowired
	private RootAliasMapDAO rootAliasMapDao;

	@Autowired
	private WordRootMapDAO wordRootMapDao;
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private WordCategoryMapDAO wordCategoryMapDAO;
	
	private final Logger logger = LoggerFactory.getLogger(VocabularyModelService.class);
	
	@Transactional(readOnly = false)
	public Alias addAlias(String aliasString) throws ResourceDuplicatedException, ResourceNotFoundException {
		Alias alias = this.getAliasByAliasString(aliasString);
		if (alias == null) {
			alias = aliasDAO.addAlias(aliasString);
		//	logger.info(String.format("Added new alias %s", aliasString));
			this.beanService.invalideCache();
		}
		
		return alias;
	}
	
	@Transactional(readOnly = false)
	public Category addCategory(String categoryName, User user) {
		Category category = this.getCategoryByCategoryNameAndUser(categoryName, user.getId());
		if (category == null) {
			category = this.categoryDAO.addCategory(categoryName, user);
		}
		
		return category;
	}

	@Transactional(readOnly = false)
	public Root addRoot(String rootString, List<String> meanings) {
		Root root = this.getRootByRootString(rootString);
		if (root == null) {
			root = rootDAO.addRoot(rootString, meanings);
		//	logger.debug(String.format("Added new root %s", rootString));
		}
		
		return root;
	}
	
	@Transactional(readOnly = false)
	public Root addRootMeaning(Root root, String meaning) {
		List<String> meanings = root.getMeanings();
		for (String existedMeaning : meanings) {
			if (existedMeaning.contains(meaning)) {
				return root;
			}
		}
		
		meanings.add(meaning);
		this.rootDAO.save(root);
		return root;
	}
	
	@Transactional(readOnly = false)
	public Word addWord(String wordString, List<String> meanings, int count) {
		Word word = this.getWordByWordString(wordString);
		if (word == null) {
			word = wordDAO.addWord(wordString, meanings, count);
			return word;
		} else if (word.getMeanings().isEmpty()){
			return this.replaceWordMeanings(word, meanings, count);
		} else {
			return word;
		}
	}
	
	@Transactional(readOnly = false)
	public Word addWordMeaning(Word word, String meaning) {
		List<String> meanings = word.getMeanings();
		for (String existedMeaning : meanings) {
			if (existedMeaning.contains(meaning)) {
				return word;
			}
		}
		
		meanings.add(meaning);
		this.wordDAO.save(word, 0);
		return word;
	}
	
	@Transactional(readOnly = false)
	public WordCategoryMap addWordCategoryMap(Word word, Category category) {
		WordCategoryMap map = this.getWordCategoryMapByWordIdAndCategory(word.getId(), category.getId());
		
		if (map == null) {
			map = new WordCategoryMap(category, word);
			this.wordCategoryMapDAO.addWordCategoryMap(map);
		}
		
		return map;
	}
	
	@Transactional(readOnly = false)
	public Word replaceWordMeanings(Word word, List<String> meanings, int count) {
		word.setMeanings(meanings);
		this.wordDAO.save(word, count);
		return word;
	}
	
	@Transactional(readOnly = false)
	public WordRootMap addWordRootMap(Word word, Root root, String description) {
		WordRootMap wordRootMap = this.getWordRootMap(word.getId(), root.getId());
		if (wordRootMap == null) {
			wordRootMap = this.wordRootMapDao.addWordRootMap(word, root, description);
			//logger.debug(String.format("Added new WordRootMap %s, %s", word.getWordString(), root.getRootString()));
			this.beanService.invalideCache();
		}
		
		return wordRootMap;
	}
	
	@Transactional(readOnly = false)
	public RootAliasMap addRootAliasMap(Root root, Alias alias, String description) {
		RootAliasMap rootAliasMap = this.getRootAliasMap(root.getId(), alias.getId());
		if (rootAliasMap == null) {
			rootAliasMap = this.rootAliasMapDao.addRoot(root, alias, description);
			//logger.debug(String.format("Added new RootAliasMap %s, %s", root.getRootString(), alias.getAliasString()));
			this.beanService.invalideCache();
		} 
		
		return rootAliasMap;
	}
	
	@Transactional 
	public Alias getAlias(Integer aliasId) throws ResourceNotFoundException {
		Alias alias = null;
		try {
			alias = this.aliasDAO.getAliasById(aliasId);
		} catch (NoResultException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}

		return alias;
	}
	
	@Transactional
	public Alias getAliasByAliasString(String aliasString) {
		Alias alias = null;
		try {
			alias = this.aliasDAO.getAliasByAliasString(aliasString); 
		} catch (NoResultException e) {
		}

		return alias;
	}
	
	public List<Alias> getAllAliases() {
		return this.aliasDAO.getAllAliass();
	}

	public Root getRoot(Integer rootId) throws ResourceNotFoundException {
		Root root = null;
		try {
			root = this.rootDAO.getRootById(rootId);
		} catch (NoResultException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}

		return root;
	}
	
	@Transactional
	public Root getRootByRootString(String rootString) {
		Root root = null;
		try {
			root = this.rootDAO.getRootByRootString(rootString); 
		} catch (NoResultException e) {
			
		}

		return root;
	}

	public List<Root> getAllRoots() {
		return this.rootDAO.getAllRoots();
	}
	
	@Transactional
	public Word getWord(Integer wordId) throws ResourceNotFoundException {
		Word word = null;
		try {
			word = this.wordDAO.getWordById(wordId);
		} catch (NoResultException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}

		return word;
	}
	
	@Transactional
	public Word getWordByWordString(String wordString) {
		Word word = null;
		 long time1 = System.currentTimeMillis();
		try {
			word = this.wordDAO.getWordByWordString(wordString); 
			long time2 = System.currentTimeMillis();
			System.out.println("dao use milli: " + (time2 - time1));
		} catch (NoResultException e) {
		}

		long time3 = System.currentTimeMillis();
		System.out.println("entire: " + (time3 - time1));
		return word;
	}
	
	@Transactional
	public WordCategoryMap getWordCategoryMapByWordIdAndCategory(Integer wordId, Integer categoryId) {
		WordCategoryMap map = null;
		try {
			map = this.wordCategoryMapDAO.getWordCategoryMapByWordIdAndCategory(wordId, categoryId);
		} catch (NoResultException e) {
		
		}
		
		return map;
	}
	
	@Transactional
	public Category getCategoryByCategoryNameAndUser(String categoryName, Integer userId) {
		Category category = null;
		try {
			category = this.categoryDAO.getCategoryByUserIdAndName(userId, categoryName);
		} catch (NoResultException e) {
		
		}
		
		return category;
	}
	
	@Transactional
	public List<Category> getUserCategories(Integer userId) {
		List<Category> categories = null;
		try {
			categories = this.categoryDAO.getCategoryByUserId(userId);
		} catch (NoResultException e) {
		
		}
		
		return categories;
	}

	public List<Word> getCategoryWords(String categoryName, Integer userId) {		
		List<Word> words = new ArrayList<>();
		Category category = this.getCategoryByCategoryNameAndUser(categoryName, userId);

		if (category != null) {
			List<WordCategoryMap> maps = this.getWordCategoryMapByCategory(category.getId());
			words = maps.stream()
					.map(map -> map.getWord())
					.sorted((a, b) -> {
						return a.getWordString().compareTo(b.getWordString());
					})
					.collect(Collectors.toList());

		}

		return words;
	}
	
	public List<Word> getAllWords() {
		return this.wordDAO.getAllWords();
	}
	
	@Transactional
	public List<RootAliasMap> getAllRootAliasMaps() {
		return this.rootAliasMapDao.getAllRootAliasMaps();
	}

	@Transactional
	public RootAliasMap getRootAliasMap(Integer rootId, Integer aliasId) {
		RootAliasMap rootAliasMap = null;
		try {
			rootAliasMap = this.rootAliasMapDao.getRootAliasMapByRootIdAndAlias(rootId, aliasId);
		} catch (NoResultException e) {
		}

		return rootAliasMap;
	}
	
	@Transactional
	public WordRootMap getWordRootMap(Integer wordId, Integer rootId) {
		WordRootMap wordRootMap = null;
		try {
			wordRootMap = this.wordRootMapDao.getWordRootMapByWordIdAndRoot(wordId, rootId);
		} catch (NoResultException e) {
			
		}

		return wordRootMap;
	}
	
	@Transactional(readOnly=false)
	public boolean updateProficiency(Integer wordId, String categoryName, Integer userId, int proficiency) {
		Category category = this.getCategoryByCategoryNameAndUser(categoryName, userId);
		WordCategoryMap wcm = this.getWordCategoryMapByWordIdAndCategory(wordId, category.getId());
		wcm.setProficiency(proficiency);
		this.wordCategoryMapDAO.save(wcm);
		
		return true;
	}
	
	@Transactional
	public List<WordRootMap> getWordRootMapsByRoot(Integer rootId) {
		return this.wordRootMapDao.getWordRootMapByRoot(rootId);
	}

	@Transactional
	public List<WordRootMap> getWordRootMapsByWord(Integer wordId) {
		return this.wordRootMapDao.getWordRootMapByWord(wordId);
	}
	
	@Transactional
	public List<WordRootMap> getAllWordRootMaps() {
		return this.wordRootMapDao.getAllWordRootMaps();
	}
	
	public List<WordCategoryMap> getWordCategoryMapByCategory(Integer categoryId) {
		return this.wordCategoryMapDAO.getWordCategoryMapByCategory(categoryId);
	}

	@Transactional
	public List<WordCategoryMap> getWordCategoryMapByWord(Integer wordId) {
		return this.wordCategoryMapDAO.getWordCategoryMapByWord(wordId);
	}
}
