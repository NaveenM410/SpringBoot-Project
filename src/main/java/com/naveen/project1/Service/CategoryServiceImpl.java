package com.naveen.project1.Service;

import com.naveen.project1.Exceptions.APIException;
import com.naveen.project1.Model.Category;
import com.naveen.project1.Repository.CategoryRepository;
import com.naveen.project1.payload.CategoryDTO;
import com.naveen.project1.payload.CategoryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder)
    {
        //The below operation performs Sorting

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        //The below Pageable is a interface to perform pagination operation
        //The pagerequest is a class to get the details of pagenumber,pagesize and sorting to store in pagedetails object.
        Pageable pagedetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);//get the category for sorting pagination
        Page<Category> categoryPage = categoryRepository.findAll(pagedetails);
        List<Category> categories=categoryPage.getContent();//the page object data is transferred to category object

        if(categories.isEmpty())
            throw new APIException("No Category Created till now");

        List<CategoryDTO> categoryDTOS = categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        //setting the data to category Response

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());

        return categoryResponse;
    }

    @Override
    public CategoryDTO createcategory(CategoryDTO categoryDTO)
    {
        //we are checking the particular category is present in db or not thats y we r using category object
        Category category = modelMapper.map(categoryDTO,Category.class);

        //the below line to check the catgory is already exist or not
        Category categoryFromdb = categoryRepository.findByCategoryName(category.getCategoryName());

        if(categoryFromdb!=null)
            throw new APIException("Category with the name " + category.getCategoryName()+ " already exists!!");
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);
    }

    @Override
    public CategoryDTO deletecategory(Long categoryId)
    {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));
        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updatecategory(CategoryDTO categoryDTO, Long categoryId)
    {
        Category category = modelMapper.map(categoryDTO,Category.class);
        Category savedcategory = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));
        category.setCategoryId(categoryId);
        savedcategory=categoryRepository.save(category);
        return modelMapper.map(savedcategory,CategoryDTO.class);

    }


}
