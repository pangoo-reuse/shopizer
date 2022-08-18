package com.salesmanager.core.business.services.promotion;

import com.salesmanager.core.model.promotion.Banner;
import com.salesmanager.core.model.promotion.BannerDescription;
import com.salesmanager.core.model.promotion.Dialog;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public interface BannerService {

    void definition(Long creatorId,String viewerUrl, String targetUri, Date createdAt);

    /**
     * * 必须关联 BannerDescription 更新才能生效
     * @param banner
     */
    void updateCombineBannerDescription(Banner banner);

    void update(Integer dialogId, BannerDescription bannerDescription);

    void delete(Integer dialogId);
}
