package com.company;
import java.io.*;
import java.sql.*;
import java.util.*;

public class Main {
    public static String DBname = "jdbc:postgresql:fileData";

    static void fileCreation() {
        try {
            File creator = new File("test.csv");
            if (creator.createNewFile()) {
                System.out.println("File created: " + creator.getName());
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    static void formatBlock() {
        String csvFilePath = "test.csv";

        try (Connection connection = DriverManager.getConnection(DBname)) {
            String sql = "SELECT employee.employee_id, employee.first_name, employee.last_name, employee.email, country.country_name, project.project_name, project.project_desc\n" +
                    "FROM employee\n" +
                    "JOIN project\n" +
                    "ON employee.project = project.project_id\n" +
                    "JOIN country\n" +
                    "ON employee.country = country.country_id;";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));

            //header line
            fileWriter.write("employee_id, first_name, last_name, email, country_name, project_name, project_desc");

            while (rs.next()) {
                Integer id = rs.getInt("employee_id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String email = rs.getString("email");
                String country_name = rs.getString("country_name");
                String project_name = rs.getString("project_name");
                String project_desc = rs.getString("project_desc");

                String formatLine = String.format("%d,%s,%s,%s,%s,%s,%s", id, first_name, last_name, email, country_name, project_name, project_desc);

                fileWriter.newLine();
                fileWriter.write(formatLine);
            }

            st.close();
            fileWriter.close();

        } catch (SQLException e) {
            System.out.println("Database Error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }
    }

    static void addMethod() {
        Scanner addIn = new Scanner(System.in);

        //Inputs
        System.out.println("First name:");
        String firstName = addIn.nextLine();

        System.out.println("Last name:");
        String lastName = addIn.nextLine();

        System.out.println("Email:");
        String email = addIn.nextLine();

        System.out.println("Country (ID):");
        Integer country = Integer.valueOf(addIn.nextLine());

        System.out.println("Project (ID):");
        Integer project = Integer.valueOf(addIn.nextLine());

        //Insertion
        try (Connection connection = DriverManager.getConnection(DBname)) {
            String stm = "INSERT INTO employee(first_name, last_name, email, country, project)" +
                    " VALUES(?, ?, ?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(stm);

            //Parameters
            st.setString(1, firstName);
            st.setString(2, lastName);
            st.setString(3, email);
            st.setInt(4, country);
            st.setInt(5, project);

            st.executeUpdate();
            System.out.println("Added!");
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

    static void deleteMethod() {
        String delSql = "DELETE FROM employee WHERE employee.first_name=?";
        Scanner delIn = new Scanner(System.in);
        System.out.println("Enter the name you would like to delete:");
        String selected = delIn.nextLine();

        try (Connection connection = DriverManager.getConnection(DBname)) {
            PreparedStatement stmt = connection.prepareStatement(delSql);
            stmt.setString(1, selected);
            stmt.executeUpdate();
            System.out.println("Employee Entry Deleted.");
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Could not delete employee.");
            e.printStackTrace();
        }

    };

    static void exportMethod() {
        fileCreation();
        formatBlock();
    };

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Employee Management System.");
        while (true) {
            System.out.println("[Add], [Delete], [Export], or [Quit]?");
            String userChoice = input.nextLine();

            if (userChoice.equals("Add")) {
                addMethod();
            } else if (userChoice.equals("Delete")) {
                deleteMethod();
            } else if (userChoice.equals("Export")) {
                exportMethod();
            } else if (userChoice.equals("Quit")) {
                break;
            }
        }
    }
}
