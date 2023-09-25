package com.wej.giftredeem.gift;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftExchangeRecordRepository extends JpaRepository<GiftExchangeRecord, Long> {
    GiftExchangeRecord findByTxnNo(String txnNo);
}
