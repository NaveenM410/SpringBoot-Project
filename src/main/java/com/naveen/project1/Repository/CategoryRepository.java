package com.naveen.project1.Repository;


import com.naveen.project1.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {


    Category findByCategoryName(String categoryName);
}
