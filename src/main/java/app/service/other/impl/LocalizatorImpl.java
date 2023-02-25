package app.service.other.impl;


import app.exceptions.InitializationError;
import app.service.other.Localizator;
import app.utils.ResourceUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class LocalizatorImpl implements Localizator {
    private static final Logger logger = LogManager.getLogger(LocalizatorImpl.class);

    private static LocalizatorImpl instance;

    private final Map<String, Map<String, String>> map = new HashMap<>();

    private LocalizatorImpl() {
        try {
            map.put("eng", ResourceUtil.getResourceAsMap("localization/resource-bundle-en.json"));
            map.put("ukr", ResourceUtil.getResourceAsMap("localization/resource-bundle-ua.json"));

            logger.info("Localizator impl initialization finished successfully");
        } catch (IOException exception) {
            logger.fatal("Localizator impl initialization failed", exception);
            throw new InitializationError("Localizator impl initialization failed", exception);
        }
    }

    @Override
    public String localize(String language, String key) {
        if (!map.containsKey(language)) {
            throw new RuntimeException("Unknown language: " + language);
        }

        Map<String, String> map1 = map.get(language);

        if (!map1.containsKey(key)) {
            logger.warn("No value for localization key = "+key+" (language = "+language+")");
            return "No value for key: "+key;
        }

        return map1.get(key);
    }

    public static LocalizatorImpl getInstance() {
        if (instance == null) instance = new LocalizatorImpl();
        return instance;
    }
}
