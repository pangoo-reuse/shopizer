package com.salesmanager.shop.store.model.promotion;


import java.util.Date;

public class FlashSaleProductVo {
    private Long productId;

    /**
     * 折扣百分比
     */
    private Integer percent;

    /**
     * 1 为 直接优惠多少元，2 为百分比，
     * 前台显示时候要根据这个字段判断是否计算后显示给用户*
     */
    private Integer saleType;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public Integer getSaleType() {
        return saleType;
    }

    public void setSaleType(Integer saleType) {
        this.saleType = saleType;
    }
}
