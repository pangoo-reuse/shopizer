package com.salesmanager.core.business.services.promotion;

import com.salesmanager.core.model.promotion.Advertise;
import com.salesmanager.core.model.promotion.Banner;

import java.util.Date;
import java.util.List;

public interface AdvertiseService {
    Integer definition(String code, Integer sortOrder, Date startAt, Date endAt) throws  Exception;

    /**
     * * 必须关联 BannerDescription 更新才能生效
     */
    void combine(Integer advertiseId, List<Integer> bannerIds) throws Exception;


    void delete(Integer advertiseId);

    List<Advertise> advertises();

    List<Advertise> findFullAdvertises();

    Advertise advertise(Integer id) ;

    Advertise advertiseByCode(String code) ;
}
