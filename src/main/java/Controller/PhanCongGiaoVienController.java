package Controller;

import Model.LopModel;
import Model.GiaoVienModel;
import connection.DatabaseConnection;
import View.PhanCongGiaoVienPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

/**
 * Controller cho phân công giáo viên quản lý lớp
 */
public class PhanCongGiaoVienController implements ActionListener {
    
    private PhanCongGiaoVienPanel view;
    private LopModel lopModel;
    private GiaoVienModel gvModel;
    
    public PhanCongGiaoVienController(PhanCongGiaoVienPanel view) {
        this.view = view;
        this.lopModel = new LopModel();
        this.gvModel = new GiaoVienModel();
        
        view.addActionListener(this);
        
        loadData();
        loadGiaoVien();
        loadLop();
    }
    
    private void loadData() {
        // Load danh sách phân công từ database với JOIN để lấy tên môn và tên khoa
        ArrayList<Object[]> data = new ArrayList<>();
        String query = "SELECT pc.magv, gv.hoten, mh.tenmon, pc.malop, k.tenkhoa " +
                       "FROM tblphancong pc " +
                       "JOIN tblgiaovien gv ON pc.magv = gv.magv " +
                       "LEFT JOIN tblmonhoc mh ON gv.mamon = mh.mamon " +
                       "LEFT JOIN tblclass c ON pc.malop = c.malop " +
                       "LEFT JOIN tblkhoa k ON c.makhoa = k.makhoa " +
                       "ORDER BY pc.magv, pc.malop";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("magv"),
                    rs.getString("hoten"),
                    rs.getString("tenmon") != null ? rs.getString("tenmon") : "",
                    rs.getString("malop"),
                    rs.getString("tenkhoa") != null ? rs.getString("tenkhoa") : ""
                };
                data.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi load phân công: " + e.getMessage());
            e.printStackTrace();
        }
        view.loadTableData(data);
    }
    
    private void loadGiaoVien() {
        ArrayList<GiaoVienModel> listGV = gvModel.getAllGiaoVien();
        ArrayList<String> listGVString = new ArrayList<>();
        for (GiaoVienModel gv : listGV) {
            listGVString.add(gv.getMagv() + " - " + gv.getHoten());
        }
        view.loadGiaoVien(listGVString);
    }
    
    private void loadLop() {
        ArrayList<LopModel> listLop = lopModel.getAllLop();
        view.loadLop(listLop);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (command.equals("Thêm phân công")) {
            handleThem();
        } else if (command.equals("Xóa phân công")) {
            handleXoa();
        }
    }
    
    private void handleThem() {
        String magv = view.getSelectedGiaoVien();
        String malop = view.getSelectedLop();
        
        if (magv == null || malop == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn giáo viên và lớp!");
            return;
        }
        
        if (lopModel.themPhanCong(magv, malop)) {
            JOptionPane.showMessageDialog(view, "Thêm phân công thành công!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(view, "Thêm phân công thất bại! Có thể giáo viên đã được phân công lớp này rồi.");
        }
    }
    
    private void handleXoa() {
        int row = view.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng để xóa!");
            return;
        }
        
        String magv = view.getTable().getValueAt(row, 0).toString();
        String malop = view.getTable().getValueAt(row, 3).toString(); // Cột 3 là Mã lớp
        
        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa phân công này?");
        if (confirm == JOptionPane.YES_OPTION) {
            if (lopModel.xoaPhanCong(magv, malop)) {
                JOptionPane.showMessageDialog(view, "Xóa phân công thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa phân công thất bại!");
            }
        }
    }
    
    /**
     * Refresh lop data when class list changes
     */
    public void refreshLopData() {
        loadLop();
    }
}

