package com.salesmanager.core.business.services.promotion;

import com.salesmanager.core.model.promotion.Banner;
import com.salesmanager.core.model.promotion.Dialog;

import java.util.Date;

public interface DialogService {

    void definition(String code, Integer sortOrder, Date createdAt, Date startAt, Date endAt);

    void updateCombineBanner(Dialog advertise);

    void update(Integer dialogId, Banner banner);

    void delete(Integer dialogId);
}
