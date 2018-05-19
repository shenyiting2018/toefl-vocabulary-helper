package com.yiting.toeflvoc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yiting.toeflvoc.daos.RootRepository;
import com.yiting.toeflvoc.models.Root;

@Service
public class RootService {
	@Autowired
	private RootRepository rootRepo;
	
	@Transactional
	public Root getRootByRootString(String rootString) {
		return this.rootRepo.getRootByRootString(rootString);
	}
	
	@Transactional(readOnly = false)
	public Root addRoot(String rootString, List<String> meaning) {
		Root root = this.getRootByRootString(rootString);
		if (root == null) {
			root = rootRepo.addRoot(rootString, meaning);
		}
		return root;
	}
	
	@Transactional(readOnly = false) 
	public void save(Root root) {
		this.rootRepo.save(root);
	}
}
