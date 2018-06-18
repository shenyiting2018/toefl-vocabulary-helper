package com.yiting.toeflvoc.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yiting.toeflvoc.models.Root;
import com.yiting.toeflvoc.repos.RootRepositoryInterface;

@Repository
public class RootDAO {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private RootRepositoryInterface rootRepo;

	private static final String GET_ROOT_BY_ID = "from Root where id = :id";	
	private static final String GET_ROOT_BY_ROOT_STRING = "from Root where rootString = :rootString";

	public List<Root> getAllRoots() {
		List<Root> roots = (List<Root>) rootRepo.findAll();
		return roots;
	}

	public Root getRootById(Integer id) {
		Root root = (Root) this.entityManager.createQuery(GET_ROOT_BY_ID)
				.setParameter("id", id)
				.getSingleResult();
		return root;
	}

	public Root getRootByRootString(String rootString) throws NoResultException{
		return (Root) this.entityManager.createQuery(GET_ROOT_BY_ROOT_STRING)
				.setParameter("rootString", rootString)
				.getSingleResult();
	}
	
	public Root addRoot(String rootString, List<String> meanings) {
		Root root = new Root();
		root.setRootString(rootString);
		root.setMeanings(meanings);
		this.rootRepo.save(root);
		return root;
	}
	
	public void save(Root root) {
		this.rootRepo.save(root);
	}
}
