package Model;
import connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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



       public boolean checklogin(String username, String password) {
        String query = "Select 1 from tbluser where `username` =? and `password` =?";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

        public void DoiMatKhau(String username, String password){
            String query ="Update tbluser set password =? where username =?";
            try (
                    Connection connection = DatabaseConnection.getConnection();
                  PreparedStatement preparedStatement = connection.prepareStatement(query);){
                preparedStatement.setString(1, password);
                preparedStatement.setString(2,username);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Doi mat khau thanh cong");
                } else {
                    System.out.println("ERROR");
                }
            } catch (SQLException e) {
            }
        }


}
