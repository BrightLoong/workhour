/*
 * @(#)DateUtils.java 2018年1月2日下午6:17:37
 * workhour
 * Copyright 2018 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.brightloong.workhour.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DateUtils
 * @author 陈龙
 * @version 1.0
 *
 */
public class DateUtils {
    
    
    private static final Logger log = LoggerFactory.getLogger(DateUtils.class);
    
    /**
     * 将传入的日期转换为hh:mm:ss的格式返回.
     * @param date
     * @return
     */
    public static Date formatDateHHhmmss(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date formatDate = null;
        try {
            formatDate = sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            log.error("转换日期格式失败!");
        }
        return formatDate;
    }
    
    /**
     * 按照给定模式进行日期格式化.
     * @param date
     * @param formatType
     * @return
     */
    public static String dateToString(Date date, String formatType) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);
        return sdf.format(date);
    }
    
    
    /**
     * double转date.
     * @param time
     * @return
     */
    public static Date doubleToDate(Double time) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1899, 11, 30, 0, 0, 0);
        calendar.add(Calendar.MILLISECOND,(int)(time * 60 * 60 * 1000));
        return calendar.getTime();
    }
}
