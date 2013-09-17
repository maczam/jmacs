package info.hexin.jmacs.json.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class C {
    private String name;
    private String password;
    private Date date;
    private Set<C> children = new HashSet<C>();

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

    public Set<C> getChildren() {
        return children;
    }

    public void setChildren(Set<C> children) {
        this.children = children;
    }
}
