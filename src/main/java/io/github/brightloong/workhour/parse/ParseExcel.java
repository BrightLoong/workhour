package io.github.brightloong.workhour.parse;

import io.github.brightloong.workhour.bean.Config;
import io.github.brightloong.workhour.bean.StaffWorkTable;
import io.github.brightloong.workhour.bean.TimeTable;
import io.github.brightloong.workhour.constants.INormalContants;
import io.github.brightloong.workhour.constants.enums.WeekEnum;
import io.github.brightloong.workhour.exception.WorkHourException;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解析Excel
 * ParseExcel
 * @author 陈龙
 * @version 1.0
 *
 */
public class ParseExcel {
    
    private static final Logger log = LoggerFactory.getLogger(ParseExcel.class);
    
    private static final String EXCEL_XLSX = "xlsx";
    
    private static final String DEFAULT = "DEFAULT";
    
    private static final int timeTableLines = 7;
    
    private static List<String> dateList = new ArrayList<String>();
    
    private static List<String> weekList = new ArrayList<String>();
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    
	/**
	 * 读取xml
	 * @param fileName
	 * @throws WorkHourException 
	 */
	public static void readExcel(Config config, Map<String, TimeTable> timeTableMap, List<StaffWorkTable> staffWorkTables) throws WorkHourException{  
	    String fileName = config.getInputFilePath();
	    Workbook wb = getWorkbook(fileName);
	    if (wb == null) {
            throw new WorkHourException("执行失败，读取Excel失败！");
        }
	    //获取第一个表单
	    Sheet sheet = wb.getSheetAt(0);
	    
	    if (sheet == null) {
            throw new WorkHourException("执行失败，未从Excel中获取到内容！");
        }
	    buildDateAndWeek(config);
	    setConfigLineInfo(sheet, config);
	    buildTiemTable(sheet, config, timeTableMap);
	    buildStaffWorkTable(sheet, config, staffWorkTables);
    }
	
	/**
	 * 构建日期和星期.
	 * @param config
	 */
	private static void buildDateAndWeek(Config config) {
	    dateList.clear();
	    weekList.clear();
	    int year = Integer.valueOf(config.getYear());
	    int month = Integer.valueOf(config.getMonth()) - 1;
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.YEAR, year);
	    calendar.set(Calendar.MONTH, month);
	    int dayMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	    for (int i = 1; i <= dayMonth; i++) {
            calendar.set(Calendar.DATE, i);
            dateList.add(sdf.format(calendar.getTime()));
            weekList.add(WeekEnum.getNameENByCode(calendar.get(Calendar.DAY_OF_WEEK)));
        }
	}
	
	/**
	 * 设置班次表开始的行数.
	 * @param sheet sheet
	 * @param config 配置信息
	 * @throws WorkHourException
	 */
	private static void setConfigLineInfo(Sheet sheet, Config config) throws WorkHourException {
	    //默认第5行开始，值班表的解析
	    config.setStaffLine(5);
	    Iterator<Row> rows = sheet.rowIterator();
	    while (rows.hasNext()) {
            Row row = rows.next();
            Cell cell = getAndCheckCell(row, 0);
            String value = getAndCheckStringCellValue(cell, "");
            if (INormalContants.TIME_TABLE_START_TAG.equals(value)) {
                config.setTimeTableLine(cell.getRowIndex() + 2);
                return;
            }
        }
	    throw new WorkHourException("执行失败，没有找到班次表信息开始行——" + INormalContants.TIME_TABLE_START_TAG);
	}
	
	/**
	 * 
	 * @param sheet
	 * @param config
	 * @param timeTableMap
	 * @throws WorkHourException
	 */
	private static void buildTiemTable(Sheet sheet, Config config, Map<String, TimeTable> timeTableMap) throws WorkHourException {
	    int tiemTableStartLine = config.getTimeTableLine();
	    Row row = sheet.getRow(tiemTableStartLine - 1);
	    int timeCellSize = 0;
	    for (int i = 3; i < row.getLastCellNum(); i++) {
	        Cell cell = row.getCell(i);
	        String value = getAndCheckStringCellValue(cell, "");
	        if (StringUtils.isBlank(value)) {
                timeCellSize = i - 3;
                break;
            }
	    }
	    
	    for (int i = 0; i < timeCellSize; i++) {
	        List<Cell> timeCells = new ArrayList<Cell>();
            for (int j = 0; j < timeTableLines; j++) {
                Row row2 = sheet.getRow(tiemTableStartLine - 1 + j);
                Cell cell = row2.getCell(i + 3);
                timeCells.add(cell);
            }
            TimeTable timeTable = getTimeTable(timeCells);
            timeTableMap.put(timeTable.getSimpleName(), timeTable);
        }
	    addRestToTimeTableMap(timeTableMap);
	}
	
	/**
	 * 添加“休”到timeTableMap中.
	 * @param timeTableMap
	 */
	private static void addRestToTimeTableMap(Map<String, TimeTable> timeTableMap) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(1991, 0, 1, 0, 0, 0);
	    Date date = calendar.getTime();
	    TimeTable timeTable = new TimeTable();
	    timeTable.setName("休息");
	    timeTable.setSimpleName("休");
	    timeTable.setWorkHour(date);
	    timeTable.setOffHour(date);
	    timeTable.setDinnerTime(0.0);
	    timeTable.setWorkTime(0.0);
	    timeTableMap.put(timeTable.getSimpleName(), timeTable);
	}
	
	/**
	 * 构建timeTable.
	 * @param cells
	 */
	private static TimeTable getTimeTable(List<Cell> cells) {
	    TimeTable timeTable = new TimeTable();
	    timeTable.setName(cells.get(0).getStringCellValue());
	    timeTable.setSimpleName(cells.get(1).getStringCellValue());
	    timeTable.setWorkHour(cells.get(2).getDateCellValue());
	    timeTable.setOffHour(cells.get(3).getDateCellValue());
	    timeTable.setDinnerTime(cells.get(5).getNumericCellValue());
	    timeTable.setWorkTime(cells.get(6).getNumericCellValue());
	    return timeTable;
	}
	
	/**
	 * 构造员工工作表
	 * @param sheet
	 * @param config
	 * @param staffWorkTables
	 * @throws WorkHourException
	 */
	private static void buildStaffWorkTable(Sheet sheet, Config config, List<StaffWorkTable> staffWorkTables) throws WorkHourException {
	    int staffStartLine = config.getStaffLine();
	    int staffEndLint = config.getTimeTableLine() - 2;
	    String department = DEFAULT;
	    for (int i = staffStartLine - 1; i < staffEndLint; i++) {
            Row row = getAndCheckRow(sheet, i);
            StaffWorkTable staffWorkTable = new StaffWorkTable();
            String depart = getAndCheckStringCellValue(getAndCheckCell(row, 0), "");
            if (!StringUtils.isBlank(depart)) {
                department = depart;
            }
           
            List<String> workType = new ArrayList<String>();
            staffWorkTable.setDepartment(department);
            staffWorkTable.setJob(getAndCheckStringCellValue(getAndCheckCell(row, 2), ""));
            String name = getAndCheckStringCellValue(getAndCheckCell(row, 1), "");
            if (StringUtils.isBlank(name)) {
				continue;
			}
            staffWorkTable.setName(name);
            staffWorkTable.setDateList(dateList);
            staffWorkTable.setWeekList(weekList);
            staffWorkTable.setWorkType(workType);
            staffWorkTables.add(staffWorkTable);
            
            for (int j = 3; j < dateList.size() + 3; j++) {
                workType.add(getAndCheckStringCellValue(getAndCheckCell(row, j), ""));
            }
        }
	}
	
	
	/**
	 * 获取并校验row.
	 * @param sheet
	 * @param rowCount
	 * @return
	 * @throws WorkHourException
	 */
	private static Row getAndCheckRow(Sheet sheet, int rowCount) throws WorkHourException {
	    Row row = sheet.getRow(rowCount);
	    if (row == null) {
            throw  getParseException(rowCount);
        }
	    return row;
	}
	
	/**
	 * 获取并校验cell
	 * @param row
	 * @param cellCount
	 * @return
	 * @throws WorkHourException
	 */
	private static Cell getAndCheckCell(Row row, int cellCount) throws WorkHourException {
	    Cell cell = row.getCell(cellCount);
	    if (cell == null) {
	        throw getParseException(row.getRowNum(), cellCount);
        }
	    return cell;
	}
	
	/**
	 * 获取类型为字符串的cell的内容，并检查其是否包含知道的字符串.
	 * @param cell cell
	 * @param containStr 包含的字符串
	 * @return
	 * @throws WorkHourException
	 */
	private static String getAndCheckStringCellValue(Cell cell, String containStr) throws WorkHourException {
	    String value = "";
	    try {
            value = cell.getStringCellValue();
        } catch (Exception e) {
            throw getParseException(cell.getRowIndex(), cell.getColumnIndex());
        }
	    if (StringUtils.isEmpty(containStr)) {
	        return value;
        }
	    if (!value.contains(containStr)) {
            throw getParseException(cell.getRowIndex(), cell.getColumnIndex(), containStr);
        }
	    return value;
	}
	
	private static WorkHourException getParseException(int rowCount, int cellCount, String containStr) {
        return new WorkHourException("执行失败，请检查第" + (rowCount + 1) + "行" + (cellCount + 1) + "列的应包含" + containStr + "！");
    }
	
	private static WorkHourException getParseException(int rowCount, int cellCount) {
	    return new WorkHourException("执行失败，请检查第" + (rowCount + 1) + "行" + (cellCount + 1) + "列的内容是否正确！");
	}
	
	private static WorkHourException getParseException(int rowCount) {
        return new WorkHourException("执行失败，请检查第" + (rowCount + 1) + "行的内容是否正确！");
    }
	
	
	/**
	 * 根据文件名判断是否是2007的excel格式
	 * @param fileName
	 * @return
	 */
    private static Workbook getWorkbook(String fileName) {
        boolean isE2007 = false; //判断是否是excel2007格式  
        if (fileName.toLowerCase().endsWith(EXCEL_XLSX)) {
            isE2007 = true;
        }
        Workbook wb = null;
        InputStream input = null;
        try {
            input = new FileInputStream(fileName);
            //根据文件格式(2003或者2007)来初始化  
            if (isE2007) {
                wb = new XSSFWorkbook(input);
            } else {
                wb = new HSSFWorkbook(input);
            }
        } catch (Exception e) {
            log.error("打开excel失败：{0}", fileName, e);
            return wb;
        } finally {
            IOUtils.closeQuietly(input);
        }
        return wb;
    }
}
