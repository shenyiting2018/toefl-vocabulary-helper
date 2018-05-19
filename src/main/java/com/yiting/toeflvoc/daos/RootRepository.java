package com.yiting.toeflvoc.daos;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yiting.toeflvoc.models.Root;
import com.yiting.toeflvoc.repos.RootRepositoryInterface;

@Repository
public class RootRepository {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private RootRepositoryInterface rootRepo;

	private static final String GET_ROOT_BY_ID = "SELECT Root FROM Root WHERE id = :id";	
	private static final String GET_ROOT_BY_ROOT_STRING = "SELECT Root FROM Root WHERE rootString = :rootString";

	public List<Root> getAllRoots() {
		List<Root> courses = (List<Root>) rootRepo.findAll();
		return courses;
	}

	public Root getRootById(Integer id) {
		Root root = (Root) this.entityManager.createQuery(GET_ROOT_BY_ID)
				.setParameter("id", id)
				.getSingleResult();
		return root;
	}

	public Root getRootByRootString(String rootString) {
		Root root = (Root) this.entityManager.createQuery(GET_ROOT_BY_ROOT_STRING)
				.setParameter("rootString", rootString)
				.getSingleResult();
		return root;
	}
	
	public Root addRoot(String rootString, List<String> meaning) {
		Root root = new Root();
		root.setRootString(rootString);
		root.setMeaning(meaning);
		this.rootRepo.save(root);
		return root;
	}
	
	public void save(Root root) {
		this.rootRepo.save(root);
	}
}
