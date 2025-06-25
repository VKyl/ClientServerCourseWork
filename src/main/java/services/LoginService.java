package services;

import controllers.auth.LoginRequest;
import database.DataBase;
import database.MySqlDb;
import lombok.SneakyThrows;

public class LoginService implements IDbExecutorService{
    private final DataBase db;

    public LoginService() {
        db = MySqlDb.getDataBase();
    }

    @SneakyThrows
    public boolean signIn(LoginRequest authData) {
        String sql = "SELECT * FROM " + tableName() + " WHERE login = " + authData.login() + " AND password = " + authData.password();
        return db.execQuery(sql).next();
    }

    public boolean signUp(LoginRequest authData) {
        try{
            String sql = "INSERT INTO " + tableName() + " (login, password) VALUES (" + authData.login() + ", " + authData.password() + ")";
            db.insert(sql);
            return true;
        } catch (RuntimeException e) {
            System.out.println("Error inserting data: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void createTable() {
        db.createTable(tableName());
    }

    @Override
    public String tableName() {
        return "UserAccounts";
    }
}
