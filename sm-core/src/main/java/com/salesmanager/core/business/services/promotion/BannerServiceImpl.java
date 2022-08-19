package com.salesmanager.core.business.services.promotion;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.promotion.BannerRepository;
import com.salesmanager.core.model.promotion.Banner;
import com.salesmanager.core.model.promotion.BannerDescription;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Service("bannerServiceV3")
public class BannerServiceImpl implements BannerService {
    private BannerRepository bannerRepository;

    @Inject
    public BannerServiceImpl(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }


    @Override
    public Integer definition(Long userId, String viewUrl, String targetUri) {

        Banner banner = new Banner();
        banner.setOwnerId(userId);

//        Language language = languageService.defaultLanguage();
//        BannerDescription bannerDescription = new BannerDescription("testBanner" , language);
//        bannerDescription.setTitle("title");
//        bannerDescription.setBanner(banner);

        banner.setViewUrl(viewUrl);
        banner.setTargetUri(targetUri);
//        banner.getDescriptions().add(bannerDescription);
        banner.setCreatedAt(new Date());

        banner = bannerRepository.saveAndFlush(banner);
        return banner.getId();
    }

    @Transactional
    @Override
    public void combine(Integer bannerId, List<BannerDescription> bannerDescriptions) throws Exception {
        boolean exists = bannerRepository.existsById(bannerId);
        if (!exists) {
            throw new ServiceException("Banner 不存在");
        }
        if (bannerDescriptions == null) {
            throw new ServiceException("请插入banner的描述");
        }

        Banner banner = bannerRepository.findBannerById(bannerId);
        for (BannerDescription bannerDescription : bannerDescriptions) {
            //将desc 跟当前banner关联起来
            bannerDescription.setBanner(banner);
            banner.getDescriptions().add(bannerDescription);
        }

        bannerRepository.save(banner);
    }

    @Transactional
    @Override
    public void delete(Integer bannerId) {
        bannerRepository.deleteById(bannerId);
    }

    @Override
    public List<Banner> banners() {
        return bannerRepository.findAll();
    }

    @Override
    public Banner banner(Integer id) throws Exception {
        boolean exists = bannerRepository.existsById(id);
        if (!exists) {
            throw new ServiceException("Banner 不存在");
        }
        return bannerRepository.findBannerById(id);
    }
}
