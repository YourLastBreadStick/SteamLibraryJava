package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GameDB {
    private static final String SERVER = "necc.trevnet.org:50000";
    private static final String DATABASE = "necc-java-db-05";
    private static final String USER = "necc-java-db-05";
    private static final String PASSWORD = "3:O,k~dfxTg1F5w:R4\"2oK\\lm";

    private static final String URL = String.format("jdbc:sqlserver://%s;databaseName=%s;" +
            "encrypt=true;trustServerCertificate=false;" +
            "loginTimeout=15;hostNameInCertificate=trevnet.org", SERVER, DATABASE);

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
