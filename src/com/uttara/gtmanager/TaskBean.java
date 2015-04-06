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
	private String status;
	private String taskName;
	private String taskDesc;
	private String taskCreatedDate;
	private String taskCompletionDate;
	private String priority;
	private String EmployeeEmail;

	public String validate() {
		String msg = "";
		if (taskName == null || taskName.trim().equals("")) {
			msg = msg + "Task name cannot be empty\n";
		}
		if (taskDesc == null || taskDesc.trim().equals("")) {
			msg = msg + "Task description cannot be empty\n";
		}
		/*
		 * if(taskCompletionDate==null||taskCompletionDate.trim().equals(""));{
		 * msg = msg+"Please select task completion date\n"; }
		 */
		if (EmployeeEmail == null || EmployeeEmail.trim().equals("")) {
			msg = msg + "Choose one member \n";
		}
		return msg;

	}

	public String getJsonString() {

		JSONObject object = new JSONObject();
		object.put("taskName", this.taskName);
		object.put("taskDesc", this.taskDesc);
		object.put("taskCompletionDate", this.taskCompletionDate);
		object.put("priority", this.priority);
		object.put("email", this.EmployeeEmail);
		object.put("status", this.status);
		object.put("taskSlNo", this.task_sl_no);
		String str = object.toString();

		return str;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the projectTaskSlno
	 */
	public int getProjectTaskSlno() {
		return projectTaskSlno;
	}

	/**
	 * @param projectTaskSlno the projectTaskSlno to set
	 */
	public void setProjectTaskSlno(int projectTaskSlno) {
		this.projectTaskSlno = projectTaskSlno;
	}

	/**
	 * @return the task_sl_no
	 */
	public int getTask_sl_no() {
		return task_sl_no;
	}

	/**
	 * @param task_sl_no the task_sl_no to set
	 */
	public void setTask_sl_no(int task_sl_no) {
		this.task_sl_no = task_sl_no;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return the taskDesc
	 */
	public String getTaskDesc() {
		return taskDesc;
	}

	/**
	 * @param taskDesc the taskDesc to set
	 */
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	/**
	 * @return the taskCreatedDate
	 */
	public String getTaskCreatedDate() {
		return taskCreatedDate;
	}

	/**
	 * @param taskCreatedDate the taskCreatedDate to set
	 */
	public void setTaskCreatedDate(String taskCreatedDate) {
		this.taskCreatedDate = taskCreatedDate;
	}

	/**
	 * @return the taskCompletionDate
	 */
	public String getTaskCompletionDate() {
		return taskCompletionDate;
	}

	/**
	 * @param taskCompletionDate the taskCompletionDate to set
	 */
	public void setTaskCompletionDate(String taskCompletionDate) {
		this.taskCompletionDate = taskCompletionDate;
	}

	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * @return the employeeEmail
	 */
	public String getEmployeeEmail() {
		return EmployeeEmail;
	}

	/**
	 * @param employeeEmail the employeeEmail to set
	 */
	public void setEmployeeEmail(String employeeEmail) {
		EmployeeEmail = employeeEmail;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TaskBean)) {
			return false;
		}
		TaskBean other = (TaskBean) obj;
		if (EmployeeEmail == null) {
			if (other.EmployeeEmail != null) {
				return false;
			}
		} else if (!EmployeeEmail.equals(other.EmployeeEmail)) {
			return false;
		}
		if (priority == null) {
			if (other.priority != null) {
				return false;
			}
		} else if (!priority.equals(other.priority)) {
			return false;
		}
		if (projectName == null) {
			if (other.projectName != null) {
				return false;
			}
		} else if (!projectName.equals(other.projectName)) {
			return false;
		}
		if (projectTaskSlno != other.projectTaskSlno) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}
		if (taskCompletionDate == null) {
			if (other.taskCompletionDate != null) {
				return false;
			}
		} else if (!taskCompletionDate.equals(other.taskCompletionDate)) {
			return false;
		}
		if (taskCreatedDate == null) {
			if (other.taskCreatedDate != null) {
				return false;
			}
		} else if (!taskCreatedDate.equals(other.taskCreatedDate)) {
			return false;
		}
		if (taskDesc == null) {
			if (other.taskDesc != null) {
				return false;
			}
		} else if (!taskDesc.equals(other.taskDesc)) {
			return false;
		}
		if (taskName == null) {
			if (other.taskName != null) {
				return false;
			}
		} else if (!taskName.equals(other.taskName)) {
			return false;
		}
		if (task_sl_no != other.task_sl_no) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TaskBean [projectName=").append(projectName)
				.append(", projectTaskSlno=").append(projectTaskSlno)
				.append(", task_sl_no=").append(task_sl_no).append(", status=")
				.append(status).append(", taskName=").append(taskName)
				.append(", taskDesc=").append(taskDesc)
				.append(", taskCreatedDate=").append(taskCreatedDate)
				.append(", taskCompletionDate=").append(taskCompletionDate)
				.append(", priority=").append(priority)
				.append(", EmployeeEmail=").append(EmployeeEmail).append("]");
		return builder.toString();
	}

	
	
	
	
}
