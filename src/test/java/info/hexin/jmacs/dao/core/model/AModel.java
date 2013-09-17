package info.hexin.jmacs.dao.core.model;

import info.hexin.jmacs.dao.annotation.Column;
import info.hexin.jmacs.dao.annotation.Table;

@Table
public class AModel {

    @Column
    private String id;

    @Column
    private String name;

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
}
