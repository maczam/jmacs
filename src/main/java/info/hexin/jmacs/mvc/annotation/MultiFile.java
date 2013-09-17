package info.hexin.jmacs.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 文件上传，绑定文件域的名称。。 input的name值
 * 
 * @author ydhexin@gmail.com
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
@Documented
public @interface MultiFile {

    /**
     * 文件域的名称，同一个from可以上传多个文件
     * 
     * @return
     */
    String value() default "";
}
