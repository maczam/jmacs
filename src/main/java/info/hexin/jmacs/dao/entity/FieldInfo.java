package info.hexin.jmacs.dao.entity;

/**
 * 属性值和队形
 * 
 * @author hexin
 * 
 */
public class FieldInfo {

    private Class<?> type;
    private String name;
    private Object value;

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
