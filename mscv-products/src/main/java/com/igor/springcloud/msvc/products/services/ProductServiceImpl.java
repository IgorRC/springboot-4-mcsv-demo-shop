package com.igor.springcloud.msvc.products.services;

import java.util.List;
import java.util.Optional;

import org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi.ecCVCDSA;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.igor.springcloud.msvc.products.entities.Product;
import com.igor.springcloud.msvc.products.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    
    
    final private ProductRepository productRepository;
    final private Environment env;

    public ProductServiceImpl(ProductRepository productRepository, Environment env) {
        this.productRepository = productRepository;
        this.env = env;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return ((List<Product>)productRepository.findAll()).stream().map(product ->{
            product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
            return product;
        }).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id).map(product ->{
            product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
            return product;
        });
    }

}
