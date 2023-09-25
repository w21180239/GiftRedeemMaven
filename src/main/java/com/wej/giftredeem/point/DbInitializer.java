package com.wej.giftredeem.point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbInitializer implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(com.wej.giftredeem.gift.DbInitializer.class);


    @Autowired
    private PointRepository pointRepository;


    @Override
    public void run(String... args) {
        logger.info("Initializing database...");

        // Initialize point data
        Point point = new Point(null, "100000001", 1000);
        pointRepository.save(point);
        logger.info("Point initialized: {}", point);


        point = new Point(null, "100000002", 2000);
        pointRepository.save(point);
        logger.info("Point initialized: {}", point);

        logger.info("Database initialization completed.");
    }
}
