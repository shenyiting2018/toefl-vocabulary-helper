package com.yiting.toeflvoc.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yiting.toeflvoc.beans.AliasBean;
import com.yiting.toeflvoc.beans.AnalyzeResultBean;
import com.yiting.toeflvoc.beans.RootBean;
import com.yiting.toeflvoc.beans.UserWordBean;
import com.yiting.toeflvoc.beans.WordBean;
import com.yiting.toeflvoc.models.Category;
import com.yiting.toeflvoc.models.RootAliasMap;
import com.yiting.toeflvoc.models.Word;
import com.yiting.toeflvoc.models.WordCategoryMap;
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
			long t1 = System.currentTimeMillis();

			List<RootAliasMap> maps = this.modelService.getAllRootAliasMaps();

			for (RootAliasMap map : maps) {
				this.aliasBeansCache.putIfAbsent(map.getAlias().getId(), new AliasBean(map.getAlias(), new ArrayList<>()));
				this.aliasBeansCache.get(map.getAlias().getId()).getRootAliasMaps().add(map);
			}
			logger.info(String.format("alisBeanCache reinited in %d milliseconds.", System.currentTimeMillis() - t1));
		}

		return this.aliasBeansCache;
	}

	private Map<Integer, RootBean> getRootBeansCache() {
		if (this.rootBeansCache.isEmpty()) {
			long t1 = System.currentTimeMillis();
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

			logger.info(String.format("rootBeansCache reinited in %d milliseconds.", System.currentTimeMillis() - t1));
		}

		return this.rootBeansCache;
	}

	public void invalideCache() {
		this.aliasBeansCache.clear();
		this.rootBeansCache.clear();
		this.wordBeansCache.clear();

		//logger.info("All cache cleared");
	}

	@Transactional(readOnly=true)
	private Map<Integer, WordBean> getWordBeansCache() {
		if (this.wordBeansCache.isEmpty()) {
			long t1 = System.currentTimeMillis();
			List<Word> allWords = this.modelService.getAllWords();

			for (int i = 0; i < allWords.size(); i++) {
				Word word = allWords.get(i);
				List<WordRootMap> wordRootMaps = this.modelService.getWordRootMapsByWord(word.getId());
				//List<WordCategoryMap> wordCategoryMap = this.modelService.getWordCategoryMapByWord(word.getId());

				this.wordBeansCache.putIfAbsent(word.getId(), new WordBean(word, wordRootMaps, null));
			}

			logger.info(String.format("wordBeansCache reinited in %d milliseconds.", System.currentTimeMillis() - t1));
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
			logger.info(msg);
			throw new ResourceNotFoundException(msg);
		}

		return rootBean;
	}

	public List<WordBean> getAllWordBeans() {
		return new ArrayList<>(this.getWordBeansCache().values());
	}

	public WordBean getWordBean(Integer wordId) throws ResourceNotFoundException {
		//WordBean wordBean = this.getWordBeansCache().get(wordId);
		Word word = this.modelService.getWord(wordId);
		if (word == null) {
			return null;
		} else {
			List<WordRootMap> wordRootMaps = this.modelService.getWordRootMapsByWord(word.getId());
			List<WordCategoryMap> wordCategoryMaps = this.modelService.getWordCategoryMapByWord(word.getId());
			return new WordBean(word, wordRootMaps, wordCategoryMaps);
		}
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

	/*@Transactional
	public List<WordBean> getCategoryWordBeans(String categoryName) {		
		List<WordBean> wordBeans = new ArrayList<>();
		Category category = this.modelService.getCategoryByCategoryName(categoryName);

		List<Word> words = new ArrayList<>();
		if (category != null) {
			List<WordCategoryMap> maps = this.modelService.getWordCategoryMapByCategory(category.getId());
			words = maps.stream()
					.map(map -> map.getWord())
					.sorted((a, b) -> {
						return a.getWordString().compareTo(b.getWordString());
					})
					.collect(Collectors.toList());

			for (Word word : words) {
				List<WordRootMap> wordRootMaps = this.modelService.getWordRootMapsByWord(word.getId());
				wordBeans.add(new WordBean(word, wordRootMaps, null));
			}
		}

		return wordBeans;
	}*/

	public List<UserWordBean> getCategoryWordBeans(String categoryName, Integer userId) {
		List<UserWordBean> userWords = new ArrayList<>();
		Category category = this.modelService.getCategoryByCategoryNameAndUser(categoryName, userId);

		if (category != null) {
			List<WordCategoryMap> maps = this.modelService.getWordCategoryMapByCategory(category.getId());
			userWords = maps.stream()
					.map(map -> new UserWordBean(map.getWord(), map.getCategory(), map.getProficiency(), map.getListNumber()))
					.sorted((a, b) -> {
						return a.getWordString().compareTo(b.getWordString());
					})
					.collect(Collectors.toList());

		}

		return userWords;
	}

	public List<UserWordBean> getGREAdditionalWordBeans(Integer userId) {
		List<UserWordBean> userWords = new ArrayList<>();
		List<Category> categories = new ArrayList<>();

		Category greBlanksCategory = this.modelService.getCategoryByCategoryNameAndUser("gre-blanks", userId);
		if (greBlanksCategory != null) categories.add(greBlanksCategory);

		Category greReadingsCategory = this.modelService.getCategoryByCategoryNameAndUser("gre-readings", userId);
		if (greReadingsCategory != null) categories.add(greReadingsCategory);

		Category greRootExpansaionCategory = this.modelService.getCategoryByCategoryNameAndUser("root-expansion", userId);
		if (greRootExpansaionCategory != null) categories.add(greRootExpansaionCategory);

		for (Category category : categories) {
			List<WordCategoryMap> maps = this.modelService.getWordCategoryMapByCategory(category.getId());
			userWords.addAll(maps.stream()
					.map(map -> new UserWordBean(map.getWord(), map.getCategory(), map.getProficiency(), map.getListNumber()))
					.sorted((a, b) -> {
						return a.getWordString().compareTo(b.getWordString());
					})
					.collect(Collectors.toList()));


		}

		return userWords;
	}

}
