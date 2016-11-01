package com;

import java.util.List;

/**
 * @author sirodrig
 *
 */
public class AccessControl  implements Comparable<AccessControl> {
	private String subject;
	private String object;
	private String info;
	private List<String> privileges;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public List<String> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<String> privileges) {
		this.privileges = privileges;
	}

	public int compareTo(AccessControl o) {
		String a = new String(this.getSubject() + this.getObject());
		String b = new String(o.getSubject() + o.getObject());
 
		  return a.compareTo(b);
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
 
	
	
}
