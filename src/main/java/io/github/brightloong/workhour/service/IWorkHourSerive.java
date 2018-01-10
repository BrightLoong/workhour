/*
 * @(#)IWorkHourSerive.java 2018年1月2日下午1:34:22
 * workhour
 * Copyright 2018 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.brightloong.workhour.service;

import io.github.brightloong.workhour.bean.ActionInfo;
import io.github.brightloong.workhour.bean.Config;

/**
 * IWorkHourSerive
 * @author 陈龙
 * @version 1.0
 *
 */
public interface IWorkHourSerive {

    /**
     * @param config
     * @return
     */
    ActionInfo start(Config config);

    /**
     * 打开文件.
     * @param filePath
     * @return
     */
    ActionInfo openFile(String filePath);

}
