package com.salesmanager.shop.store.controller.cms.facade;

import com.salesmanager.shop.model.cms.ReadableBanner;
import com.salesmanager.shop.model.cms.ReadableFlashSale;

import java.util.List;

public interface PromotionFacade {

    void createBanner(ReadableBanner banner);

    void deleteBanner(Long bannerId);

    List<ReadableBanner> getAllBanner();

    void createFlashSale(ReadableFlashSale readableFlashSale);

    void updateFlashSale(ReadableFlashSale readableFlashSale);
}
