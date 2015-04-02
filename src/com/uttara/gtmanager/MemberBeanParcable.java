package com.uttara.gtmanager;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class MemberBeanParcable implements Serializable ,Parcelable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String fname,email,lname,phno;
	public MemberBeanParcable(Parcel source) {
		this.fname = source.readString();
		this.lname = source.readString();
		this.email = source.readString();
		this.phno = source.readString();
	}
	
	public MemberBeanParcable() {
		// TODO Auto-generated constructor stub
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
	public String getPhno() {
		return phno;
	}
	public void setPhno(String phno) {
		this.phno = phno;
	}
	public String getName() {
		return fname;
	}
	public void setName(String name) {
		this.fname = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "MemberBean [fname=" + fname + ", email=" + email + ", lname="
				+ lname + ", phno=" + phno + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fname == null) ? 0 : fname.hashCode());
		result = prime * result + ((lname == null) ? 0 : lname.hashCode());
		result = prime * result + ((phno == null) ? 0 : phno.hashCode());
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
		MemberBeanParcable other = (MemberBeanParcable) obj;
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
		if (phno == null) {
			if (other.phno != null)
				return false;
		} else if (!phno.equals(other.phno))
			return false;
		return true;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.fname);
		dest.writeString(this.lname);
		dest.writeString(this.email);
		dest.writeString(this.phno);
		
	}
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
	    public MemberBeanParcable createFromParcel(Parcel in) {
	        return new MemberBeanParcable(in);
	    }

	    public MemberBeanParcable[] newArray(int size) {
	        return new MemberBeanParcable[size];
	    }
	};

}
