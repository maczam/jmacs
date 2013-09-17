package info.hexin.jmacs;

import info.hexin.jmacs.aop.AopTestAll;
import info.hexin.jmacs.asm.AsmTestAll;
import info.hexin.jmacs.config.ConfigTestAll;
import info.hexin.jmacs.dao.DaoTestAll;
import info.hexin.jmacs.ioc.IocAllTest;
import info.hexin.jmacs.json.JsonTestAll;
import info.hexin.jmacs.lang.LangTestAll;
import info.hexin.jmacs.mvc.MvcTestAll;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AsmTestAll.class, IocAllTest.class, ConfigTestAll.class, JsonTestAll.class, LangTestAll.class,
        MvcTestAll.class, DaoTestAll.class, AopTestAll.class })
public class AppTest {

}
