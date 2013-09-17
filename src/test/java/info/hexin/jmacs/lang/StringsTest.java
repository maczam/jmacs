package info.hexin.jmacs.lang;

import info.hexin.lang.string.Strings;

import org.junit.Assert;
import org.junit.Test;

public class StringsTest {

    @Test
    public void xx() {
        String tempA = Strings.lowerFirst("AAAAA");
        Assert.assertEquals("aAAAA", tempA);
    }
}
