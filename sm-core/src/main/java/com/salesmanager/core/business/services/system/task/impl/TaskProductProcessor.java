package com.salesmanager.core.business.services.system.task.impl;

import com.salesmanager.core.business.repositories.system.SystemTimerTaskRepository;
import com.salesmanager.core.business.services.system.task.TimerTaskClass;
import com.salesmanager.core.business.services.system.task.TimerTaskMethod;
import com.salesmanager.core.business.services.system.task.TimerTaskParameter;
import com.salesmanager.core.model.promotion.falshSale.FlashSaleProduct;
import com.salesmanager.core.model.system.SystemTimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@TimerTaskClass
public class TaskProductProcessor {
    @Autowired
    private SystemTimerTaskRepository systemTimerTaskRepository;

    @TimerTaskMethod
    public void saveTaskFlashTask(@TimerTaskParameter List<FlashSaleProduct> products, @TimerTaskParameter Long startAt, @TimerTaskParameter Long endAt) {
        String startAtCorn = Util.convertCron(new Date(startAt));
        String endAtCorn = Util.convertCron(new Date(endAt));
        SystemTimerTask systemTimerTask = new SystemTimerTask();
        systemTimerTask.setCron(startAtCorn);
//        systemTimerTask.setTaskMethod();
//        systemTimerTaskRepository.save()
    }

    public void timeToFlashSale() {


    }

}
