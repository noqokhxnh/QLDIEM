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

public class KhoaModel {

    private String makhoa;
    private String tenkhoa;

    public KhoaModel() {
    }

    public KhoaModel(String makhoa, String tenkhoa) {
        this.makhoa = makhoa;
        this.tenkhoa = tenkhoa;
    }

    public String getMakhoa() {
        return makhoa;
    }

    public void setMakhoa(String makhoa) {
        this.makhoa = makhoa;
    }

    public String getTenkhoa() {
        return tenkhoa;
    }

    public void setTenkhoa(String tenkhoa) {
        this.tenkhoa = tenkhoa;
    }

    public String validate() {
        if (makhoa == null || makhoa.trim().isEmpty()) {
            return "Mã khoa không được để trống!";
        }

        if (tenkhoa == null || tenkhoa.trim().isEmpty()) {
            return "Tên khoa không được để trống!";
        }

        return null;
    }

    public ArrayList<KhoaModel> getDs() {
        ArrayList<KhoaModel> list = new ArrayList<>();
        String query = "SELECT * FROM tblkhoa";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                KhoaModel k = new KhoaModel(
                        rs.getString("makhoa"),
                        rs.getString("tenkhoa")
                );
                list.add(k);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertKhoa(KhoaModel k) {
        String validationError = k.validate();
        if (validationError != null) {
            System.out.println("Validation error: " + validationError);
            return false;
        }

        String query = "INSERT INTO tblkhoa (makhoa, tenkhoa) VALUES (?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, k.getMakhoa());
            ps.setString(2, k.getTenkhoa());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateKhoa(KhoaModel k) {
        String validationError = k.validate();
        if (validationError != null) {
            System.out.println("Validation error: " + validationError);
            return false;
        }

        String query = "UPDATE tblkhoa SET tenkhoa=? WHERE makhoa=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, k.getTenkhoa());
            ps.setString(2, k.getMakhoa());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteKhoa(String makhoa) {
        String query = "DELETE FROM tblkhoa WHERE makhoa=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, makhoa);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<KhoaModel> search(String makhoaCanTim) {
        ArrayList<KhoaModel> list = new ArrayList<>();
        String query = "SELECT * FROM tblkhoa WHERE makhoa LIKE ? OR tenkhoa LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, "%" + makhoaCanTim + "%");
            ps.setString(2, "%" + makhoaCanTim + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhoaModel k = new KhoaModel(
                        rs.getString("makhoa"),
                        rs.getString("tenkhoa")
                );
                list.add(k);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

}

