package data;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        ConnectionManager connectionManager = new ConnectionManager("scratch");
        new DataManager().perform();
        connectionManager.close();

    }

}
