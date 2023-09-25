package com.wej.giftredeem.gift;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends JpaRepository<Gift, Long> {
    Gift findByGiftNo(String giftNo);
}
