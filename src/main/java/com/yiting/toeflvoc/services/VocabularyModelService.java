package com.yiting.toeflvoc.services;


import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yiting.toeflvoc.daos.AliasDAO;
import com.yiting.toeflvoc.daos.RootAliasMapDAO;
import com.yiting.toeflvoc.daos.RootDAO;
import com.yiting.toeflvoc.daos.WordDAO;
import com.yiting.toeflvoc.daos.WordRootMapDAO;
import com.yiting.toeflvoc.models.Alias;
import com.yiting.toeflvoc.models.Root;
import com.yiting.toeflvoc.models.RootAliasMap;
import com.yiting.toeflvoc.models.Word;
import com.yiting.toeflvoc.models.WordRootMap;
import com.yiting.toeflvoc.utils.ResourceDuplicatedException;
import com.yiting.toeflvoc.utils.ResourceNotFoundException;

@Service
public class VocabularyModelService {
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
	
	private final Logger logger = LoggerFactory.getLogger(VocabularyModelService.class);
	
	@Transactional(readOnly = false)
	public Alias addAlias(String aliasString) throws ResourceDuplicatedException, ResourceNotFoundException {
		Alias alias = this.getAliasByAliasString(aliasString);
		if (alias == null) {
			alias = aliasDAO.addAlias(aliasString);
			logger.info(String.format("Added new alias %s", aliasString));
			return alias;
		} else {
			throw ResourceDuplicatedException.error(Alias.class, alias.getId());
		}
	}

	@Transactional(readOnly = false)
	public Root addRoot(String rootString, List<String> meanings) throws ResourceDuplicatedException, ResourceNotFoundException {
		Root root = this.getRootByRootString(rootString);
		if (root == null) {
			root = rootDAO.addRoot(rootString, meanings);
			logger.debug(String.format("Added new root %s", rootString));
			return root;
		} else {
			throw ResourceDuplicatedException.error(Root.class, root.getId());
		}
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
	public Word addWord(String wordString, List<String> meanings) throws ResourceDuplicatedException, ResourceNotFoundException {
		Word word = this.getWordByWordString(wordString);
		if (word == null) {
			word = wordDAO.addWord(wordString, meanings);
			return word;
		} else {
			throw ResourceDuplicatedException.error(Word.class, word.getId());
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
		this.wordDAO.save(word);
		return word;
	}
	
	@Transactional(readOnly = false)
	public WordRootMap addWordRootMap(Word word, Root root, String description) throws ResourceDuplicatedException {
		WordRootMap wordRootMap = this.getWordRootMap(word.getId(), root.getId());
		if (wordRootMap == null) {
			wordRootMap = this.wordRootMapDao.addWord(word, root, description);
			logger.debug(String.format("Added new WordRootMap %s, %s", word.getWordString(), root.getRootString()));
			return wordRootMap;
		} else {
			throw ResourceDuplicatedException.error(WordRootMap.class, wordRootMap.getId());
		}
	}
	
	@Transactional(readOnly = false)
	public RootAliasMap addRootAliasMap(Root root, Alias alias, String description) throws ResourceDuplicatedException, ResourceNotFoundException {
		RootAliasMap rootAliasMap = this.getRootAliasMap(root.getId(), alias.getId());
		if (rootAliasMap == null) {
			rootAliasMap = this.rootAliasMapDao.addRoot(root, alias, description);
			logger.debug(String.format("Added new RootAliasMap %s, %s", root.getRootString(), alias.getAliasString()));
			return rootAliasMap;
		} else {
			throw ResourceDuplicatedException.error(RootAliasMap.class, rootAliasMap.getId());
		}
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
	public Alias getAliasByAliasString(String aliasString) throws ResourceNotFoundException {
		Alias alias = null;
		try {
			alias = this.aliasDAO.getAliasByAliasString(aliasString); 
		} catch (NoResultException e) {
			throw new ResourceNotFoundException(e.getMessage());
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
	public Root getRootByRootString(String rootString) throws ResourceNotFoundException {
		Root root = null;
		try {
			root = this.rootDAO.getRootByRootString(rootString); 
		} catch (NoResultException e) {
			throw new ResourceNotFoundException(e.getMessage());
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
	public Word getWordByWordString(String wordString) throws ResourceNotFoundException {
		Word word = null;
		try {
			word = this.wordDAO.getWordByWordString(wordString); 
		} catch (NoResultException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}

		return word;
	}
	
	public List<Word> getAllWords() {
		return this.wordDAO.getAllWords();
	}
	
	@Transactional
	public List<RootAliasMap> getAllRootAliasMaps() {
		return this.rootAliasMapDao.getAllRootAliasMaps();
	}

	@Transactional
	public RootAliasMap getRootAliasMap(Integer rootId, Integer aliasId) throws ResourceNotFoundException {
		RootAliasMap rootAliasMap = null;
		try {
			rootAliasMap = this.rootAliasMapDao.getRootAliasMapByRootIdAndAlias(rootId, aliasId);
		} catch (NoResultException e) {
			throw new ResourceNotFoundException(e.getMessage());
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
	
	public List<WordRootMap> getWordRootMapsByRoot(Integer rootId) {
		return this.wordRootMapDao.getWordRootMapByRoot(rootId);
	}

	public List<WordRootMap> getWordRootMapsByWord(Integer wordId) {
		return this.wordRootMapDao.getWordRootMapByWord(wordId);
	}
	
	@Transactional
	public List<WordRootMap> getAllWordRootMaps() {
		return this.wordRootMapDao.getAllWordRootMaps();
	}

}
