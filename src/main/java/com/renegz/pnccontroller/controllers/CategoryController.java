package com.renegz.pnccontroller.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renegz.pnccontroller.domain.dtos.GeneralResponse;
import com.renegz.pnccontroller.domain.dtos.SaveCategoryDTO;
import com.renegz.pnccontroller.domain.entities.Book;
import com.renegz.pnccontroller.domain.entities.Category;
import com.renegz.pnccontroller.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/all")
    public ResponseEntity<GeneralResponse> findAllCategories(){
        return GeneralResponse.getResponse(
                categoryService.findAllCategories()
        );
    }
	
	@PostMapping("/save")
	public ResponseEntity<GeneralResponse> saveCategory(@RequestBody @Valid SaveCategoryDTO info){
		
		categoryService.save(info);
		return GeneralResponse.getResponse(HttpStatus.OK, "Categoria guardada");
		
	}
	
	@DeleteMapping("/delete/{code}")
	public ResponseEntity<GeneralResponse> deleteCategory(@PathVariable("code") String code){
		
		Category category = categoryService.findCategoryByCode(code);
		
		if (category == null){
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND);
        }
		
		categoryService.delete(category);
		
		return GeneralResponse.getResponse(HttpStatus.OK);
		
	}
	
	@GetMapping("/{code}")
    public ResponseEntity<GeneralResponse> findBooksbyCategory(@PathVariable("code") String code){

        Category category = categoryService.findCategoryByCode(code);

        if (category == null){
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND);
        }
        
        return GeneralResponse.getResponse(HttpStatus.OK, category);
        
    }
	
}
