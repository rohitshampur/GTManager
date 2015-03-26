package com.uttara.gtmanager;

import java.io.Serializable;

public class TaskBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String projectName;
	private int projectTaskSlno;
	private int task_sl_no;
	private String status;
	private String taskName;
	private String taskDesc;
	private String taskCreatedDate;
	private String taskCompletionDate;
	private int priority;
	private String EmployeeName;
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getProjectTaskSlno() {
		return projectTaskSlno;
	}
	public void setProjectTaskSlno(int projectTaskSlno) {
		this.projectTaskSlno = projectTaskSlno;
	}
	public int getTask_sl_no() {
		return task_sl_no;
	}
	public void setTask_sl_no(int task_sl_no) {
		this.task_sl_no = task_sl_no;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	public String getTaskCreatedDate() {
		return taskCreatedDate;
	}
	public void setTaskCreatedDate(String taskCreatedDate) {
		this.taskCreatedDate = taskCreatedDate;
	}
	public String getTaskCompletionDate() {
		return taskCompletionDate;
	}
	public void setTaskCompletionDate(String taskCompletionDate) {
		this.taskCompletionDate = taskCompletionDate;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getEmployeeName() {
		return EmployeeName;
	}
	public void setEmployeeName(String employeeName) {
		EmployeeName = employeeName;
	}
	@Override
	public String toString() {
		return "TaskBean [projectName=" + projectName + ", projectTaskSlno="
				+ projectTaskSlno + ", task_sl_no=" + task_sl_no + ", status="
				+ status + ", taskName=" + taskName + ", taskDesc=" + taskDesc
				+ ", taskCreatedDate=" + taskCreatedDate
				+ ", taskCompletionDate=" + taskCompletionDate + ", priority="
				+ priority + ", EmployeeName=" + EmployeeName + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((EmployeeName == null) ? 0 : EmployeeName.hashCode());
		result = prime * result + priority;
		result = prime * result
				+ ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + projectTaskSlno;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime
				* result
				+ ((taskCompletionDate == null) ? 0 : taskCompletionDate
						.hashCode());
		result = prime * result
				+ ((taskCreatedDate == null) ? 0 : taskCreatedDate.hashCode());
		result = prime * result
				+ ((taskDesc == null) ? 0 : taskDesc.hashCode());
		result = prime * result
				+ ((taskName == null) ? 0 : taskName.hashCode());
		result = prime * result + task_sl_no;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskBean other = (TaskBean) obj;
		if (EmployeeName == null) {
			if (other.EmployeeName != null)
				return false;
		} else if (!EmployeeName.equals(other.EmployeeName))
			return false;
		if (priority != other.priority)
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (projectTaskSlno != other.projectTaskSlno)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (taskCompletionDate == null) {
			if (other.taskCompletionDate != null)
				return false;
		} else if (!taskCompletionDate.equals(other.taskCompletionDate))
			return false;
		if (taskCreatedDate == null) {
			if (other.taskCreatedDate != null)
				return false;
		} else if (!taskCreatedDate.equals(other.taskCreatedDate))
			return false;
		if (taskDesc == null) {
			if (other.taskDesc != null)
				return false;
		} else if (!taskDesc.equals(other.taskDesc))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		if (task_sl_no != other.task_sl_no)
			return false;
		return true;
	}
	
	
	
	

}
