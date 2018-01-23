/*
 * @(#)WorkHourServiceImpl.java 2018年1月2日下午1:34:48
 * workhour
 * Copyright 2018 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.brightloong.workhour.service.impl;

import io.github.brightloong.workhour.bean.ActionInfo;
import io.github.brightloong.workhour.bean.Config;
import io.github.brightloong.workhour.bean.ExportTable;
import io.github.brightloong.workhour.bean.StaffWorkTable;
import io.github.brightloong.workhour.bean.TimeTable;
import io.github.brightloong.workhour.exception.WorkHourException;
import io.github.brightloong.workhour.export.ExportExcel;
import io.github.brightloong.workhour.parse.ParseExcel;
import io.github.brightloong.workhour.service.IWorkHourSerive;
import io.github.brightloong.workhour.utils.DateUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * WorkHourServiceImpl
 * @author 陈龙
 * @version 1.0
 *
 */
@Service("workHourService")
public class WorkHourServiceImpl implements IWorkHourSerive{
    
    private static final Logger log = LoggerFactory.getLogger(WorkHourServiceImpl.class);
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
    
    /**
     * 打开文件.
     * @param filePath
     * @return
     */
    @Override
    public ActionInfo openFile(String filePath) {
        ActionInfo actionInfo = null;
        if (StringUtils.isBlank(filePath)) {
            actionInfo = new ActionInfo();
            actionInfo.setMsg("请输入输出目录！");
            return actionInfo;
        }
        try {
            String command = new StringBuilder("explorer.exe")
                .append(" ")
                .append(filePath).toString();
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            actionInfo = new ActionInfo();
            actionInfo.setMsg(e.getMessage());
        }
        return actionInfo;
    }
    
    /**
     * (non-Javadoc)
     * @see io.github.brightloong.workhour.service.IWorkHourSerive#start(io.github.brightloong.workhour.bean.Config)
     */
    @Override
    public ActionInfo start(Config config) {
        ActionInfo actionInfo = null;
        try {
            Map<String, TimeTable> timeTableMap = new HashMap<String, TimeTable>(); 
            List<StaffWorkTable> staffWorkTables = new ArrayList<>();
            Map<String, Object> context = new HashMap<String, Object>();
            checkConfig(config);
            checkInputAndOutput(config);
            ParseExcel.readExcel(config, timeTableMap, staffWorkTables);
            context.put("datas", buildExportDatas(timeTableMap, staffWorkTables));
            context.put("info", buildTableInfo(config));
            ExportExcel.exportExcel(config, context);
            actionInfo = ActionInfo.getSuccessInfo();
        } catch (Exception e) {
            log.error("执行失败！", e);
            actionInfo = new ActionInfo();
            actionInfo.setMsg(e.getMessage());
        }
        return actionInfo;
    }
    
    /**
     * 构建导出表上的信息.
     * @param config
     * @return
     */
    private Map<String, Object> buildTableInfo(Config config) {
        Map<String, Object> infoMap = new HashMap<String, Object>();
        int year = Integer.valueOf(config.getYear());
        int month = Integer.valueOf(config.getMonth()) - 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        int dayMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DATE, 1);
        Date start = calendar.getTime();
        calendar.set(Calendar.DATE, dayMonth);
        Date end = calendar.getTime();
        infoMap.put("dateStart", DateUtils.dateToString(start, "yyyy/M/d"));
        infoMap.put("dateEnd", DateUtils.dateToString(end, "yyyy/M/d"));
        return infoMap;
    }
    
    /**
     * 构造导出数据.
     * @param timeTableMap
     * @param staffWorkTables
     * @return
     * @throws WorkHourException
     */
    private List<ExportTable> buildExportDatas(
            Map<String, TimeTable> timeTableMap,
            List<StaffWorkTable> staffWorkTables) throws WorkHourException {
        List<ExportTable> exportTables = new ArrayList<ExportTable>();
        for (StaffWorkTable staffWorkTable : staffWorkTables) {
            List<String> dateList = staffWorkTable.getDateList();
            List<String> weekList = staffWorkTable.getWeekList();
            List<String> workType = staffWorkTable.getWorkType();
            for (int i = 0; i < dateList.size(); i++) {
                ExportTable exportTable = new ExportTable();
                exportTable.setDepartment(staffWorkTable.getDepartment());
                exportTable.setName(staffWorkTable.getName());
                exportTable.setDate(dateList.get(i));
                exportTable.setWeek(weekList.get(i));
                String key = workType.get(i);
                if (StringUtils.isBlank(key)) {
                    exportTables.add(exportTable);
                    continue;
                } else if (timeTableMap.containsKey(key)) {
                    TimeTable timeTable = timeTableMap.get(key);
                    setWorkHourAndOffHour(timeTable, exportTable);
                    exportTable.setDinnerTime(sdf.format(DateUtils.doubleToDate(timeTable.getDinnerTime())));
                    exportTable.setWorkTime(timeTable.getWorkTime());
                } else {
                    throw new WorkHourException("执行失败，未知的排班类型：" + key);
                }
                exportTables.add(exportTable);
            }
        }

        return exportTables;
    }
    
    /**
     * 设置上下班时间.
     * @param timeTable
     * @param exportTable
     */
    private void setWorkHourAndOffHour(TimeTable timeTable, ExportTable exportTable) {
        Date workHour = timeTable.getWorkHour();
        Date offHour = timeTable.getOffHour();
        String workHourStr = DateUtils.dateToString(workHour, "yyyy/M/d");
        String offHourStr = DateUtils.dateToString(offHour, "yyyy/M/d");
        if (!workHourStr.equals(offHourStr)) {
            exportTable.setOffHour(DateUtils.dateToString(offHour, "yyyy/M/d H:mm:ss"));
        } else {
            exportTable.setOffHour(DateUtils.dateToString(offHour, "H:mm:ss"));
        }
        exportTable.setWorkHour(DateUtils.dateToString(workHour, "H:mm:ss"));
    }
    
    
    /**
     * 进行校验，检验信息是否完整.
     * @param config 配置信息
     * @throws WorkHourException 异常
     */
    private void checkConfig(Config config) throws WorkHourException {
        if (StringUtils.isBlank(config.getInputFilePath())) {
            throw new WorkHourException("执行失败，请选择输入文件！");
        } else if (StringUtils.isBlank(config.getOutputFilePaht())) {
            throw new WorkHourException("执行失败，请选择输出目录！");
        } else if (StringUtils.isBlank(config.getYear())) {
            throw new WorkHourException("执行失败，请输入年份！");
        } else if (StringUtils.isBlank(config.getMonth())) {
            throw new WorkHourException("执行失败，请输入月份！");
        }
    }
    
    /**
     * 对输入输出进行检查.
     * @param config
     * @throws WorkHourException
     */
    private void checkInputAndOutput(Config config) throws WorkHourException {
        String input = config.getInputFilePath();
        String output = config.getOutputFilePaht();
        if (!input.toLowerCase().endsWith(".xls") && !input.toLowerCase().endsWith(".xlsx")) {
            throw new WorkHourException("执行失败，输入文件不是Excel！");
        }
        File outputFile = new File(output);
        if (!outputFile.isDirectory()) {
            throw new WorkHourException("执行失败，输出目录不存在或者不是一个目录！");
        }
    }
}
