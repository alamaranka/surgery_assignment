package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String USERNAME = "dbuser";
    private static final String PASSWORD = "Samaritan.29";
    private static String connectionString;
    private Connection conn = null;

    private static ConnectionManager connInstance = new ConnectionManager(connectionString);

    public ConnectionManager(String databaseName) {
        connectionString = "jdbc:mysql://localhost/" + databaseName;
    }

    public static ConnectionManager getInstance() {
        if(connInstance == null){
            connInstance = new ConnectionManager(connectionString);
        }
        return connInstance;
    }

    private boolean openConnection(){
        try{
            conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
            return true;
        } catch (SQLException e){
            System.err.println(e);
            return false;
        }
    }

    public Connection getConnection() {
        if (conn == null) {
            if (openConnection()) {
                System.out.println("Connection opened!");
                return conn;
            } else {
                return null;
            }
        }
        return conn;
    }

    public void close() {
        System.out.println("Connection closed!");
        try {
            conn.close();
            conn = null;
        } catch (Exception e) {
        }
    }
}
