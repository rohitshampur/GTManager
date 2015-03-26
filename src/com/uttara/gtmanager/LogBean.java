package com.uttara.gtmanager;

import java.io.Serializable;

public class LogBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String email,pass;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((pass == null) ? 0 : pass.hashCode());
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
		LogBean other = (LogBean) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (pass == null) {
			if (other.pass != null)
				return false;
		} else if (!pass.equals(other.pass))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "LogBean [email=" + email + ", pass=" + pass + "]";
	}
	public String validate(){
		String msg = "";
		if(email==null||email.trim().equals(""))
		{	
			msg = msg+" Email is Mandatory\n";
		}else
		{
			String mail = email;
			String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	        Boolean b = mail.matches(emailreg);

	        if (b == false) {
	            msg=msg+"Email is Invalid\n";
	        }
	    }
		if(pass==null||pass.trim().equals(""))
		{
			msg = " Password cannot be empty\n" ;
		}
		return msg;
	}
}
