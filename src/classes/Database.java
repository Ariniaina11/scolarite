package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private Connection Connection;

    public Database() throws SQLException {
        this.connexion();
    }

    void connexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/dbscolarite";
        String user = "root";
        String password = "";
        this.Connection = DriverManager.getConnection(url, user, password);
    }

    public Connection getConnection() {
        return this.Connection;
    }

    public void setConnection(Connection connection) {
        this.Connection = connection;
    }
}
