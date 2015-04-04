package info.hexin.jmacs.ioc.loader;

import info.hexin.jmacs.ioc.bean.C;
import info.hexin.jmacs.ioc.context.Ioc;
import info.hexin.jmacs.ioc.context.impl.SimpleIoc;
import info.hexin.jmacs.ioc.loader.impl.XmlLoader;

import org.junit.Test;

public class IocXmlLoaderTest {

    @Test
    public void testInjectInterface() {
        Ioc context = new SimpleIoc(new XmlLoader("app1.xml"));
        C c = context.getBean(C.class);
        System.out.println(c.getName());
    }
}
