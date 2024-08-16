package com.naveen.project1.Controller;

import com.naveen.project1.Model.Category;
import com.naveen.project1.Service.CategoryService;
import com.naveen.project1.config.AppConstants;
import com.naveen.project1.payload.CategoryDTO;
import com.naveen.project1.payload.CategoryResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class CategoryController
{
    @Autowired
    private CategoryService categoryService;



    @GetMapping("/api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name="pageSize", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageSize,
            @RequestParam(name="SortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY,required = false) String sortBy,
            @RequestParam(name="SortOrder", defaultValue = AppConstants.SORT_DIR,required = false) String sortOrder)
    {
        CategoryResponse categoryResponse= categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }

    @PostMapping("/api/public/categories")
    public ResponseEntity<CategoryDTO> createcategory(@Valid @RequestBody CategoryDTO categoryDTO)
    {
        CategoryDTO savedCategoryDTo = categoryService.createcategory(categoryDTO);

        return new ResponseEntity<>(savedCategoryDTo,HttpStatus.CREATED);
    }

    @DeleteMapping("/api/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deletecategory(@PathVariable Long categoryId)
    {
        CategoryDTO deletecategory = categoryService.deletecategory(categoryId);
        return new ResponseEntity<>(deletecategory, HttpStatus.OK);
    }

    @PutMapping("/api/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> Updatecategory(@RequestBody CategoryDTO categoryDTO,@PathVariable Long categoryId)
    {
        CategoryDTO savedcategoryDTO = categoryService.updatecategory(categoryDTO,categoryId);
        return new ResponseEntity<>(savedcategoryDTO,HttpStatus.OK);

    }
}
