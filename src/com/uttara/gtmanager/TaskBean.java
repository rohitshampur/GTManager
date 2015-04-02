package com.uttara.gtmanager;

import java.io.Serializable;

import org.json.simple.JSONObject;

public class TaskBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String projectName;
	private int projectTaskSlno;
	private int task_sl_no;
	private static String status;
	private static String taskName;
	private static String taskDesc;
	private static String taskCreatedDate;
	private static String taskCompletionDate;
	private static String priority;
	private static String EmployeeEmail;
	public String getEmployeeEmail() {
		return EmployeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		EmployeeEmail = employeeEmail;
	}
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
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String validate(){
		String msg = "";
		if(taskName==null||taskName.trim().equals("")){
			msg=msg+"Task name cannot be empty\n";
		}
		if(taskDesc==null||taskDesc.trim().equals("")){
			msg = msg+"Task description cannot be empty\n";
		}
		/*if(taskCompletionDate==null||taskCompletionDate.trim().equals(""));{
			msg = msg+"Please select task completion date\n";
		}*/
		if(EmployeeEmail==null||EmployeeEmail.trim().equals("")){
			msg=msg+"Choose one member \n";
		}
		return msg;
		
	}
	public static String getJsonString(){
		
		JSONObject object= new JSONObject();
		object.put("taskName",taskName);
		object.put("taskDesc", taskDesc);
		object.put("taskCompletionDate", taskCompletionDate);
		object.put("priority", priority);
		object.put("email", EmployeeEmail);
		String str = object.toString();
		return str;
	}
	
	
	@Override
	public String toString() {
		return "TaskBean [projectName=" + projectName + ", projectTaskSlno="
				+ projectTaskSlno + ", task_sl_no=" + task_sl_no + ", status="
				+ status + ", taskName=" + taskName + ", taskDesc=" + taskDesc
				+ ", taskCreatedDate=" + taskCreatedDate
				+ ", taskCompletionDate=" + taskCompletionDate + ", priority="
				+ priority + ", EmployeeEmail=" + EmployeeEmail + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((EmployeeEmail == null) ? 0 : EmployeeEmail.hashCode());
		result = prime * result
				+ ((priority == null) ? 0 : priority.hashCode());
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
		if (EmployeeEmail == null) {
			if (other.EmployeeEmail != null)
				return false;
		} else if (!EmployeeEmail.equals(other.EmployeeEmail))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
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
