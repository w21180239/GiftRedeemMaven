package com.wej.giftredeem.gift;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class GiftServiceTest {

    @Autowired
    private GiftRepository giftRepository;

    @Autowired
    private GiftExchangeRecordRepository giftExchangeRecordRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private GiftService giftService;

    @BeforeEach
    public void setUp() {
        giftService = new GiftService(giftRepository, giftExchangeRecordRepository);
    }

    @Test
    public void testGetGiftByGiftNo() {
        Gift gift = new Gift();
        gift.setGiftNo("1001");
        gift.setStock(10);

        // Persist the gift entity into H2 database
        testEntityManager.persist(gift);

        Gift result = giftService.getGiftByGiftNo("1001");

        assertNotNull(result);
        assertEquals("1001", result.getGiftNo());
        assertEquals(10, result.getStock());
    }

    @Test
    public void testReduceStock() {
        Gift gift = new Gift();
        gift.setGiftNo("1002");
        gift.setStock(5);

        // Persist the gift entity into H2 database
        testEntityManager.persist(gift);

        giftService.reduceStock("1002", "cus123", "txn456");

        Gift updatedGift = giftRepository.findByGiftNo("1002");
        assertEquals(4, updatedGift.getStock());
    }

    @Test
    public void testReduceStock_GiftOutOfStock() {
        Gift gift = new Gift();
        gift.setGiftNo("1003");
        gift.setStock(0);

        // Persist the gift entity into H2 database
        testEntityManager.persist(gift);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            giftService.reduceStock("1003", "cus123", "txn456");
        });

        assertEquals("Gift out of stock!", exception.getMessage());
    }

    @Test
    public void testReduceStock_GiftNotFound() {
        Gift gift = new Gift();
        gift.setGiftNo("1003");
        gift.setStock(5);

        // Persist the gift entity into H2 database
        testEntityManager.persist(gift);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            giftService.reduceStock("1004", "cus123", "txn456");
        });

        assertEquals("Gift out of stock!", exception.getMessage());
    }

    @Test
    public void testReduceStock_ExchangeRecordRecorded() {
        Gift gift = new Gift();
        gift.setGiftNo("1002");
        gift.setStock(5);

        // Persist the gift entity into H2 database
        testEntityManager.persist(gift);

        giftService.reduceStock("1002", "cus123", "txn456");

        GiftExchangeRecord exchangeRecord = giftExchangeRecordRepository.findByTxnNo("txn456");
        assertNotNull(exchangeRecord);
        assertEquals("cus123", exchangeRecord.getCusNo());
        assertEquals("1002", exchangeRecord.getGiftNo());
    }

}
