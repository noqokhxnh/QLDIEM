package Controller;

import Model.LopModel;
import Model.KhoaModel;
import Model.GiaoVienModel;
import View.QuanLyLopPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Controller cho quản lý lớp học
 */
public class QuanLyLopController implements ActionListener, MouseListener {
    
    private QuanLyLopPanel view;
    private LopModel model;
    private KhoaModel khoaModel;
    private GiaoVienModel gvModel;
    private ModernMainController mainController; // Add main controller reference
    private boolean isEditing = false;
    private LopModel currentLop = null;
    
    public QuanLyLopController(QuanLyLopPanel view) {
        this(view, null);
    }
    
    public QuanLyLopController(QuanLyLopPanel view, ModernMainController mainController) {
        this.view = view;
        this.model = new LopModel();
        this.khoaModel = new KhoaModel();
        this.gvModel = new GiaoVienModel();
        this.mainController = mainController;
        
        view.addActionListener(this);
        view.addTableMouseListener(this);
        
        loadData();
        loadKhoa();
        loadGiaoVien();
    }
    
    private void loadData() {
        ArrayList<LopModel> list = model.getAllLop();
        view.loadTableData(list);
    }
    
    private void loadKhoa() {
        ArrayList<KhoaModel> listKhoa = khoaModel.getAllKhoa();
        ArrayList<String> khoaNames = new ArrayList<>();
        for (KhoaModel khoa : listKhoa) {
            khoaNames.add(khoa.getMakhoa() + " - " + khoa.getTenkhoa());
        }
        view.loadKhoa(khoaNames);
    }
    
    private void loadGiaoVien() {
        ArrayList<GiaoVienModel> listGV = gvModel.getAllGiaoVien();
        ArrayList<String> gvNames = new ArrayList<>();
        for (GiaoVienModel gv : listGV) {
            gvNames.add(gv.getMagv() + " - " + gv.getHoten());
        }
        view.loadGiaoVien(gvNames);
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
        currentLop = null;
    }
    
    private void handleEdit() {
        int row = view.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng để sửa!");
            return;
        }
        
        String malop = view.getTable().getValueAt(row, 0).toString();
        currentLop = model.getLopByMalop(malop);
        if (currentLop != null) {
            view.fillForm(currentLop);
            view.setEditingMode(true);
            isEditing = true;
        }
    }
    
    private void handleDelete() {
        int row = view.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng để xóa!");
            return;
        }
        
        String malop = view.getTable().getValueAt(row, 0).toString();
        String tenlop = view.getTable().getValueAt(row, 1).toString();
        
        // Kiểm tra lớp có sinh viên không
        if (model.hasStudents(malop)) {
            JOptionPane.showMessageDialog(view, 
                "Không thể xóa lớp \"" + tenlop + "\" vì lớp này đang có sinh viên!\n" +
                "Vui lòng chuyển sinh viên sang lớp khác trước khi xóa.",
                "Không thể xóa",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view, 
            "Bạn có chắc muốn xóa lớp \"" + tenlop + "\"?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            if (model.deleteLop(malop)) {
                JOptionPane.showMessageDialog(view, "Xóa lớp thành công!");
                loadData();
                
                // Refresh class data in other panels
                if (mainController != null) {
                    mainController.refreshClassData();
                }
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi khi xóa lớp. Vui lòng thử lại!");
            }
        }
    }
    
    private void handleSave() {
        try {
            // Validate form data
            LopModel lopData = view.getFormData();
            
            if (lopData.getMalop().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập mã lớp!");
                return;
            }
            
            if (lopData.getTenlop().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập tên lớp!");
                return;
            }
            
            if (lopData.getMakhoa() == null || lopData.getMakhoa().equals("-- Chọn khoa --")) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn khoa!");
                return;
            }
            
            // Extract makhoa from combo selection if needed
            String khoaSelection = lopData.getMakhoa();
            if (khoaSelection.contains(" - ")) {
                lopData.setMakhoa(khoaSelection.split(" - ")[0]);
            }
            
            boolean success = false;
            String message = "";
            
            if (isEditing && currentLop != null) {
                // Update existing lop
                success = lopData.updateLop();
                message = success ? "Cập nhật lớp thành công!" : "Lỗi khi cập nhật lớp!";
            } else {
                // Check if malop already exists
                if (model.isExistMalop(lopData.getMalop())) {
                    JOptionPane.showMessageDialog(view, "Mã lớp đã tồn tại! Vui lòng chọn mã khác.");
                    return;
                }
                
                // Add new lop
                success = lopData.addLop();
                message = success ? "Thêm lớp thành công!" : "Lỗi khi thêm lớp!";
            }
            
            JOptionPane.showMessageDialog(view, message);
            
            if (success) {
                view.setEditingMode(false);
                view.clearForm();
                loadData();
                isEditing = false;
                currentLop = null;
                
                // Refresh class data in other panels
                if (mainController != null) {
                    mainController.refreshClassData();
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi không xác định: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void handleCancel() {
        view.setEditingMode(false);
        view.clearForm();
        isEditing = false;
        currentLop = null;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = view.getTable().getSelectedRow();
        if (row >= 0) {
            String malop = view.getTable().getValueAt(row, 0).toString();
            LopModel lop = model.getLopByMalop(malop);
            if (lop != null) {
                view.fillForm(lop);
            }
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
}

