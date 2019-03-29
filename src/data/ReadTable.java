package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReadTable {

    Connection connection = ConnectionManager.getInstance().getConnection();

    public List<List<Object>> getData (String tableName) throws SQLException {
        String query = "SELECT * FROM " + tableName + ";";

        try
            (
                Statement statement = QueryManager.query(connection);
                ResultSet resultSet = QueryManager.resultSet(statement, query);
            )
        {
            List<List<Object>> data = new ArrayList<>();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            List<Object> listCol = new ArrayList<>();
            for (int i = 1; i <= columnsNumber; i++) {
                listCol.add(rsmd.getColumnName(i));
            }
            data.add(listCol);

            while (resultSet.next()){
                List<Object> list = new ArrayList<>();
                for (int i = 1; i <= columnsNumber; i++) {
                    Object obj = (Object) resultSet.getObject(i);
                    if(obj instanceof String){
                        list.add((String) obj);
                    } else if (obj instanceof Double){
                        list.add((double) obj);
                    }
                }
                data.add(list);
            }
            return data;
        }

        catch (SQLException e) {
            QueryManager.processException(e);
        }

        return null;
    }

}


