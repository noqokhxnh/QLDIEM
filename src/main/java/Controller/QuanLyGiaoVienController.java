package Controller;

import Model.GiaoVienModel;
import View.QuanLyGiaoVienPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Controller cho quản lý giáo viên
 */
public class QuanLyGiaoVienController implements ActionListener, MouseListener {
    
    private QuanLyGiaoVienPanel view;
    private GiaoVienModel model;
    private boolean isEditing = false;
    private GiaoVienModel currentGV = null;
    
    public QuanLyGiaoVienController(QuanLyGiaoVienPanel view) {
        this.view = view;
        this.model = new GiaoVienModel();
        
        view.addActionListener(this);
        view.addTableMouseListener(this);
        
        loadData();
        loadKhoa();
        loadMonHoc();
    }
    
    private void loadData() {
        ArrayList<GiaoVienModel> list = model.getAllGiaoVien();
        view.loadTableData(list);
    }
    
    private void loadKhoa() {
        // TODO: Load từ Model.KhoaModel
        ArrayList<String> listKhoa = new ArrayList<>();
        listKhoa.add("CNTT");
        listKhoa.add("KT");
        listKhoa.add("NN");
        view.loadKhoa(listKhoa);
    }
    
    private void loadMonHoc() {
        // TODO: Load từ Model.MonHocModel
        ArrayList<String> listMonHoc = new ArrayList<>();
        listMonHoc.add("MH01 - Toán");
        listMonHoc.add("MH02 - Văn");
        listMonHoc.add("MH03 - Anh");
        listMonHoc.add("MH04 - Lý");
        listMonHoc.add("MH05 - Hóa");
        view.loadMonHoc(listMonHoc);
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
        currentGV = null;
    }
    
    private void handleEdit() {
        int row = view.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng để sửa!");
            return;
        }
        
        String magv = view.getTable().getValueAt(row, 0).toString();
        currentGV = model.getGiaoVienByMagv(magv);
        if (currentGV != null) {
            view.fillForm(currentGV);
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
        
        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa giáo viên này?");
        if (confirm == JOptionPane.YES_OPTION) {
            String magv = view.getTable().getValueAt(row, 0).toString();
            if (model.xoaGiaoVien(magv)) {
                JOptionPane.showMessageDialog(view, "Xóa thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại!");
            }
        }
    }
    
    private void handleSave() {
        try {
            GiaoVienModel gv = view.getFormData();
            
            // Validate
            if (gv.getMagv().isEmpty() || gv.getHoten().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Mã GV và Họ tên không được để trống!");
                return;
            }
            
            boolean success;
            if (isEditing) {
                success = model.capNhatGiaoVien(gv);
            } else {
                success = model.themGiaoVien(gv);
            }
            
            if (success) {
                JOptionPane.showMessageDialog(view, isEditing ? "Sửa thành công!" : "Thêm thành công!");
                loadData();
                view.setEditingMode(false);
                view.clearForm();
                isEditing = false;
                currentGV = null;
            } else {
                JOptionPane.showMessageDialog(view, "Thao tác thất bại!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage());
        }
    }
    
    private void handleCancel() {
        view.setEditingMode(false);
        view.clearForm();
        isEditing = false;
        currentGV = null;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = view.getTable().getSelectedRow();
        if (row >= 0) {
            String magv = view.getTable().getValueAt(row, 0).toString();
            currentGV = model.getGiaoVienByMagv(magv);
            if (currentGV != null) {
                view.fillForm(currentGV);
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

