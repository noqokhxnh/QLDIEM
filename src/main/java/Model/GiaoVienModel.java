/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author noqok
 */
import connection.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;

public class GiaoVienModel {

    private String magv;
    private String hoten;
    private String gioitinh;
    private String ngaysinh;
    private String email;
    private String sdt;
    private String makhoa;
    private String username;

    public GiaoVienModel() {
    }

    public GiaoVienModel(String magv, String hoten, String gioitinh, String ngaysinh, String email, String sdt, String makhoa, String username) {
        this.magv = magv;
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.email = email;
        this.sdt = sdt;
        this.makhoa = makhoa;
        this.username = username;
    }

    public String getMagv() {
        return magv;
    }

    public void setMagv(String magv) {
        this.magv = magv;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMakhoa() {
        return makhoa;
    }

    public void setMakhoa(String makhoa) {
        this.makhoa = makhoa;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String validate() {
        if (magv == null || magv.trim().isEmpty()) {
            return "Mã giáo viên không được để trống!";
        }

        if (hoten == null || hoten.trim().isEmpty()) {
            return "Họ tên không được để trống!";
        }

        if (email != null && !email.trim().isEmpty()) {
            if (!email.contains("@") || !email.contains(".")) {
                return "Email không hợp lệ!";
            }
        }

        return null;
    }

    public ArrayList<GiaoVienModel> getDs() {
        ArrayList<GiaoVienModel> list = new ArrayList<>();
        String query = "SELECT * FROM tblgiaovien";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                GiaoVienModel gv = new GiaoVienModel(
                        rs.getString("magv"),
                        rs.getString("hoten"),
                        rs.getString("gioitinh"),
                        rs.getDate("ngaysinh") != null ? rs.getDate("ngaysinh").toString() : null,
                        rs.getString("email"),
                        rs.getString("sdt"),
                        rs.getString("makhoa"),
                        rs.getString("username")
                );
                list.add(gv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertGiaoVien(GiaoVienModel gv) {
        String validationError = gv.validate();
        if (validationError != null) {
            System.out.println("Validation error: " + validationError);
            return false;
        }

        String query = "INSERT INTO tblgiaovien (magv, hoten, gioitinh, ngaysinh, email, sdt, makhoa, username) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, gv.getMagv());
            ps.setString(2, gv.getHoten());
            ps.setString(3, gv.getGioitinh());
            if (gv.getNgaysinh() != null && !gv.getNgaysinh().trim().isEmpty()) {
                ps.setDate(4, java.sql.Date.valueOf(gv.getNgaysinh()));
            } else {
                ps.setDate(4, null);
            }
            ps.setString(5, gv.getEmail());
            ps.setString(6, gv.getSdt());
            ps.setString(7, gv.getMakhoa());
            ps.setString(8, gv.getUsername());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateGiaoVien(GiaoVienModel gv) {
        String validationError = gv.validate();
        if (validationError != null) {
            System.out.println("Validation error: " + validationError);
            return false;
        }

        String query = "UPDATE tblgiaovien SET hoten=?, gioitinh=?, ngaysinh=?, email=?, sdt=?, makhoa=?, username=? WHERE magv=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, gv.getHoten());
            ps.setString(2, gv.getGioitinh());
            if (gv.getNgaysinh() != null && !gv.getNgaysinh().trim().isEmpty()) {
                ps.setDate(3, java.sql.Date.valueOf(gv.getNgaysinh()));
            } else {
                ps.setDate(3, null);
            }
            ps.setString(4, gv.getEmail());
            ps.setString(5, gv.getSdt());
            ps.setString(6, gv.getMakhoa());
            ps.setString(7, gv.getUsername());
            ps.setString(8, gv.getMagv());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteGiaoVien(String magv) {
        String query = "DELETE FROM tblgiaovien WHERE magv=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, magv);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<GiaoVienModel> search(String magvCanTim) {
        ArrayList<GiaoVienModel> list = new ArrayList<>();
        String query = "SELECT * FROM tblgiaovien WHERE magv LIKE ? OR hoten LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, "%" + magvCanTim + "%");
            ps.setString(2, "%" + magvCanTim + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GiaoVienModel gv = new GiaoVienModel(
                        rs.getString("magv"),
                        rs.getString("hoten"),
                        rs.getString("gioitinh"),
                        rs.getDate("ngaysinh") != null ? rs.getDate("ngaysinh").toString() : null,
                        rs.getString("email"),
                        rs.getString("sdt"),
                        rs.getString("makhoa"),
                        rs.getString("username")
                );
                list.add(gv);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

}

