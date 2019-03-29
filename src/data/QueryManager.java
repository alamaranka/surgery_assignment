package data;

import java.sql.*;

public class QueryManager {

    public static Statement query(Connection conn) throws SQLException {
        return conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    public static ResultSet resultSet (Statement statement, String str) throws SQLException {
        return statement.executeQuery(str);
    }

    public static void printResultSet(ResultSet resultSet) throws SQLException{
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print("\t");
                String columnValue = resultSet.getString(i);
                System.out.print("[" + rsmd.getColumnName(i) + "] " + columnValue);
            }
            System.out.println();
        }
    }

    public static void processException (SQLException e){
        System.err.println("Error message: " + e.getMessage());
        System.err.println("Error code: " + e.getErrorCode());
        System.err.println("SQL state: " + e.getSQLState());
    }

}
