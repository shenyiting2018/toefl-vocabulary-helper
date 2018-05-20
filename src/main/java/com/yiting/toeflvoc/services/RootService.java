package com.yiting.toeflvoc.services;

import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yiting.toeflvoc.daos.RootDAO;
import com.yiting.toeflvoc.models.Root;

@Service
public class RootService {
	@Autowired
	private RootDAO rootDAO;
    private final Logger logger = LoggerFactory.getLogger(RootService.class);

	@Transactional
	public Root getRootByRootString(String rootString) {
		Root root = null;
		try {
			root = this.rootDAO.getRootByRootString(rootString); 
		} catch (NoResultException e) {
			//TODO 
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
	public void save(Root root) {
		this.rootDAO.save(root);
	}
}
