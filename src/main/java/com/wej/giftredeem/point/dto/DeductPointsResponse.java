package com.wej.giftredeem.point.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductPointsResponse {
    private String resultCode;
    private String message;
}
