package info.hexin.jmacs.mvc.handler.mapping;

import java.util.Map;

/**
 * 默认
 * 
 * @author hexin
 * 
 */
public class UrlMatcher {
    private final Map<String, String> uriTemplateVariables;
    private final boolean matches;

    public UrlMatcher(boolean isMatches, Map<String, String> uriTemplateVariables) {
        this.matches = isMatches;
        this.uriTemplateVariables = uriTemplateVariables;
    }

    public Map<String, String> getMatchedMap() {
        return uriTemplateVariables;
    }

    public boolean matched() {
        return matches;
    }
}
