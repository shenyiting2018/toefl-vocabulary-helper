package com.yiting.toeflvoc.daos;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yiting.toeflvoc.repos.RoleRepositoryInterface;

@Repository
public class RoleDAO {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private RoleRepositoryInterface roleRepo;
	
}
