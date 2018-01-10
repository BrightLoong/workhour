/*
 * @(#)ExportTable.java 2018年1月2日下午9:14:46
 * workhour
 * Copyright 2018 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.brightloong.workhour.bean;


/**
 * 导出对象
 * ExportTable
 * @author 陈龙
 * @version 1.0
 *
 */
public class ExportTable {
    private String department;
    
    private String name;
    
    private String date;
    
    private String week;
    
    private String workHour;
    
    private String offHour;
    
    private String dinnerTime;
    
    private double workTime;

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the week
     */
    public String getWeek() {
        return week;
    }

    /**
     * @param week the week to set
     */
    public void setWeek(String week) {
        this.week = week;
    }

    /**
     * @return the workHour
     */
    public String getWorkHour() {
        return workHour;
    }

    /**
     * @param workHour the workHour to set
     */
    public void setWorkHour(String workHour) {
        this.workHour = workHour;
    }

    /**
     * @return the offHour
     */
    public String getOffHour() {
        return offHour;
    }

    /**
     * @param offHour the offHour to set
     */
    public void setOffHour(String offHour) {
        this.offHour = offHour;
    }

    /**
     * @return the dinnerTime
     */
    public String getDinnerTime() {
        return dinnerTime;
    }

    /**
     * @param dinnerTime the dinnerTime to set
     */
    public void setDinnerTime(String dinnerTime) {
        this.dinnerTime = dinnerTime;
    }

    /**
     * @return the workTime
     */
    public double getWorkTime() {
        return workTime;
    }

    /**
     * @param workTime the workTime to set
     */
    public void setWorkTime(double workTime) {
        this.workTime = workTime;
    }
    
    
}
