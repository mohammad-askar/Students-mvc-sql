package db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import org.w3c.dom.ls.LSOutput;

import javax.sql.DataSource;

public class DataSourceFactory {
    // singleton lazy // 8 gb ram
    private static HikariDataSource ds;
    private static final Dotenv dotenv = Dotenv.configure()
            .directory("./")        // Optional if .env is in project root
            .ignoreIfMissing()      // Avoid crash if missing (optional)
            .load();
    static String DB_USER = dotenv.get("DB_USER");
    static String DB_PASS = dotenv.get("DB_PASS");

    public static DataSource get(){
        if(ds == null){
            HikariConfig cfg = new HikariConfig();
            cfg.setJdbcUrl(dotenv.get("DATABASE_URL"));
            cfg.setUsername(System.getenv().getOrDefault("DB_USER", DB_USER));
            cfg.setPassword(System.getenv().getOrDefault("DB_PASS", DB_PASS));
            cfg.setMaximumPoolSize(4);
            cfg.setMinimumIdle(1);
            cfg.setPoolName("StudentPool");
            ds = new HikariDataSource(cfg);

        }
        return ds;
    }

    public static void close(){
        if(ds != null){
            ds.close();
        }
    }
}
