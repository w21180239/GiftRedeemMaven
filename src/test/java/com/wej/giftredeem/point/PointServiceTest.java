package com.wej.giftredeem.point;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PointServiceTest {

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private PointTransactionRepository pointTransactionRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private PointService pointService;

    @BeforeEach
    public void setUp() {
        pointService = new PointService(pointRepository, pointTransactionRepository);
    }

    @Test
    public void testDeductPoints() {
        Point point = new Point();
        point.setCusNo("cus1001");
        point.setPoints(1000);

        // Persist the point entity into H2 database
        testEntityManager.persist(point);

        pointService.deductPoints("cus1001", 100, "txn123");

        Point updatedPoint = pointRepository.findByCusNo("cus1001");
        assertEquals(900, updatedPoint.getPoints());
    }

    @Test
    public void testDeductPoints_InsufficientPoints() {
        Point point = new Point();
        point.setCusNo("cus1002");
        point.setPoints(50);

        // Persist the point entity into H2 database
        testEntityManager.persist(point);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            pointService.deductPoints("cus1002", 100, "txn456");
        });

        assertEquals("Insufficient points!", exception.getMessage());
    }

    @Test
    public void testDeductPoints_TransactionRecorded() {
        Point point = new Point();
        point.setCusNo("cus1001");
        point.setPoints(1000);

        // Persist the point entity into H2 database
        testEntityManager.persist(point);

        pointService.deductPoints("cus1001", 100, "txn123");

        PointTransaction transaction = pointTransactionRepository.findByTxnNo("txn123");
        assertNotNull(transaction);
        assertEquals("cus1001", transaction.getCusNo());
        assertEquals(100, transaction.getDeductedPoints());
    }
}
