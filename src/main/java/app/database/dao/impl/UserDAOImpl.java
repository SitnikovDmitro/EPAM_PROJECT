package app.database.dao.impl;

import app.database.dao.UserDAO;
import app.database.pool.ConnectionPool;
import app.database.utils.ResultSetExtractor;
import app.entity.User;
import app.enums.OrderState;
import app.enums.UserRole;
import app.exceptions.DatabaseException;
import app.utils.QueryBuilder;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class UserDAOImpl implements UserDAO {
    private static final String FIND_ALL_SQL = "SELECT * FROM users;";
    private static final String FIND_USER_BY_ID_SQL = "SELECT * FROM users WHERE id = ?;";
    private static final String FIND_USER_BY_EMAIL_SQL = "SELECT * FROM users WHERE email = ?;";
    private static final String DELETE_USER_BY_ID_SQL = "DELETE FROM users WHERE id = ?;";
    private static final String COUNT_MISSED_DAYS_SQL = "SELECT SUM(DATEDIFF(deadlineDate, ?)) FROM orders WHERE userId = ? AND state = "+ OrderState.CONFIRMED +" AND deadlineDate <> NULL AND deadlineDate < ?;";
    private static final String CREATE_USER_SQL = "INSERT INTO users (id, fine, isBlocked, email, firstname, lastname, passwordHash, role, lastFineRecalculationDate) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_USER_SQL = "UPDATE users SET fine = ?, isBlocked = ?, email = ?, firstname = ?, lastname = ?, passwordHash = ?, role = ?, lastFineRecalculationDate = ? WHERE id = ?;";
    private static final String EXISTS_USER_BY_EMAIL_SQL = "SELECT EXISTS (SELECT * FROM users WHERE email = ?);";

    private final ConnectionPool connectionPool;

    public UserDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<User> findAll() throws DatabaseException {
        try (Connection con = connectionPool.getConnection();
             Statement stmt = con.createStatement()) {

            ResultSet set = stmt.executeQuery(FIND_ALL_SQL);
            List<User> result = ResultSetExtractor.extractUsers(set);
            set.close();
            return result;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }


    @Override
    public List<User> findUsers(String query, UserRole role, boolean onlyBlocked, boolean onlyWithFine, int page, int recordsPerPage) throws DatabaseException {
        StringJoiner conditions = new StringJoiner(" AND ");
        if (role != null) conditions.add("role = "+ role.toInt());
        if (query != null && !query.isBlank()) conditions.add("(LOWER(firstname) LIKE ? OR LOWER(lastname) LIKE ? OR LOWER(email) LIKE ?)");
        if (onlyBlocked) conditions.add("isBlocked = true");
        if (onlyWithFine) conditions.add("fine > 0");

        QueryBuilder builder = new QueryBuilder();
        String sql = builder.select("*").from("users").
                             where(conditions.toString()).
                             order("firstname", "ASC").
                             limit((page-1)*recordsPerPage, recordsPerPage).build();

        System.err.println(sql);

        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            if (query != null && !query.isBlank()) {
                stmt.setString(1, "%"+query.toLowerCase()+"%");
                stmt.setString(2, "%"+query.toLowerCase()+"%");
                stmt.setString(3, "%"+query.toLowerCase()+"%");
            }

            ResultSet set = stmt.executeQuery();
            List<User> result = ResultSetExtractor.extractUsers(set);
            set.close();
            return result;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public int countUsers(String query, UserRole role, boolean onlyBlocked, boolean onlyWithFine) throws DatabaseException {
        StringJoiner conditions = new StringJoiner(" AND ");
        if (role != null) conditions.add("role = "+ role.toInt());
        if (query != null && !query.isBlank()) conditions.add("(LOWER(firstname) LIKE ? OR LOWER(lastname) LIKE ? OR LOWER(email) LIKE ?)");
        if (onlyBlocked) conditions.add("isBlocked = true");
        if (onlyWithFine) conditions.add("fine > 0");

        QueryBuilder builder = new QueryBuilder();
        String sql = builder.select("COUNT(*)").from("users").where(conditions.toString()).build();

        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            if (query != null && !query.isBlank()) {
                stmt.setString(1, "%"+query.toLowerCase()+"%");
                stmt.setString(2, "%"+query.toLowerCase()+"%");
                stmt.setString(3, "%"+query.toLowerCase()+"%");
            }

            ResultSet set = stmt.executeQuery();
            set.next();
            int count = set.getInt(1);
            set.close();
            return count;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }


    @Override
    public Optional<User> findUserById(int userId) throws DatabaseException {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_USER_BY_ID_SQL)) {

            stmt.setInt(1, userId);

            ResultSet set = stmt.executeQuery();
            Optional<User> user = ResultSetExtractor.extractUser(set);
            set.close();

            return user;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DatabaseException {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_USER_BY_EMAIL_SQL)) {

            stmt.setString(1, email);

            ResultSet set = stmt.executeQuery();
            List<User> users = ResultSetExtractor.extractUsers(set);
            set.close();

            if (users.size() > 1) throw new DatabaseException("Duplication of unique column");
            return users.size() == 0 ? Optional.empty() : Optional.of(users.get(0));
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public void deleteUserById(int userId, Connection connection) throws DatabaseException {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_USER_BY_ID_SQL)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public int countMissedDays(int userId, LocalDate currentDate) throws DatabaseException {
        if (true) return 0;

        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(COUNT_MISSED_DAYS_SQL)) {

            stmt.setDate(1, Date.valueOf(currentDate));
            stmt.setInt(2, userId);
            stmt.setDate(3, Date.valueOf(currentDate));

            ResultSet set = stmt.executeQuery();
            set.next();
            int sum = set.getInt(1);
            set.close();
            return sum;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }




    @Override
    public void createUser(User user, Connection connection) throws DatabaseException {
        try(PreparedStatement stmt = connection.prepareStatement(CREATE_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            int pos = 1;
            stmt.setInt(pos++, user.getFine());
            stmt.setBoolean(pos++, user.isBlocked());
            stmt.setString(pos++, user.getEmail());
            stmt.setString(pos++, user.getFirstname());
            stmt.setString(pos++, user.getLastname());
            stmt.setString(pos++, user.getPasswordHash());
            stmt.setInt(pos++, user.getRole().toInt());
            stmt.setDate(pos, Date.valueOf(user.getLastFineRecalculationDate()));

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            user.setId(rs.getInt(1));
            rs.close();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public void updateUser(User user, Connection connection) throws DatabaseException {
        try(PreparedStatement stmt = connection.prepareStatement(UPDATE_USER_SQL)) {

            int pos = 1;
            stmt.setInt(pos++, user.getFine());
            stmt.setBoolean(pos++, user.isBlocked());
            stmt.setString(pos++, user.getEmail());
            stmt.setString(pos++, user.getFirstname());
            stmt.setString(pos++, user.getLastname());
            stmt.setString(pos++, user.getPasswordHash());
            stmt.setInt(pos++, user.getRole().toInt());
            stmt.setDate(pos++, Date.valueOf(user.getLastFineRecalculationDate()));
            stmt.setInt(pos, user.getId());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public boolean existsUserByEmail(String email) throws DatabaseException {
        try(Connection con = connectionPool.getConnection();
            PreparedStatement stmt = con.prepareStatement(EXISTS_USER_BY_EMAIL_SQL)) {

            stmt.setString(1, email);
            ResultSet set = stmt.executeQuery();
            set.next();
            boolean exists = set.getBoolean(1);
            set.close();
            return exists;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }
}
