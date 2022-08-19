package com.salesmanager.core.business.repositories.promotion;

import com.salesmanager.core.model.promotion.Banner;
import com.salesmanager.core.model.promotion.falshSale.FlashSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlashSaleRepository extends JpaRepository<FlashSale, Integer> {
    @Query("select distinct b from FlashSale as b left join fetch b.descriptions bd where  b.id = ?1")
    Banner findWithDescription(Integer id);

    FlashSale findFlashSaleById(Integer id);

    @Query("select distinct b from Banner as b where  b.id in ?1")
    List<Banner> findAllInIds(List<Integer> bannerIds);
}
