package com.wej.giftredeem.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AccountServiceTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountTransactionRepository accountTransactionRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        accountService = new AccountService(accountRepository, accountTransactionRepository);
    }

    @Test
    public void testDeductBalance() {
        Account account = new Account();
        account.setCusNo("cus1001");
        account.setBalance(new BigDecimal("1000"));

        // Persist the account entity into H2 database
        testEntityManager.persist(account);

        accountService.deductBalance("cus1001", new BigDecimal("100"), "txn123");

        Account updatedAccount = accountRepository.findByCusNo("cus1001");
        assertEquals(new BigDecimal("900"), updatedAccount.getBalance());
    }

    @Test
    public void testDeductBalance_InsufficientBalance() {
        Account account = new Account();
        account.setCusNo("cus1002");
        account.setBalance(new BigDecimal("50"));

        // Persist the account entity into H2 database
        testEntityManager.persist(account);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            accountService.deductBalance("cus1002", new BigDecimal("100"), "txn456");
        });

        assertEquals("Insufficient balance!", exception.getMessage());
    }

    @Test
    public void testDeductBalance_TransactionRecorded() {
        Account account = new Account();
        account.setCusNo("cus1001");
        account.setBalance(new BigDecimal("1000"));

        // Persist the account entity into H2 database
        testEntityManager.persist(account);

        accountService.deductBalance("cus1001", new BigDecimal("100"), "txn123");

        AccountTransaction transaction = accountTransactionRepository.findByTxnNo("txn123");
        assertNotNull(transaction);
        assertEquals("cus1001", transaction.getCusNo());
        assertEquals(new BigDecimal("100"), transaction.getDeductedAmount());
    }

}
