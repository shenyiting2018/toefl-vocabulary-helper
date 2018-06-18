package com.yiting.toeflvoc.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yiting.toeflvoc.beans.AliasBean;
import com.yiting.toeflvoc.beans.AnalyzeResultBean;
import com.yiting.toeflvoc.beans.RootBean;
import com.yiting.toeflvoc.beans.WordBean;
import com.yiting.toeflvoc.models.RootAliasMap;
import com.yiting.toeflvoc.models.Word;
import com.yiting.toeflvoc.models.WordRootMap;
import com.yiting.toeflvoc.utils.ResourceNotFoundException;

@Service
public class VocabularyBeanService {


	@Autowired
	private VocabularyModelService modelService;

	private final Logger logger = LoggerFactory.getLogger(VocabularyBeanService.class);

	private Map<Integer, AliasBean> aliasBeansCache = new HashMap<>();
	private Map<Integer, RootBean> rootBeansCache = new HashMap<>();
	private Map<Integer, WordBean> wordBeansCache = new HashMap<>();

	private Map<Integer, AliasBean> getAliasBeansCache() {
		if (this.aliasBeansCache.isEmpty()) {
			List<RootAliasMap> maps = this.modelService.getAllRootAliasMaps();

			for (RootAliasMap map : maps) {
				this.aliasBeansCache.putIfAbsent(map.getAlias().getId(), new AliasBean(map.getAlias(), new ArrayList<>()));
				this.aliasBeansCache.get(map.getAlias().getId()).getRootAliasMaps().add(map);
			}
			logger.info("aliasBeansCache reinited.");
		}

		return this.aliasBeansCache;
	}

	private Map<Integer, RootBean> getRootBeansCache() {
		if (this.rootBeansCache.isEmpty()) {
			List<WordRootMap> wordRootMaps = this.modelService.getAllWordRootMaps();
			List<RootAliasMap> rootAliasMaps = this.modelService.getAllRootAliasMaps();

			for (WordRootMap map : wordRootMaps) {
				this.rootBeansCache.putIfAbsent(map.getRoot().getId(), new RootBean(map.getRoot(), new ArrayList<>(), new ArrayList<>()));
				this.rootBeansCache.get(map.getRoot().getId()).getWordRootMaps().add(map);
			}

			for (RootAliasMap map : rootAliasMaps) {
				this.rootBeansCache.putIfAbsent(map.getRoot().getId(), new RootBean(map.getRoot(), new ArrayList<>(), new ArrayList<>()));
				this.rootBeansCache.get(map.getRoot().getId()).getRootAliasMaps().add(map);
			}
			logger.info("rootBeansCache reinited.");
		}

		return this.rootBeansCache;
	}

	private Map<Integer, WordBean> getWordBeansCache() {
		if (this.wordBeansCache.isEmpty()) {
			List<WordRootMap> maps = this.modelService.getAllWordRootMaps();

			for (WordRootMap map : maps) {
				this.wordBeansCache.putIfAbsent(map.getWord().getId(), new WordBean(map.getWord(), new ArrayList<>()));
				this.wordBeansCache.get(map.getWord().getId()).getWordRootMaps().add(map);
			}
			logger.info("wordBeansCache reinited.");
		}

		return this.wordBeansCache;
	}

	public List<AliasBean> getAllAliasBeans() {
		return new ArrayList<>(this.getAliasBeansCache().values());
	}

	public List<RootBean> getAllRootBeans() {
		return new ArrayList<>(this.getRootBeansCache().values());
	}
	
	public RootBean getRootBean(Integer rootId) throws ResourceNotFoundException {
		RootBean rootBean = this.getRootBeansCache().get(rootId);

		if (rootBean == null) {
			String msg = String.format("Cache invalidated for some reason, received rootID: %s, but not in rootBeansCache, reinitiating rootBeansCache", rootId);
			logger.error(msg);
			throw new ResourceNotFoundException(msg);
		}

		return rootBean;
	}

	public List<WordBean> getAllWordBeans() {
		return new ArrayList<>(this.getWordBeansCache().values());
	}

	public WordBean getWordBean(Integer wordId) throws ResourceNotFoundException {
		WordBean wordBean = this.getWordBeansCache().get(wordId);

		if (wordBean == null) {
			String msg = String.format("Cache invalidated for some reason, received wordID: %s, but not in wordBeansCache, reinitiating wordcache", wordId);
			logger.error(msg);
			throw new ResourceNotFoundException(msg);
		}

		return wordBean;
	}

	@Transactional
	public List<AnalyzeResultBean> analyzeRootForWord(final String wordString) throws ResourceNotFoundException {
		Set<Integer> rootIdSet = new HashSet<>();
		List<AnalyzeResultBean> res = new ArrayList<>();
		Word word = this.modelService.getWordByWordString(wordString);
		
		if (word != null) {
			//If word already in DB, retrieve its current validated roots first.
			WordBean bean = this.getWordBean(word.getId());

			for (WordRootMap map : bean.getWordRootMaps()) {
				rootIdSet.add(map.getRoot().getId());
				res.add(new AnalyzeResultBean(
						map.getRoot().getId(),
						map.getRoot().getRootString(),
						map.getRoot().getRootString(),
						map.getRoot().getMeanings(),
						map.getDescription(),
						true)
						);
			}
		}

		List<AnalyzeResultBean> analysis = new ArrayList<>();

		for (AliasBean bean : this.getAllAliasBeans()) {
			for (RootAliasMap map : bean.getRootAliasMaps()) {
				if (!rootIdSet.contains(map.getRoot().getId()) && this.match(wordString, map.getAlias().getAliasString())) {
					analysis.add(new AnalyzeResultBean(
							map.getRoot().getId(),
							map.getAlias().getAliasString(),
							map.getRoot().getRootString(),
							map.getRoot().getMeanings(),
							"",
							false)
							);
				}
			}
		}
		
		analysis.sort((a, b) -> {
			return a.getRootString().compareTo(b.getRootString());
		}); 
		res.addAll(analysis);
		return res;
	}

	private boolean match(String a, String b) {
		return a.contains(b);
	}
}
