package com.renegz.pnccontroller.services;

import com.renegz.pnccontroller.domain.dtos.SaveCategoryDTO;
import com.renegz.pnccontroller.domain.entities.Category;

import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();
    Category findCategoryByCode(String code);
	void save(@Valid SaveCategoryDTO info);
	void delete(Category category);
}
