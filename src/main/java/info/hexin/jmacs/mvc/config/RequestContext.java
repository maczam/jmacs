package info.hexin.jmacs.mvc.config;

import info.hexin.jmacs.ioc.context.Ioc;
import info.hexin.jmacs.mvc.model.MappedInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * request 上下文
 * 
 * @author hexin
 * 
 */
public class RequestContext {
    private Ioc ioc;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private MappedInfo mappedInfo;

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public MappedInfo getMappedInfo() {
        return mappedInfo;
    }

    public void setMappedInfo(MappedInfo mappedInfo) {
        this.mappedInfo = mappedInfo;
    }

    public Ioc getIoc() {
        return ioc;
    }

    public void setIoc(Ioc ioc) {
        this.ioc = ioc;
    }
}
