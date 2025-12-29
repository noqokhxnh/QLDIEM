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

public class LopModel {

    private String malop;
    private String tenlop;
    private String makhoa;
    private String magvcn;

    public LopModel() {
    }

    public LopModel(String malop, String tenlop, String makhoa, String magvcn) {
        this.malop = malop;
        this.tenlop = tenlop;
        this.makhoa = makhoa;
        this.magvcn = magvcn;
    }

    public String getMalop() {
        return malop;
    }

    public void setMalop(String malop) {
        this.malop = malop;
    }

    public String getTenlop() {
        return tenlop;
    }

    public void setTenlop(String tenlop) {
        this.tenlop = tenlop;
    }

    public String getMakhoa() {
        return makhoa;
    }

    public void setMakhoa(String makhoa) {
        this.makhoa = makhoa;
    }

    public String getMagvcn() {
        return magvcn;
    }

    public void setMagvcn(String magvcn) {
        this.magvcn = magvcn;
    }

    public String validate() {
        if (malop == null || malop.trim().isEmpty()) {
            return "Mã lớp không được để trống!";
        }

        if (tenlop == null || tenlop.trim().isEmpty()) {
            return "Tên lớp không được để trống!";
        }

        return null;
    }

    public ArrayList<LopModel> getDs() {
        ArrayList<LopModel> list = new ArrayList<>();
        String query = "SELECT * FROM tblclass";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                LopModel l = new LopModel(
                        rs.getString("malop"),
                        rs.getString("tenlop"),
                        rs.getString("makhoa"),
                        rs.getString("magvcn")
                );
                list.add(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertLop(LopModel l) {
        String validationError = l.validate();
        if (validationError != null) {
            System.out.println("Validation error: " + validationError);
            return false;
        }

        String query = "INSERT INTO tblclass (malop, tenlop, makhoa, magvcn) VALUES (?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, l.getMalop());
            ps.setString(2, l.getTenlop());
            ps.setString(3, l.getMakhoa());
            ps.setString(4, l.getMagvcn());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateLop(LopModel l) {
        String validationError = l.validate();
        if (validationError != null) {
            System.out.println("Validation error: " + validationError);
            return false;
        }

        String query = "UPDATE tbllop SET tenlop=?, makhoa=?, magvcn=? WHERE malop=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, l.getTenlop());
            ps.setString(2, l.getMakhoa());
            ps.setString(3, l.getMagvcn());
            ps.setString(4, l.getMalop());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteLop(String malop) {
        String query = "DELETE FROM tblclass WHERE malop=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, malop);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<LopModel> search(String malopCanTim) {
        ArrayList<LopModel> list = new ArrayList<>();
        String query = "SELECT * FROM tblclass WHERE malop LIKE ? OR tenlop LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, "%" + malopCanTim + "%");
            ps.setString(2, "%" + malopCanTim + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LopModel l = new LopModel(
                        rs.getString("malop"),
                        rs.getString("tenlop"),
                        rs.getString("makhoa"),
                        rs.getString("magvcn")
                );
                list.add(l);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

}

