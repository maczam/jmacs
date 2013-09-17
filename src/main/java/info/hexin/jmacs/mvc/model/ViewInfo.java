package info.hexin.jmacs.mvc.model;

import info.hexin.jmacs.mvc.annotation.ViewType;

/**
 * 返回视图
 * 
 * @author hexin
 * 
 */
public class ViewInfo {

    private String path;
    private ViewType viewType;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ViewType getViewType() {
        return viewType;
    }

    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
    }
}
