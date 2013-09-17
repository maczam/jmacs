package info.hexin.jmacs.mvc.config;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * 
 * Web配置
 * 
 * @author hexin
 * 
 */
public class WebConfig {

    private Pattern exclusionPattern;
    private String xmls;
    private String packages;
    private String filterName;
    private Collection<String> collectionMaping;

    public Pattern getExclusionPattern() {
        return exclusionPattern;
    }

    public void setExclusionPattern(Pattern exclusionPattern) {
        this.exclusionPattern = exclusionPattern;
    }

    public String getXmls() {
        return xmls;
    }

    public void setXmls(String xmls) {
        this.xmls = xmls;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public Collection<String> getCollectionMaping() {
        return collectionMaping;
    }

    public void setCollectionMaping(Collection<String> collectionMaping) {
        this.collectionMaping = collectionMaping;
    }
}
