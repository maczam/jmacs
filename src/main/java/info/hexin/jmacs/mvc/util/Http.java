package info.hexin.jmacs.mvc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Http {

	/**
	 * 从contentType获取form name
	 */
	public final static Pattern FORM_NAME_PATTERN = Pattern.compile("[\\W]name=[\"|\']?([\\w|.]+)[\"|\']?");
	/**
	 * 从contentType获取fileName
	 */
	public final static Pattern FILE_NAME_PATTERN = Pattern.compile("filename=[\"|\']?([\\w|.]+)[\"|\']?");

	private static Pattern BONDARY_PATTEN = Pattern.compile("(=)(----[\\w]+)");

	public static String bondary(String contentType) {
		Matcher matcher = BONDARY_PATTEN.matcher(contentType);
		try {
			if (matcher.find()) {
				return matcher.group(2);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}
}
