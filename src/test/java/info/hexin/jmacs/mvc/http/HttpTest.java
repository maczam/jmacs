package info.hexin.jmacs.mvc.http;

import info.hexin.jmacs.mvc.util.Http;

import org.junit.Assert;
import org.junit.Test;

public class HttpTest {
    @Test
    public void test(){
        String contentType = "multipart/form-data; boundary=----WebKitFormBoundaryBN11CQcj0BmbntcF";
        String bondary = Http.bondary(contentType);
        Assert.assertEquals("----WebKitFormBoundaryBN11CQcj0BmbntcF", bondary);
    }
}
