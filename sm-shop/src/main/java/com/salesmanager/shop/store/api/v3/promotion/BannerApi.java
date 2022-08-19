package com.salesmanager.shop.store.api.v3.promotion;


import com.salesmanager.core.business.services.promotion.BannerService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.promotion.Banner;
import com.salesmanager.core.model.promotion.BannerDescription;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.user.ReadableUser;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.user.facade.UserFacade;
import com.salesmanager.shop.store.model.promotion.SimpleDescriptionVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/api/v3")
@Api(tags = "广告基础API")
@SwaggerDefinition(tags = {@Tag(name = "广告基础API", description = "广告基础API(基础广告的增删改查)")})
public class BannerApi {
    @Inject
    private UserFacade userFacade;

    @Autowired
    private BannerService bannerServiceV3;
    @Autowired
    private LanguageService languageService;

    private ReadableUser validAdmin(MerchantStore merchantStore, Language language, HttpServletRequest request) {
        // superadmin, admin and admin_catalogue
        String authenticated = userFacade.authenticatedUser();
        if (authenticated == null) {
            throw new UnauthorizedException();
        }

        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();
        ReadableUser user = userFacade.findByUserName(userName, null, language);

        if (user == null) {
            throw new UnauthorizedException();
        }

        userFacade.authorizedGroup(authenticated, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
                        Constants.GROUP_ADMIN_CATALOGUE, Constants.GROUP_ADMIN_RETAIL)
                .collect(Collectors.toList()));

        if (!user.getMerchant().equals(merchantStore.getCode())) {
            throw new UnauthorizedException();
        }
        return user;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = {"/private/banner/definition"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en"),
            @ApiImplicitParam(name = "viewUrl", dataType = "String"),
            @ApiImplicitParam(name = "targetUri", dataType = "String"),
    }
    )
    @ApiOperation(httpMethod = "POST", value = "定义一条banner")
    @ResponseBody
    public ResponseEntity<Integer> createV3(
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            @RequestParam(value = "viewUrl", required = false) String viewUrl,
            @RequestParam(value = "targetUri", required = false) String targetUri,
            HttpServletRequest request) throws Exception {
        ReadableUser user = validAdmin(merchantStore, language, request);

        String viewUrlDecode = URLDecoder.decode(viewUrl, "utf-8");
        String targetUriDecode = URLDecoder.decode(targetUri, "utf-8");
        Integer id = bannerServiceV3.definition(user.getId(), viewUrlDecode, targetUriDecode);
        return ResponseEntity.ok(id);

    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = {"/private/banner/{bannerId}/combine"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en"),
            @ApiImplicitParam(name = "simpleDescriptionVo", allowMultiple = true, dataType = "SimpleDescriptionVo"),
    }
    )
    @ApiOperation(httpMethod = "POST", value = "关联多语言描述到banner")
    @ResponseBody
    public ResponseEntity<Banner> combineBannerDescription(
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            @PathVariable(value = "bannerId") Integer bannerId,
            @RequestBody List<SimpleDescriptionVo> simpleDescriptionVo,
            HttpServletRequest request
    ) throws Exception {
        validAdmin(merchantStore, language, request);

        List<BannerDescription> bannerDescriptions = new ArrayList<BannerDescription>();
        for (SimpleDescriptionVo descriptionVo : simpleDescriptionVo) {

            String code = descriptionVo.getLanguageCode();
            Language cl = languageService.getByCode(code);
            if (cl!= null){
                BannerDescription description = new BannerDescription(descriptionVo.getTitle(),cl);
                description.setDescription(descriptionVo.getShortDesc());
                bannerDescriptions.add(description);
            }

        }

        bannerServiceV3.combine(bannerId, bannerDescriptions);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = {"/private/banners"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en"),
    }
    )
    @ApiOperation(httpMethod = "GET", value = "获取所有banner")
    @ResponseBody
    public ResponseEntity<List<Banner>> banners(
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletRequest request
    ) {
        validAdmin(merchantStore, language, request);
        List<Banner> banners = bannerServiceV3.banners();
        return ResponseEntity.ok(banners);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = {"/private/banners/{bannerId}"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en"),
    }
    )
    @ApiOperation(httpMethod = "GET", value = "根据id获取banner")
    @ResponseBody
    public ResponseEntity<Banner> banner(
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            @PathVariable(value = "bannerId") Integer bannerId,
            HttpServletRequest request
    ) throws Exception {
        validAdmin(merchantStore, language, request);
        Banner banner = bannerServiceV3.banner(bannerId);
        return ResponseEntity.ok(banner);
    }
}
