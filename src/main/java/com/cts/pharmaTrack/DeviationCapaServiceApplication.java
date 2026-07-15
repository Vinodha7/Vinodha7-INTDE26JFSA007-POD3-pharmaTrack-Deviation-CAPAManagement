package com.cts.pharmaTrack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Service entry point. The application sits in the com.cts.pharmaTrack root
 * package, so component scanning, JPA entity scanning and repository scanning
 * all default to this package and its subpackages (module code + shared lib).
 */
@SpringBootApplication
public class DeviationCapaServiceApplication {
    private static final Logger logger = LoggerFactory.getLogger(DeviationCapaServiceApplication.class);

    public static void main(String[] args) {
        logger.info("Starting DeviationCapaServiceApplication");
        SpringApplication.run(DeviationCapaServiceApplication.class, args);
    }
}