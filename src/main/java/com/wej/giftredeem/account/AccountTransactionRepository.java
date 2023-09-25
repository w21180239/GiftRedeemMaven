package com.wej.giftredeem.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {
    AccountTransaction findByTxnNo(String txnNo);
}
