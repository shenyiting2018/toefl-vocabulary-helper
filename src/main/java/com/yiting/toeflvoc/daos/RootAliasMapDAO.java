package com.yiting.toeflvoc.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yiting.toeflvoc.models.Alias;
import com.yiting.toeflvoc.models.Root;
import com.yiting.toeflvoc.models.RootAliasMap;
import com.yiting.toeflvoc.repos.RootAliasMapRepositoryInterface;

@Repository
public class RootAliasMapDAO {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private RootAliasMapRepositoryInterface rootAliasMapRepo;

	private static final String GET_ROOT_ALIAS_MAP_BY_ROOT_ID_AND_ALIAS_ID = "from RootAliasMap where root.id = :rootId and alias.id = :aliasId";
	
	public RootAliasMap getRootAliasMapByRootIdAndAlias(Integer rootId, Integer aliasId) throws NoResultException {
		return (RootAliasMap) this.entityManager.createQuery(GET_ROOT_ALIAS_MAP_BY_ROOT_ID_AND_ALIAS_ID)
				.setParameter("rootId", rootId)
				.setParameter("aliasId", aliasId)
				.getSingleResult();
	}
	
	public List<RootAliasMap> getAllRootAliasMaps() {
		List<RootAliasMap> rootAliasMap = (List<RootAliasMap>) rootAliasMapRepo.findAll();
		return rootAliasMap;
	}
	
	public RootAliasMap addRoot(Root root, Alias alias, String description) {
		RootAliasMap rootAliasMap = new RootAliasMap();
		rootAliasMap.setRoot(root);
		rootAliasMap.setAlias(alias);
		
		this.rootAliasMapRepo.save(rootAliasMap);
		return rootAliasMap;
	}
}
