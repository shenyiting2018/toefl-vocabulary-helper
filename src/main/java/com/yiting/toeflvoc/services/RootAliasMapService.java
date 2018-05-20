package com.yiting.toeflvoc.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yiting.toeflvoc.beans.RootAliasMapBean;
import com.yiting.toeflvoc.daos.RootAliasMapDAO;
import com.yiting.toeflvoc.models.Alias;
import com.yiting.toeflvoc.models.Root;
import com.yiting.toeflvoc.models.RootAliasMap;

@Service
public class RootAliasMapService {
	@Autowired
	private RootService rootService;

	@Autowired
	private AliasService aliasService;

	@Autowired
	private RootAliasMapDAO rootAliasMapDao;

	private final Logger logger = LoggerFactory.getLogger(RootAliasMapService.class);

	public List<RootAliasMapBean> getAllRootAliasMapBeans() {
		List<RootAliasMap> models = this.getAllRootAliasMaps();
		List<RootAliasMapBean> beans = 
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
		return beans;
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

}
