package com.wej.giftredeem.gift.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedeemRequest {
    private String txnNo;
    private String cusNo;
    private String giftNo;
}
