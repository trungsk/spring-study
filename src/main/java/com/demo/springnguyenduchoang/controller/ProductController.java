package com.demo.springnguyenduchoang.controller;

import com.demo.springnguyenduchoang.model.Product;
import com.demo.springnguyenduchoang.model.ResponseObject;
import com.demo.springnguyenduchoang.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping("")
    List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "query successfully", product))
                :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("FAILED", "Not found", ""));
    }

    @PostMapping("/insertProduct")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product product) {
        List<Product> products = productRepository.findByProductName(product.getProductName().trim());
        if (products.size() != 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("FAILED", "product has already existed", null)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "inserted successfully", productRepository.save(product))
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product inputProd, @PathVariable Long id) {
        Product updatedProd = productRepository.findById(id)
                .map(prod -> {
                    prod.setProductName(inputProd.getProductName());
                    prod.setYear(inputProd.getYear());
                    prod.setPrice(inputProd.getPrice());
                    return productRepository.save(prod);
                }).orElseGet(() -> {
                    inputProd.setId(id);
                    return productRepository.save(inputProd);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "update successfully", updatedProd)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProd(@PathVariable Long id) {
        boolean exist = productRepository.existsById(id);
        if (exist) {
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "delete successfully", "")
            );
        }

        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("FAILED", "Not found", "")
            );
        }
    }

}
