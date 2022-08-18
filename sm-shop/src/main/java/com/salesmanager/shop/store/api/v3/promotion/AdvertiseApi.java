package com.salesmanager.shop.store.api.v3.promotion;


import com.salesmanager.core.business.services.promotion.AdvertiseService;
import com.salesmanager.shop.model.entity.EntityExists;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v3")
@Api(tags = "广告API")
@SwaggerDefinition(tags = {@Tag(name = "广告API", description = "广告API")})
public class AdvertiseApi {

    @Autowired
    private AdvertiseService advertiseServiceV3;

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = { "/private/advertise/definition" })
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
    @ResponseBody
    public ResponseEntity<EntityExists> createV3(){


    }

}
