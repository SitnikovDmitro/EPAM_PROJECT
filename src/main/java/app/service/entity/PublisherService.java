package app.service.entity;

import app.entity.Publisher;
import app.exceptions.DatabaseException;

import java.util.List;

/**
 * Provides operations with user entity
 **/
public interface PublisherService {
    /**
     * Finds publishers by title
     * @param title publisher search title query
     * @return list of publishers that matches query
     */
    List<Publisher> findPublishersByTitle(String title) throws DatabaseException;

    /**
     * Finds publishers by title
     * @param title publisher search title query
     * @return list of titles of publishers that matches query
     */
    List<String> findPublishersTitlesByTitle(String title) throws DatabaseException;
}
