package info.hexin.jmacs.mvc.view.resolver;

import info.hexin.jmacs.ioc.context.Ioc;
import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.Logs;
import info.hexin.jmacs.mvc.annotation.ViewType;
import info.hexin.jmacs.mvc.model.ViewInfo;
import info.hexin.jmacs.mvc.view.ViewModel;
import info.hexin.jmacs.mvc.view.json.JsonView;
import info.hexin.jmacs.mvc.view.jsp.JspView;
import info.hexin.jmacs.mvc.view.velocity.VelocityView;
import info.hexin.lang.Exceptions;

import java.lang.reflect.Constructor;

/**
 * view 解析器，主要是判断要调用那个视图进行渲染
 * 
 * @author hexin
 * 
 */
public class CombViewResolver implements ViewResolver {
    Log log = Logs.get();

    private String defaultViewModel;

    @Override
    public ViewModel make(ViewInfo viewInfo, Object model, Ioc ioc) {
        // 采用默认
        if (viewInfo == null) {
            if (defaultViewModel != null) {
                try {
                    Class<?> klass = Class.forName(defaultViewModel);
                    Constructor<?> c = klass.getConstructor(String.class, Object.class);
                    Object o = c.newInstance(model.toString(), model);
                    return (ViewModel) o;
                } catch (Exception e) {
                    log.error("xx", e);
                }

                if (JsonView.class.getName().equals(defaultViewModel)) {
                    return new JsonView(null, model);
                } else if (JspView.class.getName().equals(defaultViewModel)) {
                    return new JspView(model.toString(), model.toString());
                } else if (VelocityView.class.getName().equals(defaultViewModel)) {
                    return new VelocityView(model.toString(), model);
                }
            } else {
                return new JsonView(null, model);
            }
        }

        // 调用配置进行返回
        if (viewInfo.getViewType() == ViewType.JSON) {
            return new JsonView(null, model);
        } else if (viewInfo.getViewType() == ViewType.JSP) {
            return new JspView(model.toString(), null);
        } else if (viewInfo.getViewType() == ViewType.VM) {
            return new VelocityView(viewInfo.getPath(), model);
        } else {
            throw Exceptions.make("不支持的返回视图！！");
        }
    }

    public String getDefaultViewModel() {
        return defaultViewModel;
    }

    public void setDefaultViewModel(String defaultViewModel) {
        this.defaultViewModel = defaultViewModel;
    }
}
