package com.naveen.project1.Service;

import com.naveen.project1.Exceptions.APIException;
import com.naveen.project1.Exceptions.ResourceNotFoundException;
import com.naveen.project1.Model.Category;
import com.naveen.project1.Model.Product;
import com.naveen.project1.Repository.CategoryRepository;
import com.naveen.project1.Repository.ProductRepository;
import com.naveen.project1.payload.ProductDTO;
import com.naveen.project1.payload.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService
{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FilesService filesService;

    @Value("${project.image}")   //----->It is in Application.properties
    private String path;


    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));

        //Validation ---> Product is Present Or Not
        boolean isProductNotPresent = true;
        List<Product> products = category.getProducts();
        for (Product value : products) {
            if (value.getProductName().equals(productDTO.getProductName())) {
                isProductNotPresent = false;
                break;
            }
        }
        if(isProductNotPresent) {
            Product product = modelMapper.map(productDTO, Product.class);
            product.setImage("default.png");
            product.setCategory(category);
            double specialPrice = product.getPrice() -
                    ((product.getDiscount() * 0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        }else{
            throw new APIException("product Already Exists!!");
        }
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder)
    {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> productPage = productRepository.findAll(pageDetails);
        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        //Product size is 0 0r not
//        if(products.isEmpty())
//        {
//            throw new APIException("No Products is Present !!");
//        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(category,pageDetails);
        List<Product> products = productPage.getContent();

        if(products.isEmpty())
        {
            throw new APIException(category.getCategoryName() + "category does not have any products");
        }

        List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%',pageDetails);
        List<Product> products=productPage.getContent();
        List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        if(products.isEmpty())
        {
            throw new APIException("Keyword Not Found with Keyword: " + keyword);
        }

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;

    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO)
    {
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId ));
        Product product = modelMapper.map(productDTO,Product.class);
        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setSpecialPrice(product.getSpecialPrice());
        Product savedproduct = productRepository.save(productFromDb);
        return modelMapper.map(savedproduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId)
    {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId ));
        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {

        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product","productId",productId ));

        //Uplaod Image to server & set the file name of uploaded image
        String fileName =filesService.uploadImage(path,image);

        //Updating the new filename to the product
        productFromDb.setImage(fileName);

        //save updated product
        Product updatedproduct = productRepository.save(productFromDb);

        //return DTO after mapping product to DTO
        return modelMapper.map(updatedproduct, ProductDTO.class);

    }


}
