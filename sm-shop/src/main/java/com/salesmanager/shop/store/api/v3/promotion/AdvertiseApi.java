package com.salesmanager.shop.store.api.v3.promotion;


import com.salesmanager.core.business.services.promotion.AdvertiseService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.promotion.Advertise;
import com.salesmanager.core.model.promotion.Banner;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.user.ReadableUser;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.user.facade.UserFacade;
import com.salesmanager.shop.store.model.promotion.AdvertiseVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/api/v3")
@Api(tags = "广告API")
@SwaggerDefinition(tags = {@Tag(name = "广告API", description = "广告API")})
public class AdvertiseApi {

    @Autowired
    private AdvertiseService advertiseServiceV3;

    @Inject
    private UserFacade userFacade;


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
    @PostMapping(value = {"/private/advertise/definition"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en"),
            @ApiImplicitParam(name = "advertiseVo", dataType = "AdvertiseVo"),
    }
    )
    @ApiOperation(httpMethod = "POST", value = "定义一条advertise")
    @ResponseBody
    public ResponseEntity<Integer> createV3(
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            @RequestBody AdvertiseVo advertiseVo,
            HttpServletRequest request) throws Exception {
        validAdmin(merchantStore, language, request);
        Date startAt = advertiseVo.getStartAt();
        Date endAt = advertiseVo.getEndAt();

        Integer id = advertiseServiceV3.definition(advertiseVo.getCode(), advertiseVo.getSortOrder(), startAt, endAt);
        return ResponseEntity.ok(id);

    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = {"/private/advertise/{advertiseId}/combine"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en"),
            @ApiImplicitParam(name = "bannerIds", allowMultiple = true, dataType = "java.lang.Integer"),
    }
    )
    @ApiOperation(httpMethod = "POST", value = "关联banner到advertise")
    @ResponseBody
    public ResponseEntity<Banner> combineBanners(
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            @PathVariable(value = "advertiseId") Integer advertiseId,
            @RequestBody List<Integer> bannerIds,
            HttpServletRequest request
    ) throws Exception {
        validAdmin(merchantStore, language, request);

        advertiseServiceV3.combine(advertiseId, bannerIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = {"/private/advertises"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en"),
    }
    )
    @ApiOperation(httpMethod = "GET", value = "获取所有Advertise")
    @ResponseBody
    public ResponseEntity<List<Advertise>> advertises(
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletRequest request
    ) {
        validAdmin(merchantStore, language, request);
        List<Advertise> advertises = advertiseServiceV3.advertises();
        return ResponseEntity.ok(advertises);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = {"/private/advertises/{advertiseId}"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en"),
    }
    )
    @ApiOperation(httpMethod = "GET", value = "根据id获取Advertise")
    @ResponseBody
    public ResponseEntity<Advertise> advertise(
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            @PathVariable(value = "advertiseId") Integer advertiseId,
            HttpServletRequest request
    ) throws Exception {
        validAdmin(merchantStore, language, request);
        Advertise advertise = advertiseServiceV3.advertise(advertiseId);
        return ResponseEntity.ok(advertise);
    }

}
