package com.wej.giftredeem.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DbInitializer implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(com.wej.giftredeem.gift.DbInitializer.class);


    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void run(String... args) {
        // Initialize account data
        Account account = new Account(null, "100000001", BigDecimal.valueOf(1000));
        accountRepository.save(account);
        logger.info("Account initialized: {}", account);


        account = new Account(null, "100000002", BigDecimal.valueOf(2000));
        accountRepository.save(account);
        logger.info("Account initialized: {}", account);

        logger.info("Database initialization completed.");
    }
}
