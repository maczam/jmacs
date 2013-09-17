package info.hexin.lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Files {

    private static final String USER_DIR = System.getenv("user.dir");

    /**
     * 获取文件的输入流
     * 
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public static InputStream fromPath(String fileName) throws FileNotFoundException {
        String filePath = null;
        // 从当前用户的目录中获取
        if (isHomeDir(fileName)) {
            filePath = USER_DIR + fileName.substring(1);
            return new FileInputStream(filePath);
        }
        // 从当前的classpath中获取
        if (!fileName.startsWith("/")) {
            filePath = "/" + fileName;
            return Files.class.getResourceAsStream(filePath);
        }

        // 从文件系统中获取
        else {
            return new FileInputStream(fileName);
        }
    }

    /**
     * 创建一个文件，并获取文件的句柄
     * 
     * @param fileName
     * @return
     */
    public static File create(String fileName) {
        return null;
    }

    /**
     * 判断文件是否为当前用户目录下面
     * 
     * @param fileName
     * @return
     */
    private static boolean isHomeDir(String fileName) {
        return fileName.charAt(0) == '~';
    }
    
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir")+File.separatorChar+".jmacs"+File.separatorChar);
    }
}
