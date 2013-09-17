package info.hexin.jmacs.mvc.model;

/**
 * 方法的描述,表是一个参数
 * 
 * @author ydhexin@gmail.com
 * 
 */
public class Arg {

    private Class<?> paramClass;
    private String name;
    private Class<?> anClass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getParamClass() {
        return paramClass;
    }

    public void setParamClass(Class<?> paramClass) {
        this.paramClass = paramClass;
    }

    public Class<?> getAnClass() {
        return anClass;
    }

    public void setAnClass(Class<?> anClass) {
        this.anClass = anClass;
    }
}
