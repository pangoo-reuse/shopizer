package com.salesmanager.shop.store.model.promotion;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class FlashSaleInputVo {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date startAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date endAt;

    public FlashSaleInputVo() {
    }


    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }
}
