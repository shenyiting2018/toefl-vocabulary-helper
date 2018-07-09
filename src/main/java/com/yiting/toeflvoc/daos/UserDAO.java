package com.yiting.toeflvoc.daos;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yiting.toeflvoc.models.Role;
import com.yiting.toeflvoc.models.User;
import com.yiting.toeflvoc.repos.RoleRepositoryInterface;
import com.yiting.toeflvoc.repos.UserRepositoryInterface;

@Repository
public class UserDAO {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private UserRepositoryInterface userRepo;
	
	@Autowired
	private RoleRepositoryInterface roleRepo;
	
	private static final String GET_USER_BY_EMAIL = "from User where email = :email";
	private static final String GET_ROLE_BY_NAME = "from Role where role = :role";

	public User getUserByEmail(String email) throws NoResultException {
		return (User) this.entityManager.createQuery(GET_USER_BY_EMAIL)
				.setParameter("email", email)
				.getSingleResult();
	}

	public Role getRoleByName(String role) throws NoResultException {
		return (Role) this.entityManager.createQuery(GET_ROLE_BY_NAME)
				.setParameter("role", role)
				.getSingleResult();
	}
	
	public void saveUser(User user) {
		this.userRepo.save(user);
	}
}
