package connectDB;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/products?useSSL=false";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "123456";

    private Connector() {
    }

    public static java.sql.Connection getConnection() {
        java.sql.Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
