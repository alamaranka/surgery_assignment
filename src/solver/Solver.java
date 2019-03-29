package solver;
import data.ConnectionManager;
import data.ReadTable;
import model.AssignmentModel;

import java.sql.SQLException;
import java.util.List;

public class Solver {

    public static void main(String[] args) throws SQLException {

        /*// set of surgery types
        double[] surgType = {1,2,3,4,5};
        // surgery types for each patient
        double[] surgTypesPat = {1,2,4,4,3,3,1,5,2,1,2,2,5,4,3,3,1,2,5,5,2,1,1,1,2,5,5,3,2,2};
        // we have 30 patients to have surgery next week...
        double[] durations = {6,4,6,6,4,4,6,4,4,6,4,4,4,6,4,4,6,4,4,4,4,6,6,6,4,4,4,4,4,4};
        // each (three) operation room has different capacities for each (six) day...
        double[][] capacities = {   {9,9,6},
                {9,9,6},
                {9,9,9},
                {9,9,9},
                {6,9,6},
                {6,0,6}
        };
        // operation rooms have tech and equipment for certain surgeries...
        double[][] surgORMatch = {
                {1,0,1},
                {0,1,1},
                {1,0,0},
                {0,1,1},
                {0,1,0}
        };*/


        ConnectionManager connectionManager = new ConnectionManager("assignmentproblem");

        List<List<Object>> surgery = new ReadTable().getData("surgery");
        List<List<Object>> patient = new ReadTable().getData("patient");
        List<List<Object>> availability = new ReadTable().getData("availability");
        List<List<Object>> capacity = new ReadTable().getData("capacity");

        connectionManager.close();

        AssignmentModel.buildModelAndSolve(surgery, patient, availability, capacity);

    }


}