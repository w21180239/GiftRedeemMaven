package com.wej.giftredeem.gift.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductPointsRequest {
    private String cusNo;
    private int points;
    private String txnNo;
}
