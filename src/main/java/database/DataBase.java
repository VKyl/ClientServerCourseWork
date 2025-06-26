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

    public Connection dbConnection (){
        return dbConnection;
    }

    public long insert(PreparedStatement statement) throws SQLException {
        statement.executeUpdate();
        try (ResultSet keys = statement.getGeneratedKeys()) {
            if (keys.next()) {
                return keys.getLong(1);
            } else {
                throw new SQLException("No ID returned from insert query");
            }
        }
    }

    public int update(PreparedStatement statement) throws SQLException {
        return statement.executeUpdate();
    }

    public boolean delete(PreparedStatement statement) throws SQLException {
        return statement.executeUpdate() > 0;
    }

    public void createTable(String tableName, String columnsQuery) {
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%s);", tableName, columnsQuery);
        try (PreparedStatement st = dbConnection.prepareStatement(sql)) {
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws SQLException {
        if (dbConnection != null && !dbConnection.isClosed()) {
            dbConnection.close();
        }
    }
}