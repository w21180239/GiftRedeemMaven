package com.wej.giftredeem.gift.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductBalanceRequest {
    private String cusNo;
    private BigDecimal amount;
    private String txnNo;
}
