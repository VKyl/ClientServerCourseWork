package database;

public class MySqlDb {
    private final DataBase db;
    private static MySqlDb instance = null;
    private MySqlDb(DatabaseConfig config) {
        db = new DataBase("com.mysql.cj.jdbc.Driver", "jdbc:mysql://" + config.getHost() + "/" + config.getDatabase() + "?createDatabaseIfNotExist=true", config.getUsername(), config.getPassword() );
    }


    public static DataBase getDataBase() {
        if (instance == null) {
            instance = new MySqlDb(new DatabaseConfig());
        }
        return instance.db;
    }
}
