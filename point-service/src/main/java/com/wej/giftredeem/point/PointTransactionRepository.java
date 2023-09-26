package com.wej.giftredeem.point;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {
    PointTransaction findByTxnNo(String txnNo);
}
