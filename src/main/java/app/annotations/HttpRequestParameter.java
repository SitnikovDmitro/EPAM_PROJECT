package app.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks parameters of http request method handler as http request parameters
 * (also http request parameters can be extracted using {@link javax.servlet.http.HttpServletRequest#getParameter(String)}).
 * Can be applied to parameters with types {@link String}, {@link Integer}, {@link Boolean},
 * {@link java.time.LocalDate}, {@link java.time.LocalDateTime}. If method parameter type is not a {@link String}
 * real http request parameter will be converted (if it possible) to actual method parameter type
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface HttpRequestParameter {
    /**
     * Name of http request parameter
     **/
    String name();

    /**
     * Default string value of http request parameter (applied if actual string value is empty or null)
     **/
    String defaultValue() default "DEFAULT";

    /**
     * True if http request parameter must exists and false if it can contain null value
     **/
    boolean required() default true;
}
