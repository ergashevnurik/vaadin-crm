package com.vaadin.crm.backend.Products.services;

import com.vaadin.crm.backend.Products.entity.Products;
import com.vaadin.crm.backend.Products.exception.ProductNotFoundException;
import com.vaadin.crm.backend.Products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    @Autowired
    private ProductRepository productRepository;

    public List<Products> getAllProducts() {
        return (List<Products>) productRepository.findAll();
    }

    public List<Products> getAllProducts(String filter) {
        if (filter != null && !filter.isEmpty()) {
            return productRepository.search(filter);
        } else {
            return (List<Products>) productRepository.findAll();
        }
    }

    public Products saveProducts(Products products) {
        return productRepository.save(products);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void updateProductDetials(Long id, Products productDetails) {
        Products product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("This Product with this ID: " + id + " Not Found"));
        product.setProductName(productDetails.getProductName());
        product.setUnitPrice(productDetails.getUnitPrice());
        productRepository.save(product);
    }

}
