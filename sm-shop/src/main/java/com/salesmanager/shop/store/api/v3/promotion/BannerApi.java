package com.salesmanager.shop.store.api.v3.promotion;


import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.promotion.Banner;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.entity.EntityExists;
import com.salesmanager.shop.model.user.ReadableUser;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.user.facade.UserFacade;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/api/v3")
@Api(tags = "广告基础API")
@SwaggerDefinition(tags = {@Tag(name = "广告基础API", description = "广告基础API(基础广告的增删改查)")})
public class BannerApi {
    @Inject
    private UserFacade userFacade;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = { "/private/banner/definition" })
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
    @ResponseBody
    public ResponseEntity<EntityExists> createV3(@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language, HttpServletRequest request){
        // superadmin, admin and admin_catalogue
        String authenticated = userFacade.authenticatedUser();
        if (authenticated == null) {
            throw new UnauthorizedException();
        }

        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();
        ReadableUser user = userFacade.findByUserName(userName, null, language);

        if(user== null) {
            throw new UnauthorizedException();
        }

        userFacade.authorizedGroup(authenticated, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
                        Constants.GROUP_ADMIN_CATALOGUE, Constants.GROUP_ADMIN_RETAIL)
                .collect(Collectors.toList()));

        if(!user.getMerchant().equals(merchantStore.getCode())) {
            throw new UnauthorizedException();
        }

        Banner banner = new Banner();
        banner.setCreatorId(user.getId());

//        Language language = languageService.defaultLanguage();
//        BannerDescription bannerDescription = new BannerDescription("testBanner" , language);
//        bannerDescription.setTitle("title");
//        bannerDescription.setBanner(banner);

        banner.setCreatorId(1L);
        banner.setTargetUri("schema://product?id=1" );
//        banner.getDescriptions().add(bannerDescription);
        banner.setCreatedAt(new Date(new Date().toInstant().plusMillis(1000 * 60 * 60 ).getEpochSecond()));

        return  null;

    }

}
