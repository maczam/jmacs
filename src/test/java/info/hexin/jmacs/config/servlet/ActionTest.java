package info.hexin.jmacs.config.servlet;

import info.hexin.jmacs.mvc.annotation.HeaderParam;
import info.hexin.jmacs.mvc.annotation.Method;
import info.hexin.jmacs.mvc.annotation.Param;
import info.hexin.jmacs.mvc.annotation.Path;

@Path
public class ActionTest {

    @Path(value = "list.do", method = Method.GET)
    public void list(@HeaderParam("xx") String xx, String xx1, String[] ss, @Param("aa") Object aa) {
    }
}
