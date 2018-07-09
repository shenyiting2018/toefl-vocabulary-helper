package com.yiting.toeflvoc.services;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yiting.toeflvoc.beans.UserBean;
import com.yiting.toeflvoc.daos.UserDAO;
import com.yiting.toeflvoc.models.Role;
import com.yiting.toeflvoc.models.User;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserDAO userDao;
	
	private final Logger logger = LoggerFactory.getLogger(UserService.class);
	/*
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;*/
    
	@Transactional
	public User getUserByEmail(String email) {
		User user = null;
		try {
			user = userDao.getUserByEmail(email);
		} catch(NoResultException e) {
			logger.debug(String.format("User: %s not found", email));
		}
		
		return user;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.getUserByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        
		//UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
		UserDetails userDetails = new UserBean(
				user.getId(), 
				user.getEmail(),
				user.getPassword(), 
				user.getFirstName(),
				user.getLastName(),
				user.getStatusCode(),
				grantedAuthorities);

		return userDetails;		
	}
}
