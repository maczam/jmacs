package info.hexin.jmacs.ioc.loader;

import info.hexin.jmacs.ioc.bean.A;
import info.hexin.jmacs.ioc.bean.B;
import info.hexin.jmacs.ioc.bean.C;
import info.hexin.jmacs.ioc.bean.IA;
import info.hexin.jmacs.ioc.context.Ioc;
import info.hexin.jmacs.ioc.context.impl.SimpleIoc;
import info.hexin.jmacs.ioc.loader.impl.AnnotationLoader;

import org.junit.Assert;
import org.junit.Test;

public class IocAnnotationLoaderTest {


    // 包不存在
    @Test(expected = RuntimeException.class)
    public void testInjectInterface() {
        Ioc context = new SimpleIoc(new AnnotationLoader("info.hexin.jmacs.jmacs.test.ioc.bean"));
        IA ia = context.getBean(IA.class);
        Assert.assertEquals("AImpl", ia.say());
    }
    
    @Test
    public void testInjectInterface1() {
        Ioc context = new SimpleIoc(new AnnotationLoader("info.hexin.jmacs.ioc.bean"));
        IA ia = context.getBean(IA.class);
        Assert.assertEquals("AImpl", ia.say());
    }

    @Test
    public void testScope() {
        Ioc context = new SimpleIoc(new AnnotationLoader("info.hexin.jmacs.ioc.bean"));
        A a1 = context.getBean(A.class);
        A a2 = context.getBean(A.class);

        Object b1 = context.getBean(B.class);
        B b2 = context.getBean(B.class);
        Assert.assertEquals(b1.hashCode(), b2.hashCode());
    }

    @Test
    public void testDefaultValue() {
        Ioc context = new SimpleIoc(new AnnotationLoader("info.hexin.jmacs.ioc.bean"));
        C c = context.getBean(C.class);
        Assert.assertEquals("abc", c.getName());
        Assert.assertEquals(1, c.getAge());
        Assert.assertEquals(3, c.getAge1().length);
    }
}
