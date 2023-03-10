package org.example;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Start{


    public static void main(String[] args){

        Connection connection = null;

        Set<Student> italianStudents = new HashSet<>();//
        Set<Student> germanStudents  = new HashSet<>();

        try{

            String url      = "jdbc:mysql://localhost:3306/newdb";
            String user     = "root";
            String password = "Milanista1997";

            connection = DriverManager.getConnection(url,user,password);
            Statement statement = connection.createStatement();

            String query = "CREATE VIEW `italian_students` AS SELECT `students`.`first_name` ,"+
                    "`students`.`last_name` FROM `students` WHERE (`students`.`country` = 'Italy')";
            String query1 = "CREATE VIEW `german_students` AS SELECT `students`.`first_name` ,"+
                    "`students`.`last_name` FROM `students` WHERE (`students`.`country` = 'Germany')";
            statement.executeUpdate(query);
            statement.executeUpdate(query1);
            System.out.println("queri eseguita!");

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM italian_students");

            while(resultSet.next()){
                italianStudents.add(new Student(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")));
            }

            resultSet = statement.executeQuery("SELECT * FROM german_students");
            while(resultSet.next()){
                germanStudents.add(new Student(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")));
            }

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        } finally {
            try{
                if(connection != null){
                    connection.close();
                }
            }catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}