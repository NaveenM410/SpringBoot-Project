package com.naveen.project1.Controller;

import com.naveen.project1.Service.ProductService;
import com.naveen.project1.config.AppConstants;
import com.naveen.project1.payload.ProductDTO;
import com.naveen.project1.payload.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class ProductController
{
    @Autowired
    private ProductService productService;

    @PostMapping("/api/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long categoryId)
    {
        ProductDTO savedProductDTO = productService.addProduct(categoryId,productDTO);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }

    @GetMapping("/api/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name="pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name="SortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy,
            @RequestParam(name="SortOrder", defaultValue = AppConstants.SORT_DIR,required = false) String sortOrder)
    {
        ProductResponse productResponse = productService.getAllProducts(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/api/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId,
                                                                 @RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                                 @RequestParam(name="pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                                 @RequestParam(name="SortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy,
                                                                 @RequestParam(name="SortOrder", defaultValue = AppConstants.SORT_DIR,required = false) String sortOrder)
    {
        ProductResponse productResponse = productService.searchByCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/api/public/products/keyword/{keyword}")
     public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword,
                                                                 @RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                                 @RequestParam(name="pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                                 @RequestParam(name="SortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy,
                                                                 @RequestParam(name="SortOrder", defaultValue = AppConstants.SORT_DIR,required = false) String sortOrder)
     {
         ProductResponse productResponse = productService.searchProductByKeyword(keyword,pageNumber,pageSize,sortBy,sortOrder);
         return new ResponseEntity<>(productResponse,HttpStatus.FOUND);
     }

     @PutMapping("/api/products/{productId}")
      public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO,@PathVariable Long productId)
      {
          ProductDTO updatedProductDTO= productService.updateProduct(productId,productDTO);
          return new ResponseEntity<>(updatedProductDTO,HttpStatus.OK);
      }

      @DeleteMapping("/api/admin/products/{productId}")
      public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId)
      {
          ProductDTO deleteProduct = productService.deleteProduct(productId);
          return new ResponseEntity<>(deleteProduct,HttpStatus.OK);
      }

    @PutMapping("/api/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                         @RequestParam("image")MultipartFile image) throws IOException {
        ProductDTO updatedProductImage = productService.updateProductImage(productId,image);
        return new ResponseEntity<>(updatedProductImage,HttpStatus.OK);
    }


}
