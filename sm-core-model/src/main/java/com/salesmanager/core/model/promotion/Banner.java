package com.salesmanager.core.model.promotion;

import com.salesmanager.core.model.generic.SalesManagerEntity;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "BANNER")
public class Banner extends SalesManagerEntity<Integer, Banner> {


    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "BANNER_ID", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "GROUP_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Integer id;


    public Banner() {

    }

    @Valid
    @OneToMany( mappedBy = "banner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BannerDescription> descriptions = new HashSet<BannerDescription>();

    /**
     * 创建者ID
     */
    @Column(name = "OWNER_ID")
    private Long ownerId;
    /**
     * 可以是图片也可以是视频地址
     */
    @Column(name = "VIEW_URL")
    private String viewUrl;

    /**
     * 可以指向一个网页，也可以指向html,也可以指向 product(productId)
     */
    @Column(name = "TARGET_URI")
    private String targetUri;

    /**
     * 创建时间*
     */
    @Column(name = "DATE_CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;


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

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public String getTargetUri() {
        return targetUri;
    }

    public void setTargetUri(String targetUri) {
        this.targetUri = targetUri;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<BannerDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<BannerDescription> descriptions) {
        this.descriptions = descriptions;
    }

}
