package com.salesmanager.core.model.promotion;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 广告，一个广告包含多个banner*
 */
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "ADVERTISE")
public class Advertise extends SalesManagerEntity<Integer, Advertise> {


    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ADVERTISE_ID", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "GROUP_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Integer id;


    public Advertise() {

    }


    /**
     * 唯一编码*
     */
    @NotEmpty
    @Column(name="CODE", length=100, nullable=false)
    private String code;

    @Column(name = "SORT_ORDER")
    private Integer sortOrder = 0;

    @Column(name = "AVAILABLE")
    private boolean available = false;
    /**
     * Product to category
     */
    @ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "ADVERTISE_BANNER", joinColumns = {
            @JoinColumn(name = "ADVERTISE_ID", nullable = false, updatable = false) }
            ,
            inverseJoinColumns = { @JoinColumn(name = "BANNER_ID",
                    nullable = false, updatable = false) }
    )
    @Cascade({
            org.hibernate.annotations.CascadeType.DETACH,
            org.hibernate.annotations.CascadeType.LOCK,
            org.hibernate.annotations.CascadeType.REFRESH,
            org.hibernate.annotations.CascadeType.REPLICATE

    })
    private Set<Banner> banners = new HashSet<Banner>();

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
    @Column(name = "DATE_START_AT",nullable = false)
    private Date startAt;
    /**
     * 活动结束时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_END_AT")
    private Date endAt;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
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

    public Set<Banner> getBanners() {
        return banners;
    }

    public void setBanners(Set<Banner> banners) {
        this.banners = banners;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
