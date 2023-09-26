package com.wej.giftredeem.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountTransactionRepository accountTransactionRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, AccountTransactionRepository accountTransactionRepository) {
        this.accountRepository = accountRepository;
        this.accountTransactionRepository = accountTransactionRepository;
    }

    @Transactional
    public void deductBalance(String cusNo, BigDecimal amount, String txnNo) {
        Account account = accountRepository.findByCusNo(cusNo);
        if (account != null && account.getBalance().compareTo(amount) >= 0) {
            account.setBalance(account.getBalance().subtract(amount));
            accountRepository.save(account);

            // Insert account transaction record
            AccountTransaction transaction = new AccountTransaction(null, txnNo, cusNo, amount);
            accountTransactionRepository.save(transaction);
        } else {
            throw new RuntimeException("Insufficient balance!");
        }
    }
}
