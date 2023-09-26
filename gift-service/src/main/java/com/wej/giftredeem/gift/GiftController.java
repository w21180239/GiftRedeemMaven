package com.wej.giftredeem.gift;

import com.wej.giftredeem.gift.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RestController
@RequestMapping("/gift")
public class GiftController {

    @Autowired
    private GiftService giftService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getGiftByGiftNo")
    public ResponseEntity<GetGiftByGiftNoResponse> getGiftByGiftNo(@RequestParam String giftNo) {
        Gift gift = giftService.getGiftByGiftNo(giftNo);
        if (gift != null) {
            return ResponseEntity.ok(new GetGiftByGiftNoResponse(gift.getGiftNo(), gift.getStock()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/reduceStock")
    public ResponseEntity<ReduceStockResponse> reduceStock(@RequestBody ReduceStockRequest request) {
        try {
            giftService.reduceStock(request.getGiftNo(), request.getCusNo(), request.getTxnNo());
            return ResponseEntity.ok(new ReduceStockResponse("200", "Stock reduced successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ReduceStockResponse("400", e.getMessage()));
        }
    }

    @PostMapping("/redeem")
    @Transactional
    public ResponseEntity<RedeemResponse> redeemGift(@RequestBody RedeemRequest request) {
        try {
            // Check if gift stock is available
            if (giftService.getGiftByGiftNo(request.getGiftNo()).getStock() <= 0) {
                return ResponseEntity.badRequest().body(new RedeemResponse("400", "Gift out of stock!"));
            }

            // Deduct points
            String pointServiceUrl = "http://localhost:8082/point/deductPoints";  // Replace with actual URL
            restTemplate.postForEntity(pointServiceUrl, new DeductPointsRequest(request.getCusNo(), 10, request.getTxnNo()), DeductPointsResponse.class);

            // Deduct balance
            String accountServiceUrl = "http://localhost:8083/account/deductBalance";  // Replace with actual URL
            restTemplate.postForEntity(accountServiceUrl, new DeductBalanceRequest(request.getCusNo(), BigDecimal.valueOf(100), request.getTxnNo()), DeductBalanceResponse.class);

            // Reduce gift stock
            giftService.reduceStock(request.getGiftNo(), request.getCusNo(), request.getTxnNo());

            return ResponseEntity.ok(new RedeemResponse("200", "Redeemed successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new RedeemResponse("500", "Error during redemption: " + e.getMessage()));
        }
    }
}
