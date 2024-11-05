package com.example.breadmap.review.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.breadmap.review.question.DataNotFoundException;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	public void create(String name) {
		Category category = new Category();
		category.setName(name);
		this.categoryRepository.save(category);	
	}
	
	public List<Category> getAll() {
		return this.categoryRepository.findAll();
	}
	
	public Category getCategoryByName(String name) {
		Optional<Category> oc = this.categoryRepository.findByName(name);
        if (oc.isPresent()) {
            return oc.get();
        } else {
            throw new DataNotFoundException("category not found");
        }
		
	}
}
