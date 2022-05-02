package com.example.bomapetite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    static final String DB_URL = "jdbc:mysql://database-bomapetite.cqqgutt3njaq.us-east-1.rds.amazonaws.com/AppBomApetite";
    static final String USER = "admin";
    static final String PASS = "08049800";
    static final String QUERY = "SELECT * FROM user";
    private static Connection cn;

    public void main() {
        try {
            // Driver para conectar con MySQL
            Class.forName("com.mysql.jdbc.Driver");
            // Conexion con la base de datos
            cn = DriverManager.getConnection(DB_URL, "admin", "08049800");

            Statement stmt = cn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
            {
                // Extract data from result set
                while (rs.next()) {
                    // Retrieve by column name
                    System.out.print("ID: " + rs.getString(2));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
