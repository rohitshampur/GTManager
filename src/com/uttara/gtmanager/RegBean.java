package com.uttara.gtmanager;

import java.io.Serializable;

public class RegBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fname,lname,email,phnum,pass,rpass;
	public RegBean() {
		// TODO Auto-generated constructor stub
	}
	

	public String getPhnum() {
		return phnum;
	}


	public void setPhnum(String phnum) {
		this.phnum = phnum;
	}


	public String getFname() {
		return fname;
	}


	public void setFname(String fname) {
		this.fname = fname;
	}


	public String getLname() {
		return lname;
	}


	public void setLname(String lname) {
		this.lname = lname;
	}


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

	public String getRpass() {
		return rpass;
	}

	public void setRpass(String rpass) {
		this.rpass = rpass;
	}
	
	public String validate()
	{	
		System.out.println("inside validate() of RegBean");
		String msg = "";
		if(fname==null||fname.trim().equals(""))
		{
			msg = msg+"Your first name is Mandatory\n";
		}
		if(lname==null||lname.trim().equals(""))
		{
			msg = msg+"Your last name is Mandatory\n";
		}
		
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
            }//else if(b == true)
            	//msg= Constants.SUCCESS;
            
            
		}
		if(phnum == null||phnum.trim().equals("")){
			if(phnum.length() >= 10 & phnum.length() <=0){
				msg = msg+"Invalid Phone number";
			}else{
				msg = msg+"Phone number cannot be blanck";
			}
		}
		if(pass==null||pass.trim().equals(""))
		{
			msg = msg+" Password cannot be empty\n" ;
		}else 
			if(!pass.equals(rpass))
		{
			msg = msg+" Passwords doesn't match please re-enter";
		}
		return msg;
		
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fname == null) ? 0 : fname.hashCode());
		result = prime * result + ((lname == null) ? 0 : lname.hashCode());
		result = prime * result + ((pass == null) ? 0 : pass.hashCode());
		result = prime * result + ((phnum == null) ? 0 : phnum.hashCode());
		result = prime * result + ((rpass == null) ? 0 : rpass.hashCode());
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
		RegBean other = (RegBean) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fname == null) {
			if (other.fname != null)
				return false;
		} else if (!fname.equals(other.fname))
			return false;
		if (lname == null) {
			if (other.lname != null)
				return false;
		} else if (!lname.equals(other.lname))
			return false;
		if (pass == null) {
			if (other.pass != null)
				return false;
		} else if (!pass.equals(other.pass))
			return false;
		if (phnum == null) {
			if (other.phnum != null)
				return false;
		} else if (!phnum.equals(other.phnum))
			return false;
		if (rpass == null) {
			if (other.rpass != null)
				return false;
		} else if (!rpass.equals(other.rpass))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "RegBean [fname=" + fname + ", lname=" + lname + ", email="
				+ email + ", phnum=" + phnum + ", pass=" + pass + ", rpass="
				+ rpass + "]";
	}
	
	

}