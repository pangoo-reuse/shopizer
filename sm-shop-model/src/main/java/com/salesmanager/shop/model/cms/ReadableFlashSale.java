package com.salesmanager.shop.model.cms;

import com.salesmanager.shop.model.entity.Entity;

import java.util.ArrayList;
import java.util.Date;

public class ReadableFlashSale extends Entity {
    private static final long serialVersionUID = 1L;

    private boolean visible;
    /**
     * 标题*
     */
    private String title;

    /**
     * 简短描述*
     */
    private String shortDesc;

    /**
     * 创建者ID*
     */
    private Long creatorId;
    /**
     * 创建时间*
     */
    private Date createdAt;
    /**
     * 活动开始时间*
     */
    private Date startAt;
    /**
     * 活动结束时间*
     */
    private Date endAt;

    /**
     * 所关联的商品id集合*
     */
    private ArrayList<Long> productIdList;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    public ArrayList<Long> getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(ArrayList<Long> productIdList) {
        this.productIdList = productIdList;
    }
}
