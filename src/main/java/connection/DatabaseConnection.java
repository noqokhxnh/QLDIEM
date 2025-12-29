/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;

/**
 *
 * @author noqok
 */

import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection {

    public static Connection getConnection() {
        Connection conn = null;
        try {
    String url = "jdbc:mysql://localhost:3306/quanlydiem";
            String user = "root";
            String password = "";
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to connection.");
        }
        return conn;
    }
}