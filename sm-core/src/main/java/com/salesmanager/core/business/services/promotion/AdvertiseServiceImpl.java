package com.salesmanager.core.business.services.promotion;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.promotion.AdvertiseRepository;
import com.salesmanager.core.business.repositories.promotion.BannerRepository;
import com.salesmanager.core.model.promotion.Advertise;
import com.salesmanager.core.model.promotion.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Service("advertiseServiceV3")
public class AdvertiseServiceImpl implements AdvertiseService {

    @Autowired
    private BannerRepository bannerRepository;

    private AdvertiseRepository advertiseRepository;

    @Inject
    public AdvertiseServiceImpl(AdvertiseRepository advertiseRepository) {
        this.advertiseRepository = advertiseRepository;
    }

    @Override
    public Integer definition(String code, Integer sortOrder, Date startAt, Date endAt) throws Exception {
        if (advertiseRepository.existsByCode(code)) {
            throw new ServiceException("advertise exists");
        }

        Advertise advertise = new Advertise();


        advertise.setEndAt(endAt);
        advertise.setStartAt(startAt);
        advertise.setCode(code);
        advertise.setSortOrder(sortOrder);
        advertise.setCreatedAt(new Date());

        advertise = advertiseRepository.saveAndFlush(advertise);
        return advertise.getId();
    }

    @Transactional
    @Override
    public void combine(Integer advertiseId, List<Integer> bannerIds) throws Exception {
        if (!advertiseRepository.existsById(advertiseId)) {
            throw new ServiceException("Advertise not exists");
        }
        if (bannerIds == null) {
            throw new ServiceException("请配置banner");
        }
        List<Banner> banners = bannerRepository.findAllInIds(bannerIds);

        Advertise advertise = advertiseRepository.findOne(advertiseId);
        //将desc 跟当前banner关联起来
        advertise.getBanners().addAll(banners);

        advertiseRepository.save(advertise);
    }

    @Transactional
    @Override
    public void delete(Integer advertiseId) {
        advertiseRepository.deleteById(advertiseId);
    }

    @Override
    public List<Advertise> advertises() {
        return advertiseRepository.findAll();
    }

    @Override
    public List<Advertise> findFullAdvertises() {
        return advertiseRepository.findFullList();
    }

    @Override
    public Advertise advertise(Integer id) {
        return advertiseRepository.findById(id).orElse(null);
    }

    @Override
    public Advertise advertiseByCode(String code) {
        return advertiseRepository.findOneFullByCode(code);
    }
}
