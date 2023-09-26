package com.wej.giftredeem.account;

import com.wej.giftredeem.account.dto.DeductBalanceRequest;
import com.wej.giftredeem.account.dto.DeductBalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/deductBalance")
    public ResponseEntity<DeductBalanceResponse> deductBalance(@RequestBody DeductBalanceRequest request) {
        try {
            accountService.deductBalance(request.getCusNo(), request.getAmount(), request.getTxnNo());
            return ResponseEntity.ok(new DeductBalanceResponse("200", "Balance deducted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new DeductBalanceResponse("400", e.getMessage()));
        }
    }
}
