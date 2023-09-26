package com.wej.giftredeem.gift.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReduceStockRequest {
    private String giftNo;
    private String cusNo;
    private String txnNo;
}
