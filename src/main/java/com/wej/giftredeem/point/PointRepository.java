package com.wej.giftredeem.point;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
    Point findByCusNo(String cusNo);
}
