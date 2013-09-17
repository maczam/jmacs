package info.hexin.jmacs.ioc.bean;

import info.hexin.jmacs.ioc.annotation.Bean;
import info.hexin.jmacs.ioc.annotation.Scope;

@Bean(scope = Scope.prototype)
public class A {
    
    public String test() {
        System.out.println("this in A");
        return "a.test";
    }
}
