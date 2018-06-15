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

import com.yiting.toeflvoc.beans.RootAliasMapBean;
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

@Service
public class VocabularyService {
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

	private List<RootAliasMapBean> aliasCache = new ArrayList<>();

	private final Logger logger = LoggerFactory.getLogger(VocabularyService.class);

	@Transactional
	public Root getRootByRootString(String rootString) {
		Root root = null;
		try {
			root = this.rootDAO.getRootByRootString(rootString); 
		} catch (NoResultException e) {
			logger.error(e.getLocalizedMessage());
		}

		return root;
	}

	@Transactional(readOnly = false)
	public Root addRoot(String rootString, List<String> meaning) {
		Root root = this.getRootByRootString(rootString);
		if (root == null) {
			root = rootDAO.addRoot(rootString, meaning);
			logger.debug(String.format("Added new root %s", rootString));
		}
		return root;
	}

	@Transactional(readOnly = false) 
	public void saveRoot(Root root) {
		this.rootDAO.save(root);
	}

	@Transactional
	public Alias getAliasByAliasString(String aliasString) {
		Alias alias = null;
		try {
			alias = this.aliasDAO.getAliasByAliasString(aliasString); 
		} catch (NoResultException e) {
			//TODO 
		}

		return alias;
	}

	@Transactional(readOnly = false)
	public Alias addAlias(String aliasString) {
		Alias alias = this.getAliasByAliasString(aliasString);
		if (alias == null) {
			alias = aliasDAO.addAlias(aliasString);
			logger.debug(String.format("Added new alias %s", aliasString));
		}
		return alias;
	}

	@Transactional(readOnly = false) 
	public void saveAlias(Alias alias) {
		this.aliasDAO.save(alias);
	}

	@Transactional
	public List<RootAliasMapBean> analyzeRootForWord(final String wordString) {
		return this.getAllRootAliasMapBeans()
				.stream()
				.filter(a -> {
					return wordString.contains(a.getAliasString());
				})
				.collect(Collectors.toList());
	}

	public boolean match(String word, String aliasString) {
		return word.contains(aliasString);
	}

	public List<RootAliasMapBean> getAllRootAliasMapBeans() {
		if (this.aliasCache.isEmpty()) {
			List<RootAliasMap> models = this.getAllRootAliasMaps();
			this.aliasCache = 
					models.stream()
					.map( m -> 
					new RootAliasMapBean(
							m.getAlias().getId(), 
							m.getRoot().getId(),
							m.getAlias().getAliasString(),
							m.getRoot().getRootString(),
							m.getRoot().getMeaning(),
							m.getDescription())
							)
					.collect(Collectors.toList());
		}

		return this.aliasCache;
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

	@Transactional(readOnly = false)
	public RootAliasMap addRootAliasMap(Root root, Alias alias, String description) {
		RootAliasMap rootAliasMap = this.getRootAliasMap(root.getId(), alias.getId());
		if (rootAliasMap == null) {
			rootAliasMap = this.rootAliasMapDao.addRoot(root, alias, description);
			logger.debug(String.format("Added new RootAliasMap %s, %s", root.getRootString(), alias.getAliasString()));
		}

		return rootAliasMap;
	}

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
	public void saveWord(Word word) {
		this.wordDAO.save(word);
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

	@Transactional(readOnly = false)
	public WordRootMap addWordRootMap(Word word, Root root, String description) {
		WordRootMap wordRootMap = this.getWordRootMap(word.getId(), root.getId());
		if (wordRootMap == null) {
			wordRootMap = this.wordRootMapDao.addWord(word, root, description);
			logger.debug(String.format("Added new WordRootMap %s, %s", word.getWordString(), root.getRootString()));
		}

		return wordRootMap;
	}

	public List<WordRootMap> getRootWords(Integer rootId) {
		return this.wordRootMapDao.getRootWords(rootId);
	}
}
