package app.database.dao.impl;

import app.database.dao.PublisherDAO;
import app.database.pool.ConnectionPool;
import app.database.utils.ResultSetExtractor;
import app.entity.Publisher;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.List;

public class PublisherDAOImpl implements PublisherDAO {
    private static final String FIND_PUBLISHERS_BY_TITLE_SQL = "SELECT * FROM publishers WHERE LOWER(title) LIKE ? LIMIT 4;";
    private static final String SAVE_PUBLISHER_SQL = "INSERT INTO publishers (id, title) VALUES (NULL, ?) ON DUPLICATE KEY UPDATE title = ?;";

    private final ConnectionPool connectionPool;

    public PublisherDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }


    @Override
    public void savePublisher(Publisher publisher, Connection connection) throws DatabaseException {
        try (PreparedStatement stmt = connection.prepareStatement(SAVE_PUBLISHER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            int pos = 1;
            stmt.setString(pos++, publisher.getTitle());
            stmt.setString(pos, publisher.getTitle());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                publisher.setId(rs.getInt(1));
            }
            rs.close();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }


    @Override
    public List<Publisher> findPublishersByTitle(String titleQuery) throws DatabaseException {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_PUBLISHERS_BY_TITLE_SQL)) {

            stmt.setString(1, "%"+titleQuery.toLowerCase()+"%");

            ResultSet set = stmt.executeQuery();
            List<Publisher> publishers = ResultSetExtractor.extractPublishers(set);
            set.close();
            return publishers;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

}
