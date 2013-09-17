package info.hexin.jmacs.mvc;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class FilterTest {
    @Test
    public void test() {
        String ss = "^.+\\.(jsp|png|gif|jpg|js|css|jspx|jpeg|swf|ico)$";
        Pattern pattern = Pattern.compile(ss, Pattern.CASE_INSENSITIVE);

        String url1 = "/favicon.ico";
        Assert.assertTrue(pattern.matcher(url1).find());
    }
}
