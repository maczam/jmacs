package info.hexin.jmacs.ioc.bean;

import info.hexin.jmacs.ioc.annotation.Bean;
import info.hexin.jmacs.ioc.annotation.Inject;

@Bean
public class C {

    @Inject(value = "abc")
    private String name;

    public String getName() {
        return name;
    }

    @Inject(value = "1")
    private int age;

    public int getAge() {
        return age;
    }

    @Inject(value = "['aa','bb','cc']")
    String[] age1;

    public String[] getAge1() {
        return age1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAge1(String[] age1) {
        this.age1 = age1;
    }
}
