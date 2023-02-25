package app.annotations;

import app.enums.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Marks method as http request handler
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HttpRequestMapping {
    /**
     * Http url address to be processed by this request handler
     **/
    String path();

    /**
     * Http request method (GET or POST)
     **/
    HttpMethod method();
}
