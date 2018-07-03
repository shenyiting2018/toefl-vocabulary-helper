package com.yiting.toeflvoc.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yiting.toeflvoc.models.Category;
import com.yiting.toeflvoc.repos.CategoryRepositoryInterface;

@Repository
public class CategoryDAO {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private CategoryRepositoryInterface categoryRepo;

	private static final String GET_CATEGORY_BY_ID = "from Category where id = :id";	
	private static final String GET_CATEGORY_BY_CATEGORY_NAME = "from Category where categoryName = :categoryName";
	
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

	public Category getCategoryByCategoryString(String categoryName) throws NoResultException{
		return (Category) this.entityManager.createQuery(GET_CATEGORY_BY_CATEGORY_NAME)
				.setParameter("categoryName", categoryName)
				.getSingleResult();
	}
	
}
