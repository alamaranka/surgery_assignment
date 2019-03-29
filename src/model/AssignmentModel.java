package model;
import ilog.concert.*;
import ilog.cplex.*;

import java.util.List;

public class AssignmentModel {

    public static void buildModelAndSolve(List<List<Object>> surgery, List<List<Object>> patient,
                                          List<List<Object>> availability, List<List<Object>> capacity){
        try {
            //create model
            IloCplex cplex = new IloCplex();
            // number of patients, days, and operation rooms...
            int numberOfSurgeries = surgery.size() - 1;
            int numberOfPatients = patient.size() - 1;
            int numberOfDays = capacity.size() - 1;
            int numberOfOperatingRooms = availability.get(0).size() - 1;
            //define variables
            IloNumVar[][][] x = new IloNumVar[numberOfPatients][numberOfDays][];
            for (int i=0; i<numberOfPatients; i++) {
                for (int j = 0; j < numberOfDays; j++) {
                    x[i][j]=cplex.boolVarArray(numberOfOperatingRooms);
                }
            }
            // this stands for absolute value...
            IloNumVar[][] w = new IloNumVar[numberOfDays][numberOfOperatingRooms];
            for (int i=0; i<numberOfDays; i++) {
                for (int j = 0; j < numberOfOperatingRooms; j++) {
                    w[i][j]=cplex.numVar(0, Double.MAX_VALUE);
                }
            }
            //objective function -> minimize the total deviation from capacity
            IloLinearNumExpr objective = cplex.linearNumExpr();
            for (int i=0; i<numberOfDays; i++) {
                for (int j = 0; j < numberOfOperatingRooms; j++) {
                    objective.addTerm(1.0, w[i][j]);
                }
            }
            cplex.addMinimize(objective);
            //constraints//////
            //cons set 1 -> force each surgery assigned to a day and an operation room
            for(int i=0; i<numberOfPatients; i++){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for(int j=0; j<numberOfDays; j++){
                    for (int k = 0; k < numberOfOperatingRooms; k++) {
                        expr.addTerm(1.0, x[i][j][k]);
                    }
                }
                cplex.addEq(expr, 1.0);
            }
            //cons set 2 ->  assign surgeries only to relevant operating rooms...
            for (int k = 0; k < numberOfSurgeries; k++) {
                for (int p = 0; p < numberOfOperatingRooms; p++) {
                    if((double)availability.get(k+1).get(p+1) == 0.0){
                        for (int i = 0; i < numberOfPatients; i++) {
                            if(patient.get(i+1).get(1).equals(surgery.get(k+1).get(0))){
                                IloLinearNumExpr expr = cplex.linearNumExpr();
                                for (int j = 0; j < numberOfDays; j++) {
                                    expr.addTerm(1.0, x[i][j][p]);
                                }
                                cplex.addEq(0.0, expr);
                            }
                        }
                    }
                }
            }
            //cons set 3 -> transformation of absolute value in the objective function
            for(int i=0; i<numberOfDays; i++){
                for(int j=0; j<numberOfOperatingRooms; j++){
                    IloLinearNumExpr expr1 = cplex.linearNumExpr();
                    IloLinearNumExpr expr2 = cplex.linearNumExpr();
                    for (int k = 0; k < numberOfPatients; k++) {
                        expr1.addTerm((double)patient.get(k+1).get(2), x[k][i][j]);
                        expr2.addTerm(-(double)patient.get(k+1).get(2), x[k][i][j]);
                    }
                    expr1.addTerm(-1.0, w[i][j]);
                    expr2.addTerm(-1.0, w[i][j]);
                    cplex.addLe(expr1, (double)capacity.get(i+1).get(j+1));
                    cplex.addLe(expr2, -(double)capacity.get(i+1).get(j+1));
                }
            }

            //solve
            cplex.solve();
            //print solution
            System.out.println("Objective value= "+ cplex.getObjValue());
            System.out.println("Optimal assignment:");
            for (int i=0; i<numberOfPatients; i++) {
                for (int j=0; j<numberOfDays; j++) {
                    for (int k = 0; k < numberOfOperatingRooms; k++) {
                        System.out.print(cplex.getValue(x[i][j][k])+"  ");
                    }
                }
                System.out.println();
            }
            System.out.println("    Assignment:     Capacity:       Deviation:");
            for (int i = 0; i < numberOfDays; i++) {
                for (int j = 0; j < numberOfOperatingRooms; j++) {
                    double sum = 0;
                    for (int k = 0; k < numberOfPatients; k++) {
                        sum += cplex.getValue(x[k][i][j]) * (double)patient.get(k+1).get(2);
                    }
                    System.out.printf("%5s", sum);
                }
                System.out.print("|");
                for (int j = 0; j < numberOfOperatingRooms; j++) {
                    System.out.printf("%5s", capacity.get(i+1).get(j+1));
                }
                System.out.print("|");
                for (int j = 0; j < numberOfOperatingRooms; j++) {
                    System.out.printf("%5s", cplex.getValue(w[i][j]));
                }
                System.out.println();
            }
        }
        catch(IloException e){
            e.printStackTrace();
        }
    }

}
