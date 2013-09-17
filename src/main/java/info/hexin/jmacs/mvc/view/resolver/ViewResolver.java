package info.hexin.jmacs.mvc.view.resolver;

import info.hexin.jmacs.ioc.context.Ioc;
import info.hexin.jmacs.mvc.model.ViewInfo;
import info.hexin.jmacs.mvc.view.ViewModel;

/**
 * 
 * @author hexin
 * 
 */
public interface ViewResolver {

    ViewModel make(ViewInfo viewInfo, Object model, Ioc ioc);
}
