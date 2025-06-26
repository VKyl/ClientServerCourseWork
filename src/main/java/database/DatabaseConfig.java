package database;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;

@Getter
public class DatabaseConfig {
    private final String host;
    private final String database;
    private final String username;
    private final String password;

    public DatabaseConfig() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        host = getenv("DB_HOST", dotenv);
        database = getenv("DB_NAME", dotenv);
        username = getenv("DB_USER", dotenv);
        password = getenv("DB_PASS", dotenv);
    }

    private String getenv(String name, Dotenv dotenv) {
        String value = System.getenv(name);
        if (value != null) {
            return value;
        }
        return dotenv.get(name);
    }
}