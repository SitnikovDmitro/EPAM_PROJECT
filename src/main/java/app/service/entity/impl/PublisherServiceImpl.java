package app.service.entity.impl;

import app.database.dao.PublisherDAO;
import app.entity.Publisher;
import app.exceptions.DatabaseException;
import app.service.entity.PublisherService;

import java.util.List;
import java.util.stream.Collectors;

public class PublisherServiceImpl implements PublisherService {
    private final PublisherDAO publisherDAO;

    public PublisherServiceImpl(PublisherDAO publisherDAO) {
        this.publisherDAO = publisherDAO;
    }

    @Override
    public List<Publisher> findPublishersByTitle(String title) throws DatabaseException {
        return publisherDAO.findPublishersByTitle(title);
    }

    @Override
    public List<String> findPublishersTitlesByTitle(String title) throws DatabaseException {
        if (title == null || title.isBlank()) {
            return List.of();
        }

        return publisherDAO.findPublishersByTitle(title).stream().map(Publisher::getTitle).collect(Collectors.toList());
    }
}
