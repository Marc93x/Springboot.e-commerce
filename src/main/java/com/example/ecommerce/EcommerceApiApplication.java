package com.example.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.ecommerce") // Explicitement scan le package et sous-packages
public class EcommerceApiApplication {

    private static final Logger logger = LoggerFactory.getLogger(EcommerceApiApplication.class);

    public static void main(String[] args) {
        logger.info("Démarrage de l'application E-commerce API...");
        SpringApplication.run(EcommerceApiApplication.class, args);
        logger.info("Application démarrée avec succès !");
    }
}

// Gestion globale des erreurs
@ControllerAdvice
class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        logger.error("Erreur détectée : " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Mauvaise requête : " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
