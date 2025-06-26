package services;

import controllers.auth.LoginRequest;
import database.DataBase;
import database.MySqlDb;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService implements IDbExecutorService {
    private final DataBase db;

    public LoginService() {
        this.db = MySqlDb.getDataBase();
        createTable();
        seedAdminUser();
    }

    @SneakyThrows
    private void seedAdminUser() {
        String checkUserSql = "SELECT COUNT(*) FROM " + tableName() + " WHERE login = ?";
        try (PreparedStatement checkStatement = db.dbConnection().prepareStatement(checkUserSql)) {
            checkStatement.setString(1, "admin");
            try (ResultSet rs = checkStatement.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    System.out.println("Admin user not found, creating one...");
                    signUp(new LoginRequest("admin", "admin"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Could not check for admin user, probably because tables are being created. " + e.getMessage());
        }
    }

    public boolean signIn(LoginRequest authData) {
        String sql = "SELECT * FROM " + tableName() + " WHERE login = ? AND password = ?";
        try (PreparedStatement statement = db.dbConnection().prepareStatement(sql)) {
            statement.setString(1, authData.login());
            statement.setString(2, authData.password());

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (Exception e) {
            System.out.println("Error during signIn: " + e.getMessage());
            return false;
        }
    }


    @SneakyThrows
    public boolean signUp(LoginRequest authData) {
        String sql = "INSERT INTO " + tableName() + " (login, password) VALUES (?, ?)";
        try (PreparedStatement statement = db.dbConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, authData.login());
            statement.setString(2, authData.password());
            db.insert(statement);
            return true;
        } catch (Exception e) {
            System.err.println("Error during signUp (user might already exist): " + e.getMessage());
            return false;
        }
    }

    @Override
    public void createTable() {
        db.createTable(tableName(), "id BIGINT PRIMARY KEY AUTO_INCREMENT, login VARCHAR(255) UNIQUE, password VARCHAR(255)");
    }

    @Override
    public String tableName() {
        return "UserAccounts";
    }
}