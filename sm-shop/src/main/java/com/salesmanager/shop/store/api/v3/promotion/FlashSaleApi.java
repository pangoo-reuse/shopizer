package com.salesmanager.shop.store.api.v3.promotion;


import com.salesmanager.core.business.services.promotion.FlashSaleService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.promotion.Banner;
import com.salesmanager.core.model.promotion.falshSale.FlashSale;
import com.salesmanager.core.model.promotion.falshSale.FlashSaleDescription;
import com.salesmanager.core.model.promotion.falshSale.FlashSaleProduct;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.user.ReadableUser;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.user.facade.UserFacade;
import com.salesmanager.shop.store.model.promotion.FlashSaleProductVo;
import com.salesmanager.shop.store.model.promotion.SimpleDescriptionVo;
import com.salesmanager.shop.store.model.promotion.FlashSaleInputVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/api/v3")
@Api(tags = "闪购活动API")
@SwaggerDefinition(tags = {@Tag(name = "闪购活动API", description = "闪购活动API")})
public class FlashSaleApi {

    @Autowired
    private FlashSaleService flashSaleServiceV3;

    @Inject
    private UserFacade userFacade;
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
    @PostMapping(value = {"/private/flash-sale/definition"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en"),
            @ApiImplicitParam(name = "flashSaleInputVo", dataType = "FlashSaleInputVo"),
    }
    )
    @ApiOperation(httpMethod = "POST", value = "定义一条闪购活动")
    @ResponseBody
    public ResponseEntity<Integer> createV3(
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            @RequestBody FlashSaleInputVo flashSaleInputVo,
            HttpServletRequest request) throws Exception {
        ReadableUser user = validAdmin(merchantStore, language, request);
        Date startAt = flashSaleInputVo.getStartAt();
        Date endAt = flashSaleInputVo.getEndAt();

        Integer id = flashSaleServiceV3.definition(user.getId(), startAt, endAt);
        return ResponseEntity.ok(id);

    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = {"/private/flashSale/{flashSaleId}/combine"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en"),
            @ApiImplicitParam(name = "simpleDescriptionVo", allowMultiple = true, dataType = "SimpleDescriptionVo"),
    }
    )
    @ApiOperation(httpMethod = "POST", value = "关联多语言描述到闪购活动")
    @ResponseBody
    public ResponseEntity combineDescriptions(
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            @PathVariable(value = "flashSaleId") Integer flashSaleId,
            @RequestBody List<SimpleDescriptionVo> simpleDescriptionVo,
            HttpServletRequest request
    ) throws Exception {
        validAdmin(merchantStore, language, request);

        List<FlashSaleDescription> bannerDescriptions = new ArrayList<FlashSaleDescription>();
        for (SimpleDescriptionVo descriptionVo : simpleDescriptionVo) {

            String code = descriptionVo.getLanguageCode();
            Language cl = languageService.getByCode(code);
            if (cl != null) {
                FlashSaleDescription description = new FlashSaleDescription(descriptionVo.getTitle(), cl);
                description.setDescription(descriptionVo.getShortDesc());
                bannerDescriptions.add(description);
            }

        }

        flashSaleServiceV3.combine(flashSaleId, bannerDescriptions);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = {"/private/flashSale/{flashSaleId}/{saleType}/combineProducts"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en"),
            @ApiImplicitParam(name = "products", allowMultiple = true, dataType = "FlashSaleProductVo"),
    }
    )
    @ApiOperation(httpMethod = "POST", value = "添加或删除相关联的商品")
    @ResponseBody
    public ResponseEntity combineProducts(
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            @PathVariable(value = "flashSaleId") Integer flashSaleId,
            @PathVariable(value = "saleType") Integer saleType,
            @RequestBody List<FlashSaleProductVo> products,
            HttpServletRequest request
    ) throws Exception {
        validAdmin(merchantStore, language, request);
        List<FlashSaleProduct> fps = new LinkedList<FlashSaleProduct>();
        products.forEach(flashSaleProductVo -> {
            FlashSaleProduct flashSaleProduct = new FlashSaleProduct();
            flashSaleProduct.setProductId(flashSaleProductVo.getProductId());
            flashSaleProduct.setCreatedAt(new Date());
            flashSaleProduct.setSaleType(flashSaleProductVo.getSaleType());
            flashSaleProduct.setPercent(flashSaleProductVo.getPercent());
            fps.add(flashSaleProduct);
        });
        flashSaleServiceV3.combineProducts(flashSaleId, fps);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = {"/private/flashSales"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en"),
    }
    )
    @ApiOperation(httpMethod = "GET", value = "获取所有闪购活动")
    @ResponseBody
    public ResponseEntity<List<FlashSale>> flashSales(
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletRequest request
    ) {
        validAdmin(merchantStore, language, request);
        List<FlashSale> flashSales = flashSaleServiceV3.flashSales();
        return ResponseEntity.ok(flashSales);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = {"/private/flashSales/{flashSalesId}"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en"),
    }
    )
    @ApiOperation(httpMethod = "GET", value = "根据id获取Advertise")
    @ResponseBody
    public ResponseEntity<FlashSale> flashSale(
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            @PathVariable(value = "flashSalesId") Integer flashSalesId,
            HttpServletRequest request
    ) throws Exception {
        validAdmin(merchantStore, language, request);
        FlashSale flashSale = flashSaleServiceV3.flashSales(flashSalesId);
        return ResponseEntity.ok(flashSale);
    }

}
