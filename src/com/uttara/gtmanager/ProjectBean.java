package com.uttara.gtmanager;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProjectBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int project_sl_no;
	private String projectName;
	private String proj_CreatedDate;
	private String description;
	private String typeOfProject;
	private int task_sl_no;
	private String status;
	private String taskName;
	private String taskDesc;
	private Date taskCreatedDate;
	private String projCompletionDate;
	public String getProjCompletionDate() {
		return projCompletionDate;
	}
	public void setProjCompletionDate(String projCompletionDate) {
		this.projCompletionDate = projCompletionDate;
	}
	private List<String> list;
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public int getProject_sl_no() {
		return project_sl_no;
	}
	public void setProject_sl_no(int project_sl_no) {
		this.project_sl_no = project_sl_no;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProj_CreatedDate(){
		return proj_CreatedDate;
	}
	public void setProj_CreatedDate(String string) {
		this.proj_CreatedDate = string;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTypeOfProject() {
		return typeOfProject;
	}
	public void setTypeOfProject(String typeOfProject) {
		this.typeOfProject = typeOfProject;
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
	public Date getTaskCreatedDate() {
		return taskCreatedDate;
	}
	public void setTaskCreatedDate(Date taskCreatedDate) {
		this.taskCreatedDate = taskCreatedDate;
	}
	public String validate(){
		String msg = "";
		if(projectName==null||projectName.trim().equals("")){
			msg=msg+"Project name cannot be empty\n";
		}
		if(proj_CreatedDate==null||proj_CreatedDate.trim().equals("")){
			msg = msg+"Select created date\n";
		}
		if(description==null||description.trim().equals(""));{
			msg = msg+"Please give description\n";
		}
		if(typeOfProject==null||typeOfProject.trim().equals("")){
			msg = msg+"Type of project cannot be empty\n";
		}
		return msg;
		
	}
	@Override
	public String toString() {
		return "ProjectBean [project_sl_no=" + project_sl_no + ", projectName="
				+ projectName + ", proj_CreatedDate=" + proj_CreatedDate
				+ ", description=" + description + ", typeOfProject="
				+ typeOfProject + ", task_sl_no=" + task_sl_no + ", status="
				+ status + ", taskName=" + taskName + ", taskDesc=" + taskDesc
				+ ", taskCreatedDate=" + taskCreatedDate
				+ ", projCompletionDate=" + projCompletionDate + ", list="
				+ list + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		result = prime
				* result
				+ ((projCompletionDate == null) ? 0 : projCompletionDate
						.hashCode());
		result = prime
				* result
				+ ((proj_CreatedDate == null) ? 0 : proj_CreatedDate.hashCode());
		result = prime * result
				+ ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + project_sl_no;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((taskCreatedDate == null) ? 0 : taskCreatedDate.hashCode());
		result = prime * result
				+ ((taskDesc == null) ? 0 : taskDesc.hashCode());
		result = prime * result
				+ ((taskName == null) ? 0 : taskName.hashCode());
		result = prime * result + task_sl_no;
		result = prime * result
				+ ((typeOfProject == null) ? 0 : typeOfProject.hashCode());
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
		ProjectBean other = (ProjectBean) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		if (projCompletionDate == null) {
			if (other.projCompletionDate != null)
				return false;
		} else if (!projCompletionDate.equals(other.projCompletionDate))
			return false;
		if (proj_CreatedDate == null) {
			if (other.proj_CreatedDate != null)
				return false;
		} else if (!proj_CreatedDate.equals(other.proj_CreatedDate))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (project_sl_no != other.project_sl_no)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
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
		if (typeOfProject == null) {
			if (other.typeOfProject != null)
				return false;
		} else if (!typeOfProject.equals(other.typeOfProject))
			return false;
		return true;
	}
	
}
	