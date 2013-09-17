package info.hexin.jmacs.mvc.view.velocity;

import info.hexin.jmacs.ioc.context.Ioc;
import info.hexin.jmacs.mvc.view.ViewModel;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * Velocity 视图
 * 
 * @author hexin
 * 
 */
public class VelocityView implements ViewModel {

    private String path;
    private Object model;

    public VelocityView(String path, Object model) {
        this.path = path;
        this.model = model;
    }

    @Override
    public void render(Ioc ioc, HttpServletRequest request, HttpServletResponse response) throws IOException {
        VelocityContext context = new VelocityContext();
        if (model != null) {
            if (model instanceof Map) {
                Map<?, ?> map = (Map<?, ?>) model;
                for (Entry<?, ?> entry : map.entrySet()) {
                    context.put((String) entry.getKey(), entry.getValue());
                }
            }
        }

        VelocityConfigurer velocityConfigurer = ioc.getBean(VelocityConfigurer.class);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
        Template template = velocityConfigurer.getVelocityEngine().getTemplate(path);
        template.merge(context, writer);
        writer.flush();
        writer.close();
    }
}
