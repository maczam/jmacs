package info.hexin.jmacs.mvc.view.json;

import info.hexin.jmacs.ioc.context.Ioc;
import info.hexin.jmacs.mvc.view.ViewModel;
import info.hexin.json.Json;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author hexin
 * 
 */
public class JsonView implements ViewModel {

    private static final String contestType = "application/json;charset=UTF-8";
    private Object object;

    public JsonView(String path, Object object) {
        this.object = object;
    }

    @Override
    public void render(Ioc ioc, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(contestType);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter out = response.getWriter();
        String json = Json.toJson(object);
        out.append(json);
        out.flush();
        out.close();
    }
}
