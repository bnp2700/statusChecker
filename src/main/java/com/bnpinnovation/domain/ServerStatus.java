package com.bnpinnovation.domain;

import java.io.Serializable;
import java.util.Date;


public class ServerStatus implements Serializable {
	private static final long serialVersionUID = -3655951755548358599L;
	
	private String name;
	private boolean serverStatus;
	private Date checkDate;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean getServerStatus() {
		return serverStatus;
	}
	
	public void setServerStatus(boolean serverStatus) {
		this.serverStatus = serverStatus;
	}
	
	public Date getCheckDate() {
		return checkDate;
	}
	
	public void setCheckDate(Date currentDate) {
		this.checkDate = currentDate;
	}
	
	@Override
	public String toString() {
		return "ServerStatus [name=" + name + ", serverStatus=" + serverStatus
				+ ", checkDate=" + checkDate + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((checkDate == null) ? 0 : checkDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (serverStatus ? 1231 : 1237);
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
		ServerStatus other = (ServerStatus) obj;
		if (checkDate == null) {
			if (other.checkDate != null)
				return false;
		} else if (!checkDate.equals(other.checkDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (serverStatus != other.serverStatus)
			return false;
		return true;
	}
}
