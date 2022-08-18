package com.salesmanager.core.business.services.promotion;

import com.salesmanager.core.model.promotion.Advertise;
import com.salesmanager.core.model.promotion.Banner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("advertiseServiceV3")
public class AdvertiseServiceImpl implements AdvertiseService{
    @Override
    public void definition(String code, Integer sortOrder, Date createdAt, Date startAt, Date endAt) {

    }

    @Override
    public void updateCombineBanners(Advertise advertise) {

    }

    @Transactional
    @Override
    public void update(Integer advertiseId, Banner... banners) {

    }

    @Override
    public void delete(Long advertiseId) {

    }
}
