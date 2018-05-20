package com.yiting.toeflvoc.services;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yiting.toeflvoc.daos.WordRootMapDAO;
import com.yiting.toeflvoc.models.Root;
import com.yiting.toeflvoc.models.Word;
import com.yiting.toeflvoc.models.WordRootMap;

@Service
public class WordRootMapService {
	@Autowired
	private WordService wordService;
	
	@Autowired
	private RootService rootService;
	
	@Autowired
	private WordRootMapDAO wordRootMapDao;
	
    private final Logger logger = LoggerFactory.getLogger(WordRootMapService.class);
    
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
	
}
