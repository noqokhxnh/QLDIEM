package Model;

import connection.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;

/**
 * Model để quản lý khoa
 */
public class KhoaModel {
    
    private String makhoa;
    private String tenkhoa;
    
    public KhoaModel() {
    }
    
    public KhoaModel(String makhoa, String tenkhoa) {
        this.makhoa = makhoa;
        this.tenkhoa = tenkhoa;
    }
    
    // Getters and Setters
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
    
    /**
     * Lấy danh sách tất cả khoa
     */
    public ArrayList<KhoaModel> getAllKhoa() {
        ArrayList<KhoaModel> list = new ArrayList<>();
        String query = "SELECT * FROM tblkhoa ORDER BY tenkhoa";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                KhoaModel khoa = new KhoaModel(
                    rs.getString("makhoa"),
                    rs.getString("tenkhoa")
                );
                list.add(khoa);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy danh sách khoa: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Lỗi không xác định khi lấy danh sách khoa: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * Lấy tên khoa theo mã khoa
     */
    public String getTenkhoaByMakhoa(String makhoa) {
        String query = "SELECT tenkhoa FROM tblkhoa WHERE makhoa = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, makhoa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("tenkhoa");
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy tên khoa: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Lỗi không xác định khi lấy tên khoa: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}