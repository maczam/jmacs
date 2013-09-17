package info.hexin.jmacs.ioc;

import java.lang.reflect.Field;

/**
 * 需要注入的属性
 * 
 * @author hexin
 * 
 */
public class Property {
    // 要注入的名称
    private String ref;
    private Field field;
    // xml中注入的属性
    private String name;
    // 直接注入值注入的值
    private String value;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("field >> " + field).append("\n");
        sb.append("xml name >> " + name + " : xml value >>>" + value).append("\n");
        sb.append("ref >>> ").append(ref);
        return sb.toString();
    }
}
