package com.yiting.toeflvoc.daos;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yiting.toeflvoc.models.User;
import com.yiting.toeflvoc.repos.UserRepositoryInterface;

@Repository
public class UserDAO {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private UserRepositoryInterface userRepo;
	
	private static final String GET_USER_BY_EMAIL = "from User where email = :email";
	
	public User getUserByEmail(String email) throws NoResultException{
		return (User) this.entityManager.createQuery(GET_USER_BY_EMAIL)
				.setParameter("email", email)
				.getSingleResult();
	}

}
