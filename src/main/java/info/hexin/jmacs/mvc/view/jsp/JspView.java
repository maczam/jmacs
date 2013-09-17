package info.hexin.jmacs.mvc.view.jsp;

import java.util.Map;
import java.util.Map.Entry;

import info.hexin.jmacs.ioc.context.Ioc;
import info.hexin.jmacs.mvc.view.ViewModel;
import info.hexin.lang.Exceptions;
import info.hexin.lang.string.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JspView implements ViewModel {
    private String path;
    private Object model;

    public JspView(String path, Object model) {
        this.path = path;
        this.model = model;
    }

    @Override
    public void render(Ioc ioc, HttpServletRequest request, HttpServletResponse response) {
        try {
            path = Strings.isNotBlank(path) ? path : (model == null ? null : model.toString());
            if (path == null) {
                throw Exceptions.make("没有发现返回jsp的页面路径！");
            }

            if (!path.endsWith(".jsp")) {
                path = path + ".jsp";
            }
            if (path.startsWith("redirect:")) {
                path = path.substring(9);
                response.sendRedirect(path);
            } else {
                if (model != null) {
                    if (model instanceof Map) {
                        Map<?, ?> map = (Map<?, ?>) model;
                        for (Entry<?, ?> entry : map.entrySet()) {
                            request.setAttribute((String) entry.getKey(), entry.getValue());
                        }
                    }
                }
                request.getRequestDispatcher(path).forward(request, response);
            }
        } catch (Exception e) {
            throw Exceptions.make(e);
        }
    }
}
