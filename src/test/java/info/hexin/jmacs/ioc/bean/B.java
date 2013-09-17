package info.hexin.jmacs.ioc.bean;

import info.hexin.jmacs.ioc.annotation.Bean;
import info.hexin.jmacs.ioc.annotation.Inject;

@Bean
public class B {

    @Inject
    private A a;
    
    public String test() {
        return a.test();
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }
}
