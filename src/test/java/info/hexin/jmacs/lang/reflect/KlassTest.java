package info.hexin.jmacs.lang.reflect;

import info.hexin.lang.Assert;
import info.hexin.lang.reflect.Klass;

import org.junit.Test;

/**
 * 
 * @author hexin
 * 
 */
public class KlassTest {
    @Test
    public void test1() {
        Assert.isTrue(Klass.isInterfaceFrom(B.class, A.class));
        Assert.isTrue(Klass.isInterfaceFrom(C.class, A.class));
    }
    
    @Test(expected=RuntimeException.class)
    public void test2(){
        Assert.isTrue(Klass.isInterfaceFrom(D.class, A.class));
    }
}


interface A{}
class B implements A{}
class C extends B{}
class D {}
