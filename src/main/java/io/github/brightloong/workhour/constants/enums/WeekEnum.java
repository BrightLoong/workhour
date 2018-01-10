/*
\ * @(#)WeekEnum.java 2018年1月2日上午11:20:32
 * workhour
 * Copyright 2018 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.brightloong.workhour.constants.enums;

/**
 * WeekEnum
 * @author 陈龙
 * @version 1.0
 *
 */
public enum WeekEnum {
    
    MON(2, "一", "MON"), 
    TUE(3, "二", "TUE"),
    WED(4, "三", "WED"),
    THU(5, "四", "THU"),
    FRI(6, "五", "FRI"),
    SAT(7, "六", "SAT"),
    SUN(1, "日", "SUN");
    int code;
    
    private String name_CN;
    
    private String name_EN;
    
    private WeekEnum(int code, String name_CN, String name_EN) {
        this.code = code;
        this.name_CN = name_CN;
        this.name_EN = name_EN;
    }
    
    
    /**
     * @return the name_CN
     */
    public String getName_CN() {
        return name_CN;
    }


    /**
     * @param name_CN the name_CN to set
     */
    public void setName_CN(String name_CN) {
        this.name_CN = name_CN;
    }


    /**
     * @return the name_EN
     */
    public String getName_EN() {
        return name_EN;
    }



    /**
     * @param name_EN the name_EN to set
     */
    public void setName_EN(String name_EN) {
        this.name_EN = name_EN;
    }


    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }


    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }


    /**
     * 根据中文星期获取英文星期.
     * @param name_CN 中文星期
     * @return 英文星期
     */
    public static String getNameENByNameCN(String name_CN) {
        for (WeekEnum weekEnum : WeekEnum.values()) {
            if (name_CN.equals(weekEnum.getName_CN())) {
                return weekEnum.getName_EN();
            }
        }
        return null;
    }
    
    /**
     * 根据code获取英文星期.
     * @param code code
     * @return 英文星期
     */
    public static String getNameENByCode(int code) {
        for (WeekEnum weekEnum : WeekEnum.values()) {
            if (code == weekEnum.getCode()) {
                return weekEnum.getName_EN();
            }
        }
        return null;
    }
}
