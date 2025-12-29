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

public class MonHocModel {

    private String mamon;
    private String tenmon;
    private int sotinchi;

    public MonHocModel() {
    }

    public MonHocModel(String mamon, String tenmon, int sotinchi) {
        this.mamon = mamon;
        this.tenmon = tenmon;
        this.sotinchi = sotinchi;
    }

    public String getMamon() {
        return mamon;
    }

    public void setMamon(String mamon) {
        this.mamon = mamon;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public int getSotinchi() {
        return sotinchi;
    }

    public void setSotinchi(int sotinchi) {
        this.sotinchi = sotinchi;
    }

    public String validate() {
        if (mamon == null || mamon.trim().isEmpty()) {
            return "Mã môn học không được để trống!";
        }

        if (tenmon == null || tenmon.trim().isEmpty()) {
            return "Tên môn học không được để trống!";
        }

        if (sotinchi <= 0) {
            return "Số tín chỉ phải lớn hơn 0!";
        }

        return null;
    }

    public ArrayList<MonHocModel> getDs() {
        ArrayList<MonHocModel> list = new ArrayList<>();
        String query = "SELECT * FROM tblmonhoc";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                MonHocModel m = new MonHocModel(
                        rs.getString("mamon"),
                        rs.getString("tenmon"),
                        rs.getInt("sotinchi")
                );
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertMonHoc(MonHocModel m) {
        String validationError = m.validate();
        if (validationError != null) {
            System.out.println("Validation error: " + validationError);
            return false;
        }

        String query = "INSERT INTO tblmonhoc (mamon, tenmon, sotinchi) VALUES (?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, m.getMamon());
            ps.setString(2, m.getTenmon());
            ps.setInt(3, m.getSotinchi());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMonHoc(MonHocModel m) {
        String validationError = m.validate();
        if (validationError != null) {
            System.out.println("Validation error: " + validationError);
            return false;
        }

        String query = "UPDATE tblmonhoc SET tenmon=?, sotinchi=? WHERE mamon=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, m.getTenmon());
            ps.setInt(2, m.getSotinchi());
            ps.setString(3, m.getMamon());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMonHoc(String mamon) {
        String query = "DELETE FROM tblmonhoc WHERE mamon=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, mamon);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<MonHocModel> search(String mamonCanTim) {
        ArrayList<MonHocModel> list = new ArrayList<>();
        String query = "SELECT * FROM tblmonhoc WHERE mamon LIKE ? OR tenmon LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, "%" + mamonCanTim + "%");
            ps.setString(2, "%" + mamonCanTim + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MonHocModel m = new MonHocModel(
                        rs.getString("mamon"),
                        rs.getString("tenmon"),
                        rs.getInt("sotinchi")
                );
                list.add(m);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

}

