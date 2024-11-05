package com.example.breadmap.review.category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	Optional<Category> findByName(String subject);

}
