package com.salesmanager.core.model.promotion.promo;

import com.salesmanager.core.model.generic.SalesManagerEntity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "PROMO")
public class Promo extends SalesManagerEntity<Integer, Promo> {


    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PROMO_ID", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "GROUP_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Integer id;


    public Promo() {

    }

    @Valid
    @OneToMany(mappedBy = "promo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PromoDescription> descriptions = new HashSet<PromoDescription>();

    /**
     * 创建者ID
     */
    @Column(name = "CREATOR_ID")
    private Long creatorId;

    /**
     * 优惠码
     */
    @NotEmpty
    @Column(name="CODE", length=100, nullable=false)
    private String code;

    /**
     * 活动开始时间*
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_START_AT",nullable = false)
    private Date startAt;
    /**
     * 活动结束时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_END_AT")
    private Date endAt;

    /**
     * 总数量 -1 为不限量，其他为限量*
     */
    @Column(name = "NUM_OF_TOTAL")
    private Integer numOfTotal = 0;

    /**
     * 优惠券类型
     * @see PromoType
     */
    @Column(name = "PROMO_TYPE")
    private Integer promoType;
    /**
     * 是否启用
     * @see PromoType
     */
    @Column(name = "AVAILABLE")
    private boolean available = false;
    /**
     * 是否可以叠加其他促销抵用
     * @see PromoType
     */
    @Column(name = "FX")
    private boolean fx = false;

    /**
     * 目标ID,可以是类别，可以是商品，为空则为全站通用或新用户专享
     * @see PromoType
     * @see com.salesmanager.core.model.catalog.category.Category,com.salesmanager.core.model.catalog.product.Product
     */
    @Column(name = "TARGET_IDS")
    @ElementCollection // 1
    @CollectionTable(name = "PROMO_T_IDS", joinColumns = @JoinColumn(name = "PROMO_TG_IDS")) // 2
    private List<String> targetIds ;

    /**
     * 创建时间*
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATED_AT")
    private Date createdAt ;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
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



    public Set<PromoDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<PromoDescription> descriptions) {
        this.descriptions = descriptions;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Integer getNumOfTotal() {
        return numOfTotal;
    }

    public void setNumOfTotal(Integer numOfTotal) {
        this.numOfTotal = numOfTotal;
    }

    public Integer getPromoType() {
        return promoType;
    }

    public void setPromoType(Integer promoType) {
        this.promoType = promoType;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isFx() {
        return fx;
    }

    public void setFx(boolean fx) {
        this.fx = fx;
    }

    public List<String> getTargetIds() {
        return targetIds;
    }

    public void setTargetIds(List<String> targetIds) {
        this.targetIds = targetIds;
    }
}
