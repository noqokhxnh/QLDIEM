package Model;

import connection.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class SinhVienModel {
    private String username;
    private String masv;
    private String hoten;
    private String ngaysinh;
    private String gioitinh;
    private String diachi;
    private String malop;

    public SinhVienModel(String username, String masv, String hoten, String ngaysinh, String gioitinh, String diachi, String malop) {
        this.username = username;
        this.masv = masv;
        this.hoten = hoten;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
        this.diachi = diachi;
        this.malop = malop;
    }

    public SinhVienModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getMalop() {
        return malop;
    }

    public void setMalop(String malop) {
        this.malop = malop;
    }

    public ArrayList<SinhVienModel> ds = new ArrayList<>();

    public SinhVienModel  getData(String username) throws SQLException {
       SinhVienModel sv = null;
        String query = "Select * from tblsinhvien where username =?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1,username);

        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            sv = new SinhVienModel(
            rs.getString("username"),
            rs.getString("masv"),
            rs.getString("hoten"),
            rs.getString("ngaysinh"),
            rs.getString("gioitinh"),
            rs.getString("diachi"),
            rs.getString("malop")
);
        }

return sv;
    }




}
