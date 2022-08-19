package com.salesmanager.core.model.promotion.falshSale;

import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "FLASH_SALE")
public class FlashSale extends SalesManagerEntity<Integer, FlashSale> implements Auditable {


    private static final long serialVersionUID = 1L;

    @Embedded
    private AuditSection auditSection = new AuditSection();
    @Id
    @Column(name = "FLASH_SALE_ID", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "GROUP_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Integer id;


    public FlashSale() {

    }

    /**
     * 创建者ID
     */
    @Column(name = "CREATOR_ID")
    private Long ownerId;

    /**
     * Product to flashSale
     */
    @OneToMany(mappedBy = "flashSale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FlashSaleProduct> flashSaleProducts = new HashSet<FlashSaleProduct>();

    @Valid
    @OneToMany(mappedBy = "flashSale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FlashSaleDescription> descriptions = new HashSet<FlashSaleDescription>();

    /**
     * 创建时间*
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATED_AT")
    private Date createdAt;
    /**
     * 活动开始时间*
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_START_AT", nullable = false)
    private Date startAt;
    /**
     * 活动结束时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_END_AT", nullable = false)
    private Date endAt;

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection audit) {
        this.auditSection = audit;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Set<FlashSaleProduct> getFlashSaleProducts() {
        return flashSaleProducts;
    }

    public void setFlashSaleProducts(Set<FlashSaleProduct> flashSaleProducts) {
        this.flashSaleProducts = flashSaleProducts;
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

    public Set<FlashSaleDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<FlashSaleDescription> descriptions) {
        this.descriptions = descriptions;
    }
}
