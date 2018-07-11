package com.yiting.toeflvoc.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yiting.toeflvoc.models.Category;
import com.yiting.toeflvoc.models.User;
import com.yiting.toeflvoc.repos.CategoryRepositoryInterface;

@Repository
@SuppressWarnings("unchecked")
public class CategoryDAO {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private CategoryRepositoryInterface categoryRepo;

	private static final String GET_CATEGORY_BY_ID = "from Category where id = :id";	
	private static final String GET_CATEGORY_BY_USER = "from Category where user.id = :userId";
	private static final String GET_CATEGORY_BY_USER_AND_NAME = "from Category where user.id = :userId and categoryName = :categoryName";

	
	public List<Category> getAllCategorys() {
		List<Category> Categorys = (List<Category>) categoryRepo.findAll();
		return Categorys;
	}

	public Category getCategoryById(Integer id) {
		Category Category = (Category) this.entityManager.createQuery(GET_CATEGORY_BY_ID)
				.setParameter("id", id)
				.getSingleResult();
		return Category;
	}
	
	public List<Category> getCategoryByUserId(Integer userId) throws NoResultException{
		List<Category> Category = this.entityManager.createQuery(GET_CATEGORY_BY_USER)
				.setParameter("userId", userId)
				.getResultList();
		return Category;
	}
	
	public Category getCategoryByUserIdAndName(Integer userId, String categoryName) throws NoResultException {
		Category Category = (Category) this.entityManager.createQuery(GET_CATEGORY_BY_USER_AND_NAME)
				.setParameter("userId", userId)
				.setParameter("categoryName", categoryName)
				.getSingleResult();
		return Category;
	}
	
	public Category addCategory(String categoryName, User user) {
		Category category = new Category(user, categoryName);
		this.save(category, 0);
		return category;
	}
	
	public Category save(Category category, int count) {
		if (count % 25 == 0) {
			this.categoryRepo.saveAndFlush(category);
		} else {
			this.categoryRepo.save(category);
		}
		
		return category;
	}
}
