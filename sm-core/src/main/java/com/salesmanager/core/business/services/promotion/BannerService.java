package com.salesmanager.core.business.services.promotion;

import com.salesmanager.core.model.promotion.Banner;
import com.salesmanager.core.model.promotion.BannerDescription;

import java.util.List;

public interface BannerService {

    Integer definition(Long creatorId, String viewUrl, String targetUri);

    /**
     * * 必须关联 BannerDescription 更新才能生效
     */
    void combine(Integer bannerId, List<BannerDescription> bannerDescriptions) throws Exception;

    void delete(Integer bannerId);


    List<Banner> banners();

    Banner banner(Integer id) throws Exception;
}
