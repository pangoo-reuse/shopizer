package com.salesmanager.test.promotion;

import com.paypal.core.rest.JSONFormatter;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.promotion.AdvertiseRepository;
import com.salesmanager.core.business.repositories.promotion.BannerDescriptionRepository;
import com.salesmanager.core.business.repositories.promotion.BannerRepository;
import com.salesmanager.core.business.repositories.reference.language.LanguageRepository;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.model.promotion.Advertise;
import com.salesmanager.core.model.promotion.Banner;
import com.salesmanager.core.model.promotion.BannerDescription;
import com.salesmanager.core.model.reference.language.Language;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Test content with CMS store logo
 *
 * @author Carl Samson
 */
public class AdvertiseTest extends com.salesmanager.test.common.AbstractSalesManagerCoreTestCase {

    @Inject
    private AdvertiseRepository advertiseRepository;
    @Inject
    private BannerRepository bannerRepository;
    @Inject
    private BannerDescriptionRepository descriptionRepository;
    @Inject
    private LanguageService languageService;
    @Inject
    private LanguageRepository languageRepository;

    @Test
    public void createLanguage() throws ServiceException {
        Language l =    new Language("zh-CN");


        languageRepository.save(l );
        Language language = languageService.getByCode("zh-CN");
//
//        languageService.delete(language);
    }

    @Test
    public void testAdvertise() throws ServiceException {
        //字段包含 fetch = FetchType.LAZY，cascade != CascadeType.REFRESH 插入到数据库该字段必须为数据库已经存在的数据
        Banner banner = new Banner();



        banner.setOwnerId(1L);
        banner.setTargetUri("schema://product?id=1" );
//        banner.getDescriptions().add(bannerDescription);
        banner.setCreatedAt(new Date(new Date().toInstant().plusMillis(1000 * 60 * 60 ).getEpochSecond()));


        banner = bannerRepository.saveAndFlush(banner);


        Language language = languageService.defaultLanguage();
        BannerDescription bannerDescription = new BannerDescription("testBanner" , language);
        bannerDescription.setTitle("title");
        bannerDescription.setBanner(banner);

       descriptionRepository.saveAndFlush(bannerDescription);

        Banner b = bannerRepository.findWithDescription(banner.getId());
        Advertise advertise = new Advertise();
        advertise.setAvailable(true);
        advertise.setCode("2EE#445");
        advertise.setCreatedAt(new Date());
        advertise.setStartAt(new Date());
        advertise.setEndAt(new Date(new Date().toInstant().plusMillis(1000 * 60 * 60).getEpochSecond()));
        advertise.setSortOrder(1);

        advertise.getBanners().add(banner);
        advertiseRepository.save(advertise);

        advertiseRepository.count();
    }
    @Test
    public void testAdvertise2(){
        List<Advertise> a = advertiseRepository.findAll();

        System.out.println(JSONFormatter.toJSON(a));
    }

}