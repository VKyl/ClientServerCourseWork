package database;

import java.sql.*;

public class DataBase implements AutoCloseable {
    protected final Connection dbConnection;

    public DataBase(String driverClass, String connectionString, String username, String password) {
        try {
            Class.forName(driverClass);
            dbConnection = DriverManager.getConnection(connectionString, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    public long insert(String rawInsertQuery) {
        try (PreparedStatement statement = dbConnection.prepareStatement(rawInsertQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                } else {
                    throw new RuntimeException("No ID returned from insert query");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Insert query failed", e);
        }
    }

    public ResultSet update(String rawUpdateQuery) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement(rawUpdateQuery);
            statement.executeUpdate();
            return statement.getResultSet();
        } catch (SQLException e) {
            throw new RuntimeException("Update query failed", e);
        }
    }

    public boolean delete(String rawDeleteQuery) {
        try (PreparedStatement statement = dbConnection.prepareStatement(rawDeleteQuery)) {
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Delete query failed", e);
        }
    }

    public ResultSet selectById(String rawSelectQuery) {
        try (PreparedStatement statement = dbConnection.prepareStatement(rawSelectQuery)) {
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Select by ID query failed", e);
        }
    }

    public ResultSet execQuery(String sql) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error listing with filters", e);
        }
    }

    public void createTable(String tableName) {
        String sql = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                        "name VARCHAR(1000), " +
                        "price DOUBLE);", tableName);

        try (PreparedStatement st = dbConnection.prepareStatement(sql)) {
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropTable(String tableName) {
        String sql = String.format("DROP TABLE IF EXISTS %s", tableName);
        try (Statement statement = dbConnection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to drop table", e);
        }
    }

    @Override
    public void close() {
        try {
            if (dbConnection != null && !dbConnection.isClosed()) {
                dbConnection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close database connection", e);
        }
    }
}
