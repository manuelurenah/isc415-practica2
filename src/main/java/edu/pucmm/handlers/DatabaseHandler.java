package edu.pucmm.handlers;

import edu.pucmm.data.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MEUrena on 5/26/16.
 * All rights reserved.
 */
public class DatabaseHandler {
    private String className;
    private String dbURL;
    private Connection connection;

    public DatabaseHandler(String className, String dbURL) {
        this.className = className;
        this.dbURL = dbURL;

        this.connection = startDatabaseConnection();
    }

    private Connection startDatabaseConnection() {
        try {
            Class.forName(className);
            connection = DriverManager.getConnection(dbURL, "admin", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public void createStudentTable() throws SQLException {
        String query = "CREATE TABLE ESTUDIANTE(MATRICULA INT PRIMARY KEY, NOMBRE VARCHAR(255),APELLIDO VARCHAR(255),TELEFONO VARCHAR(10))";
        connection = null;

        try {
            connection = startDatabaseConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public void deleteItemWithId(int studentID) {
        String query = "DELETE FROM Estudiante WHERE Matricula = ?";
        connection = null;

        try {
            connection = startDatabaseConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, String.valueOf(studentID));

            ps.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getAllItemsFromTable() {
        List<Student> studentList = new ArrayList<>();
        String query = "SELECT * FROM Estudiante";
        connection = null;

        try {
            connection = startDatabaseConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Student st = new Student();
                st.setStudentID(Integer.parseInt(rs.getString("Matricula")));
                st.setName(rs.getString("Nombre"));
                st.setLastName(rs.getString("Apellido"));
                st.setPhone(rs.getString("Telefono"));

                studentList.add(st);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }

    public Student getItemWithId(int studentID) {
        Student st = new Student();
        String query = "SELECT * FROM Estudiante WHERE Matricula = ?";
        connection = null;

        try {
            connection = startDatabaseConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, studentID);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                st.setStudentID(Integer.parseInt(rs.getString("Matricula")));
                st.setName(rs.getString("Nombre"));
                st.setLastName(rs.getString("Apellido"));
                st.setPhone(rs.getString("Telefono"));
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return st;
    }

    public void insertItemIntoTable(Student student) {
        String query = "INSERT INTO Estudiante(Matricula, Nombre, Apellido, Telefono) VALUES (?,?,?,?)";
        connection = null;

        try {
            connection = startDatabaseConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, String.valueOf(student.getStudentID()));
            ps.setString(2, student.getName());
            ps.setString(3, student.getLastName());
            ps.setString(4, student.getPhone());

            ps.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateItemInTable(Student st) {
        String query = "UPDATE Estudiante SET Nombre=?, Apellido=?, Telefono=? WHERE Matricula = ?";
        connection = null;

        try {
            connection = startDatabaseConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, st.getName());
            ps.setString(2, st.getLastName());
            ps.setString(3, st.getPhone());
            ps.setString(4, String.valueOf(st.getStudentID()));

            ps.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
