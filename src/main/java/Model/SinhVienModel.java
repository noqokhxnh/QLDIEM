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

    public SinhVienModel getData(String username) throws SQLException {
        SinhVienModel sv = null;
        String query = "Select * from tblsinhvien where username =?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            sv = new SinhVienModel(
                    rs.getString("username"),
                    rs.getString("masv"),
                    rs.getString("hoten"),
                    rs.getDate("ngaysinh") != null ? rs.getDate("ngaysinh").toString() : null,
                    rs.getString("gioitinh"),
                    rs.getString("diachi"),
                    rs.getString("malop")
            );
        }

        return sv;
    }

    public String validate() {
        if (masv == null || masv.trim().isEmpty()) {
            return "Mã sinh viên không được để trống!";
        }

        if (hoten == null || hoten.trim().isEmpty()) {
            return "Họ tên không được để trống!";
        }

        return null;
    }

    public ArrayList<SinhVienModel> getDs() {
        ArrayList<SinhVienModel> list = new ArrayList<>();
        String query = "SELECT * FROM tblsinhvien";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                SinhVienModel sv = new SinhVienModel(
                        rs.getString("username"),
                        rs.getString("masv"),
                        rs.getString("hoten"),
                        rs.getDate("ngaysinh") != null ? rs.getDate("ngaysinh").toString() : null,
                        rs.getString("gioitinh"),
                        rs.getString("diachi"),
                        rs.getString("malop")
                );
                list.add(sv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertSinhVien(SinhVienModel sv) {
        String validationError = sv.validate();
        if (validationError != null) {
            System.out.println("Validation error: " + validationError);
            return false;
        }

        String query = "INSERT INTO tblsinhvien (masv, hoten, ngaysinh, gioitinh, diachi, malop, username) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, sv.getMasv());
            ps.setString(2, sv.getHoten());
            if (sv.getNgaysinh() != null && !sv.getNgaysinh().trim().isEmpty()) {
                ps.setDate(3, java.sql.Date.valueOf(sv.getNgaysinh()));
            } else {
                ps.setDate(3, null);
            }
            ps.setString(4, sv.getGioitinh());
            ps.setString(5, sv.getDiachi());
            ps.setString(6, sv.getMalop());
            ps.setString(7, sv.getUsername());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSinhVien(SinhVienModel sv) {
        String validationError = sv.validate();
        if (validationError != null) {
            System.out.println("Validation error: " + validationError);
            return false;
        }

        String query = "UPDATE tblsinhvien SET hoten=?, ngaysinh=?, gioitinh=?, diachi=?, malop=?, username=? WHERE masv=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, sv.getHoten());
            if (sv.getNgaysinh() != null && !sv.getNgaysinh().trim().isEmpty()) {
                ps.setDate(2, java.sql.Date.valueOf(sv.getNgaysinh()));
            } else {
                ps.setDate(2, null);
            }
            ps.setString(3, sv.getGioitinh());
            ps.setString(4, sv.getDiachi());
            ps.setString(5, sv.getMalop());
            ps.setString(6, sv.getUsername());
            ps.setString(7, sv.getMasv());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSinhVien(String masv) {
        String query = "DELETE FROM tblsinhvien WHERE masv=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, masv);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<SinhVienModel> search(String masvCanTim) {
        ArrayList<SinhVienModel> list = new ArrayList<>();
        String query = "SELECT * FROM tblsinhvien WHERE masv LIKE ? OR hoten LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, "%" + masvCanTim + "%");
            ps.setString(2, "%" + masvCanTim + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SinhVienModel sv = new SinhVienModel(
                        rs.getString("username"),
                        rs.getString("masv"),
                        rs.getString("hoten"),
                        rs.getDate("ngaysinh") != null ? rs.getDate("ngaysinh").toString() : null,
                        rs.getString("gioitinh"),
                        rs.getString("diachi"),
                        rs.getString("malop")
                );
                list.add(sv);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

}
