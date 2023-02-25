package app.annotations;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks parameters of http request method handler as http session attribute
 * (also http session attributes can be extracted using {@link javax.servlet.http.HttpSession#getAttribute(String)})
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface HttpSessionAttribute {
    /**
     * Name of http session attribute
     **/
    String name();

    /**
     * True if http session attribute must exists and false if it can contain null value
     **/
    boolean required() default true;
}
