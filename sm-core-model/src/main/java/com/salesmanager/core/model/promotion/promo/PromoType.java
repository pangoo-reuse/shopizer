package com.salesmanager.core.model.promotion.promo;

public enum PromoType {
    //新用户领取
    NEW_USER(1),
    //针对特定的类别的优惠券
    CATEGORY(2),
    //针对特定的商品的优惠券
    PRODUCT(3);

    private final int type;

    PromoType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
