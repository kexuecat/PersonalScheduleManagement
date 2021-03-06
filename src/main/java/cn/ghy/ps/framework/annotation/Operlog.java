package cn.ghy.ps.framework.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解 拦截Controller
 *
 * @author 穹镜
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Operlog{
    /**
     * 描述业务操作
     */
    String modal() default "";

    /**
     * 操作类型
     */
    String descr() default "";

}
