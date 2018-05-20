package com.yiting.toeflvoc.services;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yiting.toeflvoc.daos.AliasDAO;
import com.yiting.toeflvoc.models.Alias;

@Service
public class AliasService {
	@Autowired
	private AliasDAO aliasDAO;
    private final Logger logger = LoggerFactory.getLogger(AliasService.class);

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
	public void save(Alias alias) {
		this.aliasDAO.save(alias);
	}
}
