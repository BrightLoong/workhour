/*
 * @(#)Config.java 2018年1月2日下午2:21:40
 * workhour
 * Copyright 2018 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.brightloong.workhour.bean;

/**
 * 一些初始化的信息.
 * Config
 * @author 陈龙
 * @version 1.0
 *
 */
public class Config {
    /**输入路径*/
    private String inputFilePath;
    
    /**输出路径*/
    private String outputFilePaht;
    
    /**年*/
    private String year;
    
    /**月*/
    private String month;
    
    /**员工开始行*/
    private int staffLine;
    
    /**工时表开始行*/
    private int timeTableLine;
    
    /**唯一实例*/
    private static Config config = new Config();
    
    /**
     * 私有构造
     */
    private Config() {
        
    }
    
    /**
     * 获取唯一实例.
     * @return
     */
    public static Config getInstance() {
        return config;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * @return the staffLine
     */
    public int getStaffLine() {
        return staffLine;
    }

    /**
     * @param staffLine the staffLine to set
     */
    public void setStaffLine(int staffLine) {
        this.staffLine = staffLine;
    }

    /**
     * @return the timeTableLine
     */
    public int getTimeTableLine() {
        return timeTableLine;
    }

    /**
     * @param timeTableLine the timeTableLine to set
     */
    public void setTimeTableLine(int timeTableLine) {
        this.timeTableLine = timeTableLine;
    }

    /**
     * @return the inputFilePath
     */
    public String getInputFilePath() {
        return inputFilePath;
    }

    /**
     * @param inputFilePath the inputFilePath to set
     */
    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    /**
     * @return the outputFilePaht
     */
    public String getOutputFilePaht() {
        return outputFilePaht;
    }

    /**
     * @param outputFilePaht the outputFilePaht to set
     */
    public void setOutputFilePaht(String outputFilePaht) {
        this.outputFilePaht = outputFilePaht;
    }

    /**
     * @return the config
     */
    public static Config getConfig() {
        return config;
    }

    /**
     * @param config the config to set
     */
    public static void setConfig(Config config) {
        Config.config = config;
    }
}
