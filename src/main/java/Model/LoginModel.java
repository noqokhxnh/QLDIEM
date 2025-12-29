package Model;
import connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class LoginModel {

    private String username;
    private String password;
    private int type;

    public LoginModel(String username, String password, int type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }


    public LoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginModel() {
        
    }
    


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



       public Integer checklogin(String username, String password) {
        String query = "SELECT type FROM tbluser WHERE username=? AND password=?";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("type"); // Trả về vai trò (0, 1, 2)
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Đăng nhập thất bại
    }




}
