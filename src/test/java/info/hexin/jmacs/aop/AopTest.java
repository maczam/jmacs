package info.hexin.jmacs.aop;

import info.hexin.jmacs.aop.model.A;
import info.hexin.jmacs.ioc.context.Ioc;
import info.hexin.jmacs.ioc.context.impl.SimpleIoc;
import info.hexin.jmacs.ioc.loader.impl.AnnotationLoader;

import org.junit.Test;

public class AopTest {
    
    
//    @Test
    public void test(){
        Ioc context = new SimpleIoc(new AnnotationLoader("info.hexin.jmacs.jmacs.aop.model"));
        A a = context.getBean(A.class);
        a.test();
    }
    
//    @Test
    public void test1() {
        Ioc context = new SimpleIoc(new AnnotationLoader("info.hexin.jmacs.jmacs.aop.model"));
        A a = context.getBean(A.class);
        a.test1(new String[] { "" }, 1, 2, "xxx");
    }
//    @Test
    public void test2(){
        Ioc context = new SimpleIoc(new AnnotationLoader("info.hexin.jmacs.jmacs.aop.model"));
        A a = context.getBean(A.class);
        a.test2();
    }
    
    @Test
    public void test3(){
        Ioc context = new SimpleIoc(new AnnotationLoader("info.hexin.jmacs.jmacs.aop.model"));
        A a = context.getBean(A.class);
        long result = a.test3("xx",2);
        System.out.println(result);
    }
}
