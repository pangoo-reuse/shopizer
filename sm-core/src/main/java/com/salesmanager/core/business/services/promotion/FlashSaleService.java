package com.salesmanager.core.business.services.promotion;

import com.salesmanager.core.model.promotion.falshSale.FlashSale;
import com.salesmanager.core.model.promotion.falshSale.FlashSaleDescription;
import com.salesmanager.core.model.promotion.falshSale.FlashSaleProduct;

import java.util.Date;
import java.util.List;

public interface FlashSaleService {
    Integer definition(Long ownerId,Date startAt, Date endAt) throws Exception;

    /**
     * * 必须关联 BannerDescription 更新才能生效
     */
    void combine(Integer flashSaleId, List<FlashSaleDescription> flashSaleDescriptions) throws Exception;

    void combineProducts(Integer flashSaleId, List<FlashSaleProduct> products) throws Exception;

    void delete(Integer flashSaleId);

    List<FlashSale> flashSales();


    FlashSale flashSales(Integer id);

}
