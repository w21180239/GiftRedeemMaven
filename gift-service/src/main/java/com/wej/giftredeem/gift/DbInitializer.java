package com.wej.giftredeem.gift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbInitializer implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DbInitializer.class);

    @Autowired
    private GiftRepository giftRepository;

    @Override
    public void run(String... args) {
        logger.info("Initializing database...");

        // Initialize gift data
        Gift gift = new Gift(null, "1001", 100);
        giftRepository.save(gift);
        logger.info("Gift initialized: {}", gift);

        gift = new Gift(null, "1002", 200);
        giftRepository.save(gift);
        logger.info("Gift initialized: {}", gift);

        logger.info("Database initialization completed.");
    }
}
