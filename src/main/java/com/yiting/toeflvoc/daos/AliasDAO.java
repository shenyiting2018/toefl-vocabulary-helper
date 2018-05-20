package com.yiting.toeflvoc.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yiting.toeflvoc.models.Alias;
import com.yiting.toeflvoc.repos.AliasRepositoryInterface;

@Repository
public class AliasDAO {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private AliasRepositoryInterface aliasRepo;

	private static final String GET_ALIAS_BY_ID = "SELECT Alias FROM Alias WHERE id = :id";	
	private static final String GET_ALIAS_BY_ALIAS_STRING = "from Alias where aliasString = :aliasString";

	public List<Alias> getAllAliass() {
		List<Alias> aliases = (List<Alias>) aliasRepo.findAll();
		return aliases;
	}

	public Alias getAliasById(Integer id) {
		Alias alias = (Alias) this.entityManager.createQuery(GET_ALIAS_BY_ID)
				.setParameter("id", id)
				.getSingleResult();
		return alias;
	}

	public Alias getAliasByAliasString(String aliasString) throws NoResultException{
		return (Alias) this.entityManager.createQuery(GET_ALIAS_BY_ALIAS_STRING)
				.setParameter("aliasString", aliasString)
				.getSingleResult();
	}
	
	public Alias addAlias(String aliasString) {
		Alias alias = new Alias();
		alias.setAliasString(aliasString);
		this.aliasRepo.save(alias);
		return alias;
	}
	
	public void save(Alias alias) {
		this.aliasRepo.save(alias);
	}
}
