package com.itransform.productservice.controller;


import com.itransform.productservice.exception.ResourceNotFoundException;
import com.itransform.productservice.model.Order;
import com.itransform.productservice.model.Product;
import com.itransform.productservice.repository.OrderdRepository;
import com.itransform.productservice.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderdRepository orderdRepository;


    @PostMapping("/addProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProduct(@RequestBody Product product){
        productRepository.save(product);

    }
    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public void ordered(@RequestBody Order order){
        orderdRepository.save(order);

    }

    @RequestMapping("/findAllProducts")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @RequestMapping("/findAllProductsById/{id}")
    public Optional<Product> getProduct(@PathVariable("id") String id){
        return productRepository.findById(id);
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") String id ,
                                                 @RequestBody Product product) throws ResourceNotFoundException {
        Product product1 = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found" +id));

        product1.setId(product.getId());
        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setPrice(product.getPrice());

        final Product updatedProduct = productRepository.save(product1);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProduct(@PathVariable("id") String id){
        productRepository.deleteById(id);
    }
}
