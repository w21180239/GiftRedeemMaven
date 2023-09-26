package com.wej.giftredeem.gift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GiftService {

    private final GiftRepository giftRepository;
    private final GiftExchangeRecordRepository giftExchangeRecordRepository;

    @Autowired
    public GiftService(GiftRepository giftRepository, GiftExchangeRecordRepository giftExchangeRecordRepository) {
        this.giftRepository = giftRepository;
        this.giftExchangeRecordRepository = giftExchangeRecordRepository;
    }

    public Gift getGiftByGiftNo(String giftNo) {
        return giftRepository.findByGiftNo(giftNo);
    }

    @Transactional
    public void reduceStock(String giftNo, String cusNo, String txnNo) {
        Gift gift = giftRepository.findByGiftNo(giftNo);
        if (gift != null && gift.getStock() > 0) {
            gift.setStock(gift.getStock() - 1);
            giftRepository.save(gift);

            // Insert exchange record
            GiftExchangeRecord record = new GiftExchangeRecord(null, txnNo, cusNo, giftNo);
            giftExchangeRecordRepository.save(record);
        } else {
            throw new RuntimeException("Gift out of stock!");
        }
    }
}
