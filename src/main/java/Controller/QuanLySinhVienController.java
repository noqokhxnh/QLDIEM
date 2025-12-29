package Controller;

import Model.SinhVienModel;
import Model.LopModel;
import View.QuanLySinhVienPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Controller cho quản lý sinh viên (Admin)
 */
public class QuanLySinhVienController implements ActionListener, MouseListener {
    
    private QuanLySinhVienPanel view;
    private SinhVienModel model;
    private LopModel lopModel;
    private boolean isEditing = false;
    private SinhVienModel currentSV = null;
    
    public QuanLySinhVienController(QuanLySinhVienPanel view) {
        this.view = view;
        this.model = new SinhVienModel();
        this.lopModel = new LopModel();
        
        view.addActionListener(this);
        view.addTableMouseListener(this);
        
        loadData();
        loadLop();
    }
    
    private void loadData() {
        ArrayList<SinhVienModel> list = model.getAllSinhVien();
        view.loadTableData(list);
    }
    
    private void loadLop() {
        ArrayList<LopModel> listLop = lopModel.getAllLop();
        ArrayList<String> listLopString = new ArrayList<>();
        for (LopModel lop : listLop) {
            listLopString.add(lop.getMalop() + " - " + lop.getTenlop());
        }
        view.loadLop(listLopString);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch (command) {
            case "Thêm":
                handleAdd();
                break;
            case "Sửa":
                handleEdit();
                break;
            case "Xóa":
                handleDelete();
                break;
            case "Lưu":
                handleSave();
                break;
            case "Hủy":
                handleCancel();
                break;
        }
    }
    
    private void handleAdd() {
        view.clearForm();
        view.setEditingMode(true);
        isEditing = false;
        currentSV = null;
    }
    
    private void handleEdit() {
        int row = view.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng để sửa!");
            return;
        }
        
        String masv = view.getTable().getValueAt(row, 0).toString();
        // TODO: Load SinhVienModel từ database
        view.setEditingMode(true);
        isEditing = true;
    }
    
    private void handleDelete() {
        int row = view.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng để xóa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa sinh viên này?");
        if (confirm == JOptionPane.YES_OPTION) {
            // TODO: Xóa sinh viên
            loadData();
        }
    }
    
    private void handleSave() {
        try {
            SinhVienModel sv = view.getFormData();
            
            // Debug: Print form data
            System.out.println("Debug - Form data:");
            System.out.println("- MASV: " + sv.getMasv());
            System.out.println("- Họ tên: " + sv.getHoten());
            System.out.println("- Mã lớp: " + sv.getMalop());
            System.out.println("- Username: " + sv.getUsername());
            
            // Validate
            if (sv.getMasv().isEmpty() || sv.getHoten().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Mã SV và Họ tên không được để trống!");
                return;
            }
            
            if (sv.getMalop() == null || sv.getMalop().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn lớp cho sinh viên!");
                return;
            }
            
            if (sv.getUsername().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Username không được để trống!");
                return;
            }
            
            // Check if student ID already exists
            if (!isEditing && model.isExistMasv(sv.getMasv())) {
                JOptionPane.showMessageDialog(view, "Mã sinh viên đã tồn tại! Vui lòng chọn mã khác.");
                return;
            }
            
            // Check if username already exists
            if (!isEditing && model.isExistUsername(sv.getUsername())) {
                JOptionPane.showMessageDialog(view, "Username đã tồn tại! Vui lòng chọn username khác.");
                return;
            }
            
            boolean success;
            if (isEditing) {
                success = model.capNhatSinhVien(sv);
            } else {
                success = model.themSinhVien(sv);
            }
            
            if (success) {
                JOptionPane.showMessageDialog(view, isEditing ? "Sửa thành công!" : "Thêm thành công!");
                loadData();
                view.setEditingMode(false);
                view.clearForm();
                isEditing = false;
                currentSV = null;
            } else {
                JOptionPane.showMessageDialog(view, "Thao tác thất bại! Vui lòng kiểm tra lại dữ liệu.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void handleCancel() {
        view.setEditingMode(false);
        view.clearForm();
        isEditing = false;
        currentSV = null;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = view.getTable().getSelectedRow();
        if (row >= 0) {
            // TODO: Fill form với dữ liệu từ bảng
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}
    
    @Override
    public void mouseReleased(MouseEvent e) {}
    
    @Override
    public void mouseEntered(MouseEvent e) {}
    
    @Override
    public void mouseExited(MouseEvent e) {}
    
    /**
     * Refresh lop data when class list changes
     */
    public void refreshLopData() {
        loadLop();
    }
    
    /**
     * Refresh all data (students and classes)
     */
    public void refreshAllData() {
        loadData(); // Reload student table
        loadLop();  // Reload class dropdown
    }
}

