package info.hexin.jmacs.dao.core.model;

import info.hexin.jmacs.dao.annotation.Column;
import info.hexin.jmacs.dao.annotation.Table;

import java.util.Date;

@Table("wp_users")
public class A {

    @Column
    private String id;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private Date brithday;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Date getBrithday() {
        return brithday;
    }

    public void setBrithday(Date brithday) {
        this.brithday = brithday;
    }
}
