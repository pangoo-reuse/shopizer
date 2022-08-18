package com.salesmanager.core.business.services.promotion;

import com.salesmanager.core.model.promotion.Advertise;
import com.salesmanager.core.model.promotion.Banner;

import java.util.Date;

public interface AdvertiseService {
    void definition(String code, Integer sortOrder, Date createdAt, Date startAt, Date endAt);

    void updateCombineBanners(Advertise advertise);

    void update(Integer advertiseId, Banner... banners);

    void delete(Long advertiseId);
}
