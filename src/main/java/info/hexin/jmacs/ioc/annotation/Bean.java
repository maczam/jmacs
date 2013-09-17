package info.hexin.jmacs.ioc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 所有需要使用的组件都必须标记这个注解，包括Action、Service、Dao等组件，如果没有标记的话。不能使用框架来管理bean的生命周期
 * 
 * @author hexin
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
public @interface Bean {

    String name() default "";

    Scope scope() default Scope.singleton;
}
