package com.wej.giftredeem.point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointService {

    private final PointRepository pointRepository;

    private final PointTransactionRepository pointTransactionRepository;

    @Autowired
    public PointService(PointRepository pointRepository, PointTransactionRepository pointTransactionRepository) {
        this.pointRepository = pointRepository;
        this.pointTransactionRepository = pointTransactionRepository;
    }

    @Transactional
    public void deductPoints(String cusNo, int points, String txnNo) {
        Point point = pointRepository.findByCusNo(cusNo);
        if (point != null && point.getPoints() >= points) {
            point.setPoints(point.getPoints() - points);
            pointRepository.save(point);

            // Insert point transaction record
            PointTransaction transaction = new PointTransaction(null, txnNo, cusNo, points);
            pointTransactionRepository.save(transaction);
        } else {
            throw new RuntimeException("Insufficient points!");
        }
    }
}
