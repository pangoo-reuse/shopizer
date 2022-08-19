package com.salesmanager.shop.store.model.promotion;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class AdvertiseVo {
    private String code;

    private Integer sortOrder;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date startAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date endAt;

    public AdvertiseVo() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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
