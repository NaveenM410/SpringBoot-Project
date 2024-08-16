package com.naveen.project1.Service;

import com.naveen.project1.Model.Category;
import com.naveen.project1.payload.CategoryDTO;
import com.naveen.project1.payload.CategoryResponse;


import java.util.List;

public interface CategoryService
{


    CategoryDTO updatecategory(CategoryDTO categoryDTO, Long categoryId);

    CategoryResponse getAllCategories(Integer PageNumber,Integer PageSize,String sortBy,String sortOrder);

    CategoryDTO createcategory(CategoryDTO categoryDTO);

    CategoryDTO deletecategory(Long categoryId);
}
