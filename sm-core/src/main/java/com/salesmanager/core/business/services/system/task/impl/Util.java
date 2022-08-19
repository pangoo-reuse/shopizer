package com.salesmanager.core.business.services.system.task.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    /***
     * 日期转corn表达式
     * @param date 日期
     * @return
     */
    public static String convertCron(Date date){
        String dateFormat="ss mm HH dd MM ? yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String corn = null;
        if (date != null) {
            corn = sdf.format(date);
        }
        return corn;
    }
}
