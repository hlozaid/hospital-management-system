package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }


    public void addPatient(){
        System.out.print("Enter Patient Name: ");
        String name = scanner.next();
        System.out.print("Enter Patient Age: ");
        int age = scanner.nextInt();
        System.out.print("Enter Patient Gender: ");
        String gender = scanner.next();
        try{
            String query = "INSERT INTO patients(name, age, gender) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3, gender);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Patient Added Successfully!! ");
            }else{
                System.out.println("Failed to add Patient!! ");
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void viewPatient(){
        String query = "select * from patients";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Patient Id | Name               | Age      | Gender     |");
            System.out.println("+------------+--------------------+----------+------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n", id, name, age, gender);
                System.out.println("+------------+--------------------+----------+------------+");
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void deletePatient(){
        System.out.print("Enter Patient ID to delete: ");
        int id = scanner.nextInt();

        if(!getPatientById(id)){
            System.out.println("Patient ID " + id + " does not exist.");
            return;
        }

        String query = "Delete FROM patients WHERE id =?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int affectedRow = preparedStatement.executeUpdate();

            if(affectedRow>0){
                System.out.println("Patient deleted successfully! ");
            }else{
                System.out.println("Failed to delete patient");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public boolean getPatientById(int id){
        String query = "select * from patients where id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else {
                return false;
            }

        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

}
