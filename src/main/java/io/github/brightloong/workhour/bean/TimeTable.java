package io.github.brightloong.workhour.bean;

import java.util.Date;

/**
 * 班次时间表
 * @author Administrator
 *
 */
public class TimeTable {
	/**班次名称*/
	private String name;
	
	/**简称*/
	private String simpleName;
	
	/**上班时间*/
	private Date workHour;
	
	/**下班时间*/
	private Date offHour;
	
	/**就餐开始时间*/
	private String dinnerTimeStartEnd;
	
	/**就餐时长*/
	private Double dinnerTime;
	
	/**工作时长*/
	private Double workTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public Date getWorkHour() {
		return workHour;
	}

	public void setWorkHour(Date workHour) {
		this.workHour = workHour;
	}

	public Date getOffHour() {
		return offHour;
	}

	public void setOffHour(Date offHour) {
		this.offHour = offHour;
	}

	/**
     * @return the dinnerTimeStartEnd
     */
    public String getDinnerTimeStartEnd() {
        return dinnerTimeStartEnd;
    }

    /**
     * @param dinnerTimeStartEnd the dinnerTimeStartEnd to set
     */
    public void setDinnerTimeStartEnd(String dinnerTimeStartEnd) {
        this.dinnerTimeStartEnd = dinnerTimeStartEnd;
    }

    public Double getDinnerTime() {
		return dinnerTime;
	}

	public void setDinnerTime(Double dinnerTime) {
		this.dinnerTime = dinnerTime;
	}

	public Double getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Double workTime) {
		this.workTime = workTime;
	}
}
