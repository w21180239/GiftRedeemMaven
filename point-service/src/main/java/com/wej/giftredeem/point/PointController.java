package com.wej.giftredeem.point;

import com.wej.giftredeem.point.dto.DeductPointsRequest;
import com.wej.giftredeem.point.dto.DeductPointsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/point")
public class PointController {
    @Autowired
    private PointService pointService;

    @PostMapping("/deductPoints")
    public ResponseEntity<DeductPointsResponse> deductPoints(@RequestBody DeductPointsRequest request) {
        try {
            pointService.deductPoints(request.getCusNo(), request.getPoints(), request.getTxnNo());
            return ResponseEntity.ok(new DeductPointsResponse("200", "Points deducted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new DeductPointsResponse("400", e.getMessage()));
        }
    }
}
