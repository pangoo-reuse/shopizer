package com.salesmanager.core.model.promotion.falshSale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.common.audit.AuditListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "FLASH_SALE_PRODUCT", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "FLASH_SALE_PRODUCT_ID"
        })
}
)
@Entity
public class FlashSaleProduct implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "FLASH_SALE_PRODUCT_ID", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "GROUP_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Integer id;


    public FlashSaleProduct() {

    }

    @JsonIgnore
    @ManyToOne(targetEntity = FlashSale.class)
    @JoinColumn(name = "FLASH_SALE_ID", nullable = false)
    private FlashSale flashSale;

    @Column(name = "productId")
    private Long productId;

    /**
     * 折扣百分比
     */
    @Column(name = "percent")
    private Integer percent;

    /**
     * 1 为 直接优惠多少元，2 为百分比，
     * 前台显示时候要根据这个字段判断是否计算后显示给用户*
     */
    @Column(name = "SALE_TYPE")
    private Integer saleType;

    /**
     * 创建时间*
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATED_AT")
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


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

    public FlashSale getFlashSale() {
        return flashSale;
    }

    public void setFlashSale(FlashSale flashSale) {
        this.flashSale = flashSale;
    }
}
