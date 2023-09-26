package com.wej.giftredeem.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductBalanceResponse {
    private String resultCode;
    private String message;
}
