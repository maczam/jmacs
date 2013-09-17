package info.hexin.lang;

import info.hexin.lang.string.Strings;

public class UrlPaths {

    /**
     * 将两个pattern 合并
     * 
     * @param pattern1
     * @param pattern2
     * @return
     */
    public static String combinePattern(String pattern1, String pattern2) {
        if (Strings.isBlank(pattern1) && Strings.isBlank(pattern2)) {
            return "";
        } else if (Strings.isBlank(pattern1)) {
            return pattern2;
        } else if (Strings.isBlank(pattern2)) {
            return pattern1;
        } else if (pattern1.endsWith("/*")) {
            if (pattern2.startsWith("/")) {
                // /hotels/* + /booking -> /hotels/booking
                return pattern1.substring(0, pattern1.length() - 1) + pattern2.substring(1);
            } else {
                // /hotels/* + booking -> /hotels/booking
                return pattern1.substring(0, pattern1.length() - 1) + pattern2;
            }
        } else if (pattern1.endsWith("/**")) {
            if (pattern2.startsWith("/")) {
                // /hotels/** + /booking -> /hotels/**/booking
                return pattern1 + pattern2;
            } else {
                // /hotels/** + booking -> /hotels/**/booking
                return pattern1 + "/" + pattern2;
            }
        } else {
            int dotPos1 = pattern1.indexOf('.');
            if (dotPos1 == -1) {
                // simply concatenate the two patterns
                if (pattern1.endsWith("/") || pattern2.startsWith("/")) {
                    return pattern1 + pattern2;
                } else {
                    return pattern1 + "/" + pattern2;
                }
            }
            String fileName1 = pattern1.substring(0, dotPos1);
            String extension1 = pattern1.substring(dotPos1);
            String fileName2;
            String extension2;
            int dotPos2 = pattern2.indexOf('.');
            if (dotPos2 != -1) {
                fileName2 = pattern2.substring(0, dotPos2);
                extension2 = pattern2.substring(dotPos2);
            } else {
                fileName2 = pattern2;
                extension2 = "";
            }
            String fileName = fileName1.endsWith("*") ? fileName2 : fileName1;
            String extension = extension1.startsWith("*") ? extension2 : extension1;

            return fileName + extension;
        }
    }
}
