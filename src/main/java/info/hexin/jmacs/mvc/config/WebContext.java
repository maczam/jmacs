package info.hexin.jmacs.mvc.config;

import info.hexin.jmacs.ioc.context.Ioc;
import info.hexin.jmacs.mvc.handler.mapping.UrlMaping;

/**
 * 
 * @author hexin
 * 
 */
public class WebContext {

    private Ioc ioc;
    private UrlMaping urlMaping;
    private WebConfig webConfig;

    public Ioc getIoc() {
        return ioc;
    }

    public void setIoc(Ioc ioc) {
        this.ioc = ioc;
    }

    public UrlMaping getUrlMaping() {
        return urlMaping;
    }

    public void setUrlMaping(UrlMaping urlMaping) {
        this.urlMaping = urlMaping;
    }

    public WebConfig getWebConfig() {
        return webConfig;
    }

    public void setWebConfig(WebConfig webConfig) {
        this.webConfig = webConfig;
    }
}
