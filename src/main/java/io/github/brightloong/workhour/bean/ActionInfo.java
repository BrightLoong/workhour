/*
 * @(#)ActionInfo.java 2018年1月2日下午3:42:44
 * workhour
 * Copyright 2018 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.brightloong.workhour.bean;

import java.awt.Color;

/**
 * 执行信息.
 * ActionInfo
 * @author 陈龙
 * @version 1.0
 *
 */
public class ActionInfo {
    /**提示信息*/
    private String msg;
    
    /**信息颜色*/
    private Color msgColor;
    

    /**
     * 默认红色
     */
    public ActionInfo() {
        this.msgColor = Color.red;
    }
    
    
    

    /**
     * @param msg
     * @param msgColor
     */
    public ActionInfo(String msg, Color msgColor) {
        super();
        this.msg = msg;
        this.msgColor = msgColor;
    }




    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return the msgColor
     */
    public Color getMsgColor() {
        return msgColor;
    }

    /**
     * @param msgColor the msgColor to set
     */
    public void setMsgColor(Color msgColor) {
        this.msgColor = msgColor;
    }
    
    /**
     * 获取执行成功的信息
     * @return
     */
    public static ActionInfo getSuccessInfo() {
        ActionInfo actionInfo = new ActionInfo("执行成功！", Color.GREEN);
        return actionInfo;
    }
}
