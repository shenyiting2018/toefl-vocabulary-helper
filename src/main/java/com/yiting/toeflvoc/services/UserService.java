package com.yiting.toeflvoc.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yiting.toeflvoc.beans.UserBean;
import com.yiting.toeflvoc.daos.UserDAO;
import com.yiting.toeflvoc.models.Category;
import com.yiting.toeflvoc.models.Role;
import com.yiting.toeflvoc.models.User;
import com.yiting.toeflvoc.models.Word;
import com.yiting.toeflvoc.models.WordCategoryMap;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private VocabularyModelService modelService;
	
	private final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private final String SUPERUSER = "superuser";
	private final String INVITATION_CODE = "ADS3WNC2";

	@Transactional(readOnly=false)
	public User registerUser(String email, String password, String retypePassword, String firstName, String lastName, String invitationCode) throws Exception {
		User user = this.getUserByEmail(email);
		
		if (user != null) {
			return user;
		} else {
			if (StringUtils.isBlank(email)
					|| StringUtils.isBlank(password)
					|| StringUtils.isBlank(retypePassword)
					|| !password.equals(retypePassword)
					|| SUPERUSER.equals(email)
					|| !INVITATION_CODE.equals(invitationCode)) {
				throw new Exception("Invalid Input");
			}
			
			user = new User(email, password, firstName, lastName);
			user = this.registerUser(user);
		}
		
		return user;
	}
    
	@Transactional
	private User registerUser(User user) {
		user.setStatusCode(1);
		HashSet<Role> roleSet = new HashSet<>();
		Role userRole = this.getRoleByName("USER");
		roleSet.add(userRole);
		user.setRoles(roleSet);
		
		this.userDao.saveUser(user);
		this.initializeUser(user);
		return user;
	}
	
	@Transactional
	private void initializeUser(User user) {
		User superUser = this.getSuperuser();
		
		List<Category> superUserCategories = this.modelService.getUserCategories(superUser.getId());
		for (Category superUserCategory : superUserCategories) {
			Category category = this.modelService.addCategory(superUserCategory.getCategoryName(), user);
			int count = 0;
			List<Word> superUserCategoryWords = this.modelService.getCategoryWords(category.getCategoryName(), superUser.getId());
			for (Word superUserCategoryWord : superUserCategoryWords) {
				this.modelService.addWordCategoryMap(superUserCategoryWord, category);
				logger.info(String.format("%s", count++));
			}
			
			logger.info(String.format("Initialized %s for user", category.getCategoryName()));
		}
	}
	
	@Transactional
	public Role getRoleByName(String role) {
		return this.userDao.getRoleByName(role);
	}
	
	@Transactional
	public User getSuperuser() {
		return this.getUserByEmail(SUPERUSER);
	}
	
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
