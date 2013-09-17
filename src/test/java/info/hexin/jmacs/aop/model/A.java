package info.hexin.jmacs.aop.model;

import info.hexin.jmacs.aop.annotation.Point;
import info.hexin.jmacs.ioc.annotation.Bean;
import info.hexin.lang.Exceptions;

@Bean
public class A {

    @Point("tx")
    public void test() {
        System.out.println("xxxxxxxxxxxxxxxxxxxxx");
        System.out.println("in >>>> A >>>> test");
    }

    @Point("tx")
    public void test1(String[] param, int k, double x, String xx) {
        System.out.println("xxxxxxxxxxxxxxxxxxxxx");
        System.out.println("in >>>> A >>>> test");
        throw Exceptions.make("出现异常");
    }

    @Point("tx")
    public long test2() {
        System.out.println("in a test2");
        return 2;
    }

    @Point({"tx","log"})
    public long test3(String xx, double x) {
        System.out.println("in a test3");
        return 2;
    }
}
