package com.naveen.project1.Service;

import com.naveen.project1.payload.ProductDTO;
import com.naveen.project1.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface
ProductService {


    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);


    ProductResponse getAllProducts(Integer PageNumber,Integer PageSize,String sortBy,String sortOrder);

    ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);


    ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
