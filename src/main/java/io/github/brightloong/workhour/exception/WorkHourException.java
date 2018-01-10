/*
 * @(#)WorkHourException.java 2018年1月2日上午9:40:52
 * workhour
 * Copyright 2018 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.brightloong.workhour.exception;

/**
 * WorkHourException
 * @author 陈龙
 * @version 1.0
 *
 */
public class WorkHourException extends Exception{

    /**
     * 默认
     */
    private static final long serialVersionUID = 1L;
    
    public WorkHourException(String msg) {
        super(msg);
    }
    
    public WorkHourException(String msg, Throwable e) {
        super(msg, e);
    }
}
