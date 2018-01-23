/*
 * @(#)ExportExcel.java 2018年1月2日下午8:57:16
 * workhour
 * Copyright 2018 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.brightloong.workhour.export;

import io.github.brightloong.workhour.bean.Config;
import io.github.brightloong.workhour.constants.INormalContants;
import io.github.brightloong.workhour.exception.WorkHourException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import net.sf.jett.transform.ExcelTransformer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 用于导出Excel.
 * ExportExcel
 * @author 陈龙
 * @version 1.0
 *
 */
public class ExportExcel {
    
    /**
     * 导出结果到模板.
     * @param config 配置信息
     * @param context 上下文
     * @throws Exception 
     */
    public static void exportExcel(Config config, Map<String, Object> context) throws Exception {
        String templateFilePath =  io.github.brightloong.workhour.utils.IOUtils.getFilePath(INormalContants.TEMPLATE_PAHT);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(templateFilePath);
            Workbook workbook = new ExcelTransformer().transform(fis, context);
            // 使用输出流生成的临时Excel文档,并将临时的Excel作为输入流，方便下载使用
            String tempFilePath = getExportExcelPath(config);
            fos = new FileOutputStream(tempFilePath);
            workbook.write(fos);
        } catch (Exception e) {
            if (e instanceof WorkHourException) {
				throw e;
			}
        } finally {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(fos);
        }
    }
    
    /**
     * 获取导出文件路径.
     * @param config 配置信息
     * @return
     * @throws WorkHourException 
     */
    private static String getExportExcelPath(Config config) throws WorkHourException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(new Date());
        String tempFilePaht = new StringBuilder(config.getOutputFilePaht())
                .append(File.separator)
                .append(INormalContants.EXPORT_EXCEL_BASE_NAME)
                .append(dateStr)
                .append(INormalContants.EXCEL_FILE_TYPE)
                .toString();
        File file = new File(tempFilePaht);
        if (file.exists()) {
            boolean isDel = FileUtils.deleteQuietly(file);
            if (!isDel) {
                throw new WorkHourException("执行失败，输出文件下已经存在结果文件且无法删除！");
            }
        }
        return tempFilePaht;
    }
}
