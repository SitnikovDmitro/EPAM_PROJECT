package app.controller;

import app.annotations.HttpRequestMapping;
import app.annotations.HttpRequestParameter;
import app.enums.HttpMethod;
import app.service.entity.PublisherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;


public class PublisherController {
    private static final Logger logger = LogManager.getLogger(PublisherController.class);

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @HttpRequestMapping(path = "/publishers/find", method = HttpMethod.GET)
    public void findPublishers(@HttpRequestParameter(name = "query", required = false) String query,
                               HttpServletResponse response) throws Exception {

        logger.debug("Call find publishers in publisher controller");

        response.setContentType("text/json; charset=utf-8");
        new ObjectMapper().writer().withRootName("publishers").writeValue(response.getWriter(), publisherService.findPublishersTitlesByTitle(query));
    }
}
