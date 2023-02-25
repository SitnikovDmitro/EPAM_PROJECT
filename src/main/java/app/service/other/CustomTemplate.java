package app.service.other;

import java.util.Map;

/**
 * Template to build text content using parameter substitution
 **/
public interface CustomTemplate {
    /**
     * Build text content using parameter substitution
     * @param parameters map of string parameters to substitute in template
     * @return built text content
     */
    String generate(Map<String, String> parameters);
}
