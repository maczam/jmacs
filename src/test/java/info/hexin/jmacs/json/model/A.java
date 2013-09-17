package info.hexin.jmacs.json.model;

import java.util.Date;

public class A {
	private String name;
	private String password;
	private Date date;
	private A childern;
	
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public A getChildern() {
        return childern;
    }
    public void setChildern(A childern) {
        this.childern = childern;
    }
}
