package io.github.brightloong.workhour.bean;

import java.util.List;

/**
 * 人员工作排班明细
 * @author Administrator
 *
 */
public class StaffWorkTable {
	/**姓名*/
	private String name;
	
	/**部门*/
	private String department;
	
	/**职务*/
	private String job;
	
	/**日期*/
	private List<String> dateList;
	
	/**星期*/
	private List<String> weekList;
	
	/**排班类型*/
	private List<String> workType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public List<String> getDateList() {
		return dateList;
	}

	public void setDateList(List<String> dateList) {
		this.dateList = dateList;
	}

	public List<String> getWeekList() {
		return weekList;
	}

	public void setWeekList(List<String> weekList) {
		this.weekList = weekList;
	}

	public List<String> getWorkType() {
		return workType;
	}

	public void setWorkType(List<String> workType) {
		this.workType = workType;
	}
}
