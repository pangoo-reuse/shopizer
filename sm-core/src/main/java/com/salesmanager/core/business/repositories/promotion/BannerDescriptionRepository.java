package com.salesmanager.core.business.repositories.promotion;

import com.salesmanager.core.model.promotion.Banner;
import com.salesmanager.core.model.promotion.BannerDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerDescriptionRepository extends JpaRepository<BannerDescription, Long> {
}
