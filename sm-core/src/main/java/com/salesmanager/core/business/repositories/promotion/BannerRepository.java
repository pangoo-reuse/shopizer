package com.salesmanager.core.business.repositories.promotion;

import com.salesmanager.core.model.promotion.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    @Query("select distinct b from Banner as b left join fetch b.descriptions bd where  b.id = ?1")
    Banner findWithDescription(Integer id);
}
