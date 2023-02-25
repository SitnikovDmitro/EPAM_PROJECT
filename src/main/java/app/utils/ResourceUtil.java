package app.utils;

import app.exceptions.InitializationError;
import app.service.other.impl.CustomTemplateImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides basic resource loading operations
 **/
public class ResourceUtil {
    /**
     * Extracts resource as byte array
     * @param path path to resource file
     * @return resource data in byte array
     **/
    public static byte[] getResourceAsByteArray(String path) throws IOException {
        try (InputStream stream = ResourceUtil.class.getClassLoader().getResourceAsStream(path)) {

            if (stream == null) {
                throw new IOException("Resource not found by path: "+path);
            }

            return stream.readAllBytes();
        }
    }

    /**
     * Extracts json resource
     * @param path path to resource json file
     * @return resource data in json object
     **/
    public static JsonNode getResourceAsJson(String path) throws IOException {
        try (InputStream stream = ResourceUtil.class.getClassLoader().getResourceAsStream(path)) {

            if (stream == null) {
                throw new IOException("Resource not found by path: "+path);
            }

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readTree(stream);
        }
    }

    /**
     * Extracts resource as string
     * @param path path to resource file
     * @return resource data in string
     **/
    public static String getResourceAsString(String path) throws IOException {
        return new String(getResourceAsByteArray(path), StandardCharsets.UTF_8);
    }

    /**
     * Extracts json resource as map
     * @param path path to resource json file
     * @return resource data of json resource in map
     **/
    public static Map<String, String> getResourceAsMap(String path) throws IOException {
        JsonNode json = getResourceAsJson(path);
        Map<String, String> map = new HashMap<>();

        json.fields().forEachRemaining((entry) -> map.put(entry.getKey(), entry.getValue().asText()));

        return map;
    }
}

