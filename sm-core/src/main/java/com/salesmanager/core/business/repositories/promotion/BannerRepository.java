package com.salesmanager.core.business.repositories.promotion;

import com.salesmanager.core.model.promotion.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Integer> {
    @Query("select distinct b from Banner as b left join fetch b.descriptions bd where  b.id = ?1")
    Banner findWithDescription(Integer id);

    Banner findBannerById(Integer id);

    @Query("select distinct b from Banner as b where  b.id in ?1")
    List<Banner> findAllInIds(List<Integer> bannerIds);
}
