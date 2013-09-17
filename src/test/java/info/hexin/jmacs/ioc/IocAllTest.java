package info.hexin.jmacs.ioc;

import info.hexin.jmacs.ioc.loader.IocAnnotationLoaderTest;
import info.hexin.jmacs.ioc.loader.LoaderTestAll;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ IocAnnotationLoaderTest.class, LoaderTestAll.class })
public class IocAllTest {

}
