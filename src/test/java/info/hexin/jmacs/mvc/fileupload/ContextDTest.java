package info.hexin.jmacs.mvc.fileupload;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContextDTest {

	public static void main(String[] args) {
		String ss = "Content-Disposition: form-data; name=\"fileforload1\"; filename=\"新建文本文档 (2).txt\""
				+ "Content-Type: text/plain";

//		Pattern formNamePattern = Pattern.compile("[\\W]name=[\"|\']?([\\w|.]+)[\"|\']?");
//		Matcher formNameMatcher = formNamePattern.matcher(ss);
//		if(formNameMatcher.find()){
//			System.out.println(formNameMatcher.group(1));
//		}
		
		
//		Pattern fileNamePattern = Pattern.compile("filename=[\"|\']?([\\w|[\u4e00-\u9fa5]|.]+)[\"|\']?");
//		Matcher fileNameMatcher = fileNamePattern.matcher(ss);
//		if (fileNameMatcher.find()) {
//			System.out.println(fileNameMatcher.group(1));
//		}
		String [] s = ss.split("[\n;]");
		for (String string : s) {
			System.out.println(string);
		}
		
	}
}
