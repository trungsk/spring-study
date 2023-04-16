//package com.demo.springnguyenduchoang.database;
//
//import com.demo.springnguyenduchoang.model.Product;
//import com.demo.springnguyenduchoang.repository.ProductRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class Database {
//    private static final Logger LOGGER = LoggerFactory.getLogger(Database.class);
//    @Bean
//    CommandLineRunner initDB(ProductRepository productRepository){
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//                Product product1 = new Product( "samsung", 1998, 5.6);
//                Product product2 = new Product("xiaomi", 1997, 54.6);
//                Product product3 = new Product("nokia", 1999, 5.7);
//                LOGGER.info("insert data: " + productRepository.save(product1));
//                LOGGER.info("insert data: " + productRepository.save(product2));
//                LOGGER.info("insert data: " + productRepository.save(product3));
//            }
//        };
//    }
//}
