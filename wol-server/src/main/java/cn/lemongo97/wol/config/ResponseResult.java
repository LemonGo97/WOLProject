package cn.lemongo97.wol.config;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author LemonGo97
 */
@Retention(RUNTIME)
@Target({TYPE,METHOD})
@Documented
public @interface ResponseResult {
}
