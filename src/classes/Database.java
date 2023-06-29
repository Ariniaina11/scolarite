package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection Connection;

    public Database() throws SQLException {
        this.connexion();
    }

    public void connexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/dbscolarite";
        String user = "root";
        String password = "";
        this.Connection = DriverManager.getConnection(url, user, password);
    }

    public static Connection getConnection() {
        return Connection;
    }

    public void setConnection(Connection connection) {
        this.Connection = connection;
    }
}
