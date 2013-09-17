package info.hexin.jmacs.mvc.urlpattern;

import info.hexin.jmacs.mvc.handler.mapping.UrlMatcher;
import info.hexin.jmacs.mvc.handler.mapping.UrlPattern;
import info.hexin.lang.Assert;
import info.hexin.lang.UrlPaths;

import org.junit.Test;

public class PathMatcherTest {

    @Test
    public void testFilter() {
        String xx = UrlPaths.combinePattern("*.action", "/user");
        Assert.equals("/user.action", xx);
        String xx1 = UrlPaths.combinePattern("/*", "/user");
        Assert.equals("/user", xx1);
    }

    @Test
    public void testClassType() {
        String xx = UrlPaths.combinePattern("", "/user");
        Assert.equals("/user", xx);
        String xx1 = UrlPaths.combinePattern(null, "/user");
        Assert.equals("/user", xx1);
    }

    @Test
    public void testMethod() {
        UrlPattern urlPattern = UrlPattern.compile("/{xxx}/{x}/xx-{id}.action");
        UrlMatcher urlMatcher = urlPattern.mather("/hexin/1/xx-21.action");
        if(urlMatcher.matched()){
            System.out.println(urlMatcher.getMatchedMap());
        }
    }
}
