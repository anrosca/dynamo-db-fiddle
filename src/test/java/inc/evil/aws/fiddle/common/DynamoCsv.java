package inc.evil.aws.fiddle.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DynamoCsv {
    String value() default "";
    Class<?> entityType();
    boolean reCreateTable() default true;
}
