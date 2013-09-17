package info.hexin.jmacs.mvc.view.velocity;

import java.io.File;
import java.net.URL;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import info.hexin.jmacs.ioc.InitBean;

/**
 * Velocity初始化配置
 * 
 * @author hexin
 * 
 */
public class VelocityConfigurer implements InitBean {

    private String templatePath;
    private VelocityEngine velocityEngine;

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    @Override
    public void afterPropertiesSet() {
        velocityEngine = new VelocityEngine();
        URL url = Thread.currentThread().getContextClassLoader().getResource(templatePath);
        String tempPath = (new File(url.getPath())).getAbsolutePath();

        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
        velocityEngine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
        velocityEngine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, tempPath);
        velocityEngine.init();
    }
}
