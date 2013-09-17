package info.hexin.jmacs.mvc.handler.mapping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 请求url匹配
 * 
 * @author hexin
 * 
 */
public class UrlPattern {

    private static final Pattern GLOB_PATTERN = Pattern.compile("\\?|\\*|\\{((?:\\{[^/]+?\\}|[^/{}]|\\\\[{}])+?)\\}");
    private static final String DEFAULT_VARIABLE_PATTERN = "(.*)";
    private Pattern pattern;
    private List<String> variableNames = new LinkedList<String>();

    public UrlMatcher mather(String url) {
        Matcher matcher = pattern.matcher(url);
        Map<String, String> uriTemplateVariables = null;
        boolean matches = matcher.matches();
        if (matches) {
            uriTemplateVariables = new HashMap<String, String>();
            if (uriTemplateVariables != null) {
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    String name = this.variableNames.get(i - 1);
                    String value = matcher.group(i);
                    uriTemplateVariables.put(name, value);
                }
            }
        }
        return new UrlMatcher(matches, uriTemplateVariables);
    }

    public static UrlPattern compile(String pattern) {
        UrlPattern urlPattern = new UrlPattern();
        StringBuilder patternBuilder = new StringBuilder();
        Matcher m = GLOB_PATTERN.matcher(pattern);
        int end = 0;
        while (m.find()) {
            patternBuilder.append(quote(pattern, end, m.start()));
            String match = m.group();
            if ("?".equals(match)) {
                patternBuilder.append('.');
            } else if ("*".equals(match)) {
                patternBuilder.append(".*");
            } else if (match.startsWith("{") && match.endsWith("}")) {
                int colonIdx = match.indexOf(':');
                if (colonIdx == -1) {
                    patternBuilder.append(DEFAULT_VARIABLE_PATTERN);
                    urlPattern.variableNames.add(m.group(1));
                } else {
                    String variablePattern = match.substring(colonIdx + 1, match.length() - 1);
                    patternBuilder.append('(');
                    patternBuilder.append(variablePattern);
                    patternBuilder.append(')');
                    String variableName = match.substring(1, colonIdx);
                    urlPattern.variableNames.add(variableName);
                }
            }
            end = m.end();
        }
        patternBuilder.append(quote(pattern, end, pattern.length()));
        urlPattern.pattern = Pattern.compile(patternBuilder.toString());
        return urlPattern;
    }

    private static String quote(String s, int start, int end) {
        if (start == end) {
            return "";
        }
        return Pattern.quote(s.substring(start, end));
    }
}
