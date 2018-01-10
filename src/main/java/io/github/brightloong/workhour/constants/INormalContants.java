package io.github.brightloong.workhour.constants;

import java.io.File;

public interface INormalContants {
	
	/**班次时间表开始标签*/
	public static final String TIME_TABLE_START_TAG = "影城班次时间一览表";
	
	/**模板路径*/
	public static final String TEMPLATE_PAHT = "template" + File.separator +"template.xlsx";
	
	/**导出excel基本名字*/
	public static final String EXPORT_EXCEL_BASE_NAME = "考勤汇总";
	
	/**导出excel格式*/
	public static final String EXCEL_FILE_TYPE = ".xlsx";
	
	/**logo路径*/
	public static final String LOGO_PATH = "image" + File.separator +"logo.png";
}
