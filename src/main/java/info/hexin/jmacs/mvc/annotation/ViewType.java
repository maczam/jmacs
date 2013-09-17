package info.hexin.jmacs.mvc.annotation;

/**
 * 返回视图 <code>
 * ACTION = "action"; 
 * FORWARD = "forward"; 
 * REDIRECT = "redirect";
 * FREEMARKER = "fmt"; 
 * FREEMARKER2 = "fm"; 
 * VELOCITY = "vt"; 
 * VELOCITY2 = "vm";
 * OUT = "out"; 
 * JSP = "jsp";
 * JSON="json"
 * </code>
 * 
 * @author ydhexin@gmail.com
 * 
 */
public enum ViewType {
    JSP, JSON, FM, VM, OUT
}
