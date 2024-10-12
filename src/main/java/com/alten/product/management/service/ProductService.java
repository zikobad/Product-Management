package com.alten.product.management.service;

import com.alten.product.management.dto.ProductRequest;
import com.alten.product.management.entity.Product;
import com.alten.product.management.exception.ResourceNotFoundException;
import com.alten.product.management.repository.ProductRepository;
import com.alten.product.management.utils.AppUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setCode(productRequest.getCode());
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setImage(productRequest.getImage());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setInternalReference(productRequest.getInternalReference());
        product.setShellId(productRequest.getShellId());
        product.setInventoryStatus(productRequest.getInventoryStatus());
        product.setRating(productRequest.getRating());
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        AppUtils.updateIfNotNull(product::setCode, productRequest.getCode());
        AppUtils.updateIfNotNull(product::setName, productRequest.getName());
        AppUtils.updateIfNotNull(product::setDescription, productRequest.getDescription());
        AppUtils.updateIfNotNull(product::setImage, productRequest.getImage());
        AppUtils.updateIfNotNull(product::setCategory, productRequest.getCategory());
        AppUtils.updateIfNotNull(product::setPrice, productRequest.getPrice());
        AppUtils.updateIfNotNull(product::setQuantity, productRequest.getQuantity());
        AppUtils.updateIfNotNull(product::setInternalReference, productRequest.getInternalReference());
        AppUtils.updateIfNotNull(product::setShellId, productRequest.getShellId());
        AppUtils.updateIfNotNull(product::setInventoryStatus, productRequest.getInventoryStatus());
        AppUtils.updateIfNotNull(product::setRating, productRequest.getRating());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
