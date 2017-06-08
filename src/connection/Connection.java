package connection;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Connection {
    public static final String URL = "jdbc:mysql://localhost:3306/form?autoReconnect=true&useSSL=false";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";
    static java.sql.Connection connection;
    public static void connection(){    //метод для встановлення зв'язку з БД
        try {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static java.sql.Connection getConnection() {
        return connection;
    }
}
