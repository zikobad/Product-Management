package com.alten.product.management;

import com.alten.product.management.dto.ProductRequest;
import com.alten.product.management.entity.InventoryStatus;
import com.alten.product.management.entity.Product;
import com.alten.product.management.exception.ResourceNotFoundException;
import com.alten.product.management.repository.ProductRepository;
import com.alten.product.management.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductManagementApplicationTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(1L);
        product.setCode("P001");
        product.setName("Product 1");
        product.setDescription("Description 1");
        product.setImage("image_url_1");
        product.setCategory("Category 1");
        product.setPrice(100.0);
        product.setQuantity(10);
        product.setInternalReference("IR001");
        product.setShellId(1L);
        product.setInventoryStatus(InventoryStatus.INSTOCK);
        product.setRating(4.0);
    }

    @Test
    void getAllProducts_shouldReturnPageOfProducts() {
        Pageable pageable = Pageable.ofSize(10);
        Page<Product> expectedPage = new PageImpl<>(Collections.singletonList(product), pageable, 1);

        when(productRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Product> result = productService.getAllProducts(pageable);

        assertEquals(expectedPage, result);
        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void getProductById_shouldReturnProduct_whenExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductById_shouldReturnEmpty_whenNotExists() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Product> result = productService.getProductById(2L);

        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(2L);
    }

    @Test
    void createProduct_shouldSaveProduct() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setCode("P002");
        productRequest.setName("Product 2");
        productRequest.setDescription("Description 2");
        productRequest.setImage("image_url_2");
        productRequest.setCategory("Category 2");
        productRequest.setPrice(200.0);
        productRequest.setQuantity(20);
        productRequest.setInternalReference("IR002");
        productRequest.setShellId(1L);
        productRequest.setInventoryStatus(InventoryStatus.OUTOFSTOCK);
        productRequest.setRating(4.0);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.createProduct(productRequest);

        assertNotNull(result);
        assertEquals("P001", result.getCode()); // Validate the code of the created product
        assertEquals("Product 1", result.getName());
        assertEquals("Description 1", result.getDescription());
        assertEquals("image_url_1", result.getImage());
        assertEquals("Category 1", result.getCategory());
        assertEquals(100.0, result.getPrice());
        assertEquals(10, result.getQuantity());
        assertEquals("IR001", result.getInternalReference());
        assertEquals(1L, result.getShellId());
        assertEquals(InventoryStatus.INSTOCK, result.getInventoryStatus());
        assertEquals(4.0, result.getRating());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_shouldUpdateAndReturnProduct_whenExists() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setCode("P001-Updated");
        productRequest.setName("Updated Product");
        productRequest.setDescription("Updated Description");
        productRequest.setImage("updated_image_url");
        productRequest.setCategory("Updated Category");
        productRequest.setPrice(150.0);
        productRequest.setQuantity(15);
        productRequest.setInternalReference("IR001-Updated");
        productRequest.setShellId(2L);
        productRequest.setInventoryStatus(InventoryStatus.LOWSTOCK);
        productRequest.setRating(4.0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.updateProduct(1L, productRequest);

        assertEquals("P001-Updated", result.getCode());
        assertEquals("Updated Product", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals("updated_image_url", result.getImage());
        assertEquals("Updated Category", result.getCategory());
        assertEquals(150.0, result.getPrice());
        assertEquals(15, result.getQuantity());
        assertEquals("IR001-Updated", result.getInternalReference());
        assertEquals(2L, result.getShellId());
        assertEquals(InventoryStatus.LOWSTOCK, result.getInventoryStatus());
        assertEquals(4.0, result.getRating());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_shouldThrowException_whenNotFound() {
        ProductRequest productRequest = new ProductRequest();

        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(2L, productRequest));

        verify(productRepository, times(1)).findById(2L);
    }

    @Test
    void deleteProduct_shouldCallDeleteById_whenProductExists() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
}
