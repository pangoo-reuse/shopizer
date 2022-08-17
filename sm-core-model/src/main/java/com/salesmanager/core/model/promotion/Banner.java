package com.salesmanager.core.model.promotion;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "BANNER")
public class Banner extends SalesManagerEntity<Integer, Banner> implements Auditable {


    private static final long serialVersionUID = 1L;

    @Embedded
    private AuditSection auditSection = new AuditSection();
    @Id
    @Column(name = "BANNER_ID", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "GROUP_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Integer id;


    public Banner() {

    }

    @Valid
    @OneToMany(mappedBy = "promotionBanner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BannerDescription> descriptions = new HashSet<BannerDescription>();

    /**
     * 创建者ID
     */
    @Column(name = "CREATOR_ID")
    private Long creatorId;
    /**
     * 可以是图片也可以是视频地址
     */
    @Column(name = "VIEWER_URL")
    private String viewerUrl;

    /**
     * 可以指向一个网页，也可以指向html,也可以指向 product(productId)
     */
    @Column(name = "TARGET_URI")
    private String targetUri;

    /**
     * 创建时间*
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATED")
    private Date createdAt;


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

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getViewerUrl() {
        return viewerUrl;
    }

    public void setViewerUrl(String viewerUrl) {
        this.viewerUrl = viewerUrl;
    }

    public String getTargetUri() {
        return targetUri;
    }

    public void setTargetUri(String targetUri) {
        this.targetUri = targetUri;
    }

    public BannerDescription getDescription() {
        if (descriptions != null && descriptions.size() > 0) {
            return descriptions.iterator().next();
        }

        return null;
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
