package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataManager {

    Connection connection = ConnectionManager.getInstance().getConnection();

    public String getTable(){
        String query = "SHOW TABLES;";
        try
            (
                Statement statement = QueryManager.query(connection);
                ResultSet resultSet = QueryManager.resultSet(statement, query);
            )
        {
            List<String> tables = new ArrayList<>();
            System.out.println("\u001B[1mWhich table you want to work with? (select the number from below list!): \u001B[0m");
            while (resultSet.next()){
                String s = resultSet.getString(1);
                tables.add(s);
                System.out.println(resultSet.getRow() + " -> " + s);
            }
            String table = tables.get(new Scanner(System.in).nextInt() - 1);
            System.out.println("'" + table + "'" + " table selected!");
            System.out.println("-------------------------------------");
            return table;
        } catch (SQLException e) {
            QueryManager.processException(e);
            return null;
        } catch (IndexOutOfBoundsException e){
            System.err.println("No such table. Please make a valid selection!");
            return getTable();
        }
    }

    public String getQueryType(String tableName) {
        final String[] queryList = {"INSERT", "UPDATE", "DELETE"};
        System.out.println("\u001B[1mWhich query type you want to perform? (select the number from below list!): \u001B[0m");
        for (int i = 0; i < queryList.length; i++) {
            System.out.println((i+1) + " -> " + queryList[i]);
        }

        try {
            String query = queryList[new Scanner(System.in).nextInt()-1];
            System.out.println("'" + query + "'" + " query type selected!");
            System.out.println("-------------------------------------");
            return query;
        }
        catch (IndexOutOfBoundsException e){
            System.err.println("No such query. Please make a valid selection!");
            return getQueryType(tableName);
        }

    }

    public void perform() {
        String tableName = getTable();
        String queryType = getQueryType(tableName);
        switch (queryType){
            case "INSERT":
                insertRow();
        }
    }

    public boolean insertRow(){

        return false;
    }



}
