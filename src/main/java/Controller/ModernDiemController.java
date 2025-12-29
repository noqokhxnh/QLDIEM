package Controller;

import Model.DiemModel;
import Model.LopModel;
import View.ModernDiemPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Modern DiemController with enhanced functionality and smooth operations
 */
public class ModernDiemController implements ActionListener, MouseListener {
    
    private ModernDiemPanel view;
    private DiemModel model;
    private LopModel lopModel;
    private String username;
    private int userType;
    private boolean isEditing = false;
    private DiemModel currentDiem = null;
    private String selectedLop = null; // Lớp đang được chọn (cho giáo viên)
    private String teacherSubject = null; // Môn học của giáo viên
    
    public ModernDiemController(ModernDiemPanel view, String username, int userType) {
        this.view = view;
        this.username = username;
        this.userType = userType;
        this.model = new DiemModel();
        this.lopModel = new LopModel();
        
        setupEventHandlers();
        setupTeacherSubjectAndClass();
        loadInitialData();
        setupAutoCalculation();
    }
    
    private void setupTeacherSubjectAndClass() {
        if (userType == 1) { // Giáo viên
            // Lấy môn học của giáo viên
            Model.GiaoVienModel gvModel = new Model.GiaoVienModel();
            teacherSubject = gvModel.getMonHocByUsername(username);
            
            if (teacherSubject != null) {
                // Hiển thị tên môn học trên UI
                String tenMon = gvModel.getTenMonByMamon(teacherSubject);
                view.setTeacherSubject(teacherSubject, tenMon);
                
                // Load danh sách lớp giáo viên quản lý
                String magv = lopModel.getMagvByUsername(username);
                if (magv != null) {
                    ArrayList<LopModel> listLop = lopModel.getLopByGiaoVien(magv);
                    view.loadLop(listLop);
                    
                    // Thêm listener cho dropdown lớp
                    view.setLopChangeListener(e -> {
                        selectedLop = view.getSelectedLop();
                        loadDiemForTeacher();
                    });
                    
                    // Khóa trường môn học cho giáo viên
                    view.lockSubjectField(teacherSubject, tenMon);
                }
            } else {
                // Giáo viên chưa được phân môn
                view.showMessage("Giáo viên chưa được phân công môn học. Vui lòng liên hệ Admin.");
            }
        }
    }
    
    private void setupEventHandlers() {
        view.addActionListener(this);
        view.addTableMouseListener(this);
        
        // Setup search functionality - search sẽ được thực hiện khi nhấn nút Tìm
        // Real-time search có thể được thêm sau nếu cần
    }
    
    private void loadInitialData() {
        try {
            ArrayList<DiemModel> data;
            if (userType == 2) { // Sinh viên
                data = model.getDiemByUsername(username);
                view.loadTableData(data);
                showStatusMessage("Đã tải " + data.size() + " bản ghi điểm", MessageType.SUCCESS);
            } else if (userType == 1) { // Giáo viên - load điểm môn của mình
                if (teacherSubject != null) {
                    loadDiemForTeacher();
                    showStatusMessage("Đã tải điểm môn " + teacherSubject, MessageType.SUCCESS);
                } else {
                    view.loadTableData(new ArrayList<>());
                    showStatusMessage("Giáo viên chưa được phân công môn học", MessageType.WARNING);
                }
            } else { // Admin
                data = model.getAllDiem();
                view.loadTableData(data);
                showStatusMessage("Đã tải " + data.size() + " bản ghi điểm", MessageType.SUCCESS);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            showStatusMessage("Lỗi khi tải dữ liệu: " + e.getMessage(), MessageType.ERROR);
        }
    }
    
    private void loadDiemForTeacher() {
        if (teacherSubject == null) return;
        
        ArrayList<DiemModel> list;
        if (selectedLop != null && !selectedLop.isEmpty()) {
            // Load điểm của lớp được chọn, chỉ môn của giáo viên
            list = model.getDiemByLopAndMon(selectedLop, teacherSubject);
        } else {
            // Load tất cả điểm của môn giáo viên dạy
            list = model.getDiemByMon(teacherSubject);
        }
        view.loadTableData(list);
    }
    
    private void setupAutoCalculation() {
        // Add document listeners for automatic total score calculation
        // This would require access to the text fields - simplified for now
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        try {
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
                case "Tìm":
                    performSearch();
                    break;
                case "📤 Xuất Excel":
                    handleExport();
                    break;
                case "📥 Nhập Excel":
                    handleImport();
                    break;
                case "🔄 Làm mới":
                    handleRefresh();
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showStatusMessage("Lỗi: " + ex.getMessage(), MessageType.ERROR);
        }
    }
    
    private void handleAdd() {
        if (userType == 2) {
            showStatusMessage("Sinh viên không có quyền thêm điểm", MessageType.WARNING);
            return;
        }
        
        // Kiểm tra quyền môn học cho giáo viên
        if (userType == 1 && teacherSubject == null) {
            showStatusMessage("Giáo viên chưa được phân công môn học", MessageType.WARNING);
            return;
        }
        
        // Giáo viên phải chọn lớp trước
        if (userType == 1 && selectedLop == null) {
            showStatusMessage("Vui lòng chọn lớp trước khi thêm điểm", MessageType.WARNING);
            return;
        }
        
        view.clearForm();
        view.setEditingMode(true);
        isEditing = false;
        currentDiem = null;
        
        // Pre-fill some fields for teacher
        if (userType == 1) {
            // Điền sẵn môn học của giáo viên
            view.getSubjectField().setText(teacherSubject);
            
            // Điền sẵn năm học hiện tại
            java.util.Calendar cal = java.util.Calendar.getInstance();
            int currentYear = cal.get(java.util.Calendar.YEAR);
            int currentMonth = cal.get(java.util.Calendar.MONTH) + 1; // Calendar.MONTH is 0-based
            
            String academicYear;
            if (currentMonth >= 9) { // September onwards is new academic year
                academicYear = currentYear + "-" + (currentYear + 1);
            } else {
                academicYear = (currentYear - 1) + "-" + currentYear;
            }
            view.getAcademicYearField().setText(academicYear);
        }
        
        showStatusMessage("Nhập thông tin điểm mới", MessageType.INFO);
    }
    
    private void handleEdit() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow < 0) {
            showStatusMessage("Vui lòng chọn một dòng để sửa", MessageType.WARNING);
            return;
        }
        
        if (userType == 2) {
            showStatusMessage("Sinh viên không có quyền sửa điểm", MessageType.WARNING);
            return;
        }
        
        // Kiểm tra quyền môn học cho giáo viên
        if (userType == 1 && teacherSubject == null) {
            showStatusMessage("Giáo viên chưa được phân công môn học", MessageType.WARNING);
            return;
        }
        
        // Get selected row data
        try {
            String masv = view.getTable().getValueAt(selectedRow, 0).toString();
            String mamon = view.getTable().getValueAt(selectedRow, 1).toString();
            int hocky = Integer.parseInt(view.getTable().getValueAt(selectedRow, 2).toString());
            
            // Kiểm tra quyền môn học cho giáo viên
            if (userType == 1 && !mamon.equals(teacherSubject)) {
                showStatusMessage("Giáo viên chỉ có thể sửa điểm môn " + teacherSubject, MessageType.WARNING);
                return;
            }
            
            // Find the corresponding DiemModel object
            currentDiem = findDiemByKey(masv, mamon, hocky);
            if (currentDiem != null) {
                view.fillForm(currentDiem);
                view.setEditingMode(true);
                isEditing = true;
                
                showStatusMessage("Đang chỉnh sửa điểm của sinh viên " + masv, MessageType.INFO);
            }
        } catch (Exception e) {
            showStatusMessage("Lỗi khi lấy dữ liệu: " + e.getMessage(), MessageType.ERROR);
        }
    }
    
    private void handleDelete() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow < 0) {
            showStatusMessage("Vui lòng chọn một dòng để xóa", MessageType.WARNING);
            return;
        }
        
        if (userType == 2) {
            showStatusMessage("Sinh viên không có quyền xóa điểm", MessageType.WARNING);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            view,
            "Bạn có chắc chắn muốn xóa điểm này?\nHành động này không thể hoàn tác!",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String masv = view.getTable().getValueAt(selectedRow, 0).toString();
                String mamon = view.getTable().getValueAt(selectedRow, 1).toString();
                int hocky = Integer.parseInt(view.getTable().getValueAt(selectedRow, 2).toString());
                
                if (model.deleteDiem(masv, mamon, hocky)) {
                    // Refresh data theo lớp đã chọn
                    if (userType == 1 && selectedLop != null) {
                        ArrayList<DiemModel> data = model.getDiemByLop(selectedLop);
                        view.loadTableData(data);
                    } else if (userType == 2) {
                        ArrayList<DiemModel> data = model.getDiemByUsername(username);
                        view.loadTableData(data);
                    } else {
                        ArrayList<DiemModel> data = model.getAllDiem();
                        view.loadTableData(data);
                    }
                    view.clearForm();
                    showStatusMessage("Đã xóa điểm thành công", MessageType.SUCCESS);
                } else {
                    showStatusMessage("Xóa điểm thất bại", MessageType.ERROR);
                }
            } catch (Exception e) {
                showStatusMessage("Lỗi khi xóa: " + e.getMessage(), MessageType.ERROR);
            }
        }
    }
    
    private void handleSave() {
        try {
            DiemModel diem = view.getFormData();
            
            // Enhanced validation - removed duplicate validation since getFormData() already validates
            
            // Kiểm tra quyền môn học cho giáo viên
            if (userType == 1) {
                if (teacherSubject == null) {
                    showStatusMessage("Giáo viên chưa được phân công môn học", MessageType.WARNING);
                    return;
                }
                if (!diem.getMamon().equals(teacherSubject)) {
                    showStatusMessage("Giáo viên chỉ có thể nhập điểm cho môn " + teacherSubject, MessageType.WARNING);
                    return;
                }
            }
            
            // Kiểm tra quyền giáo viên: sinh viên phải thuộc lớp đã chọn
            if (userType == 1 && selectedLop != null) {
                if (!model.checkSinhVienTrongLop(diem.getMasv(), selectedLop)) {
                    showStatusMessage("Lỗi: Sinh viên " + diem.getMasv() + " không thuộc lớp đã chọn!", MessageType.ERROR);
                    return;
                }
            }
            
            // Kiểm tra điểm đã tồn tại (cho chức năng thêm)
            if (!isEditing && checkDiemExists(diem.getMasv(), diem.getMamon(), diem.getHocky())) {
                showStatusMessage("Sinh viên đã có điểm môn này ở học kỳ " + diem.getHocky() + "!", MessageType.WARNING);
                return;
            }
            
            boolean success;
            String message;
            
            if (isEditing) {
                // Update existing record
                success = model.updateDiem(diem);
                message = success ? "Cập nhật điểm thành công" : "Cập nhật điểm thất bại";
            } else {
                // Insert new record
                success = model.insertDiem(diem);
                message = success ? "Thêm điểm thành công" : "Thêm điểm thất bại";
            }
            
            if (success) {
                // Refresh data theo lớp đã chọn
                if (userType == 1 && selectedLop != null) {
                    loadDiemForTeacher(); // Sử dụng method mới
                } else if (userType == 1) {
                    loadDiemForTeacher(); // Load tất cả điểm môn của giáo viên
                } else if (userType == 2) {
                    ArrayList<DiemModel> data = model.getDiemByUsername(username);
                    view.loadTableData(data);
                } else {
                    ArrayList<DiemModel> data = model.getAllDiem();
                    view.loadTableData(data);
                }
                
                view.setEditingMode(false);
                view.clearForm();
                isEditing = false;
                currentDiem = null;
                showStatusMessage(message, MessageType.SUCCESS);
                
                // Show success message in dialog as well
                view.showSuccessMessage(message);
            } else {
                showStatusMessage(message, MessageType.ERROR);
            }
            
        } catch (IllegalArgumentException e) {
            view.showValidationError(e.getMessage());
            showStatusMessage(e.getMessage(), MessageType.WARNING);
        } catch (Exception e) {
            showStatusMessage("Lỗi khi lưu: " + e.getMessage(), MessageType.ERROR);
        }
    }
    
    private void handleCancel() {
        view.setEditingMode(false);
        view.clearForm();
        isEditing = false;
        currentDiem = null;
        
        showStatusMessage("Đã hủy thao tác", MessageType.INFO);
    }
    
    private void performSearch() {
        String keyword = view.getSearchKeyword();
        if (keyword.isEmpty()) {
            // Reload dữ liệu gốc
            if (userType == 1) {
                loadDiemForTeacher(); // Sử dụng method mới cho giáo viên
                showStatusMessage("Đã làm mới dữ liệu", MessageType.INFO);
            } else if (userType == 2) {
                ArrayList<DiemModel> data = model.getDiemByUsername(username);
                view.loadTableData(data);
                showStatusMessage("Đã làm mới dữ liệu", MessageType.INFO);
            } else {
                ArrayList<DiemModel> data = model.getAllDiem();
                view.loadTableData(data);
                showStatusMessage("Đã làm mới dữ liệu", MessageType.INFO);
            }
        } else {
            // Tìm kiếm
            ArrayList<DiemModel> kq = searchWithPermission(keyword);
            view.loadTableData(kq);
            showStatusMessage("Tìm thấy " + kq.size() + " kết quả cho '" + keyword + "'", MessageType.INFO);
        }
    }
    
    /**
     * Tìm kiếm với phân quyền
     */
    private ArrayList<DiemModel> searchWithPermission(String keyword) {
        ArrayList<DiemModel> allResults = model.search(keyword);
        ArrayList<DiemModel> filteredResults = new ArrayList<>();
        
        if (userType == 0) { // Admin - thấy tất cả
            return allResults;
        } else if (userType == 1) { // Giáo viên - chỉ thấy môn của mình
            for (DiemModel diem : allResults) {
                // Lọc theo môn học của giáo viên
                if (teacherSubject != null && teacherSubject.equals(diem.getMamon())) {
                    // Nếu đã chọn lớp thì lọc thêm theo lớp
                    if (selectedLop != null) {
                        if (model.checkSinhVienTrongLop(diem.getMasv(), selectedLop)) {
                            filteredResults.add(diem);
                        }
                    } else {
                        filteredResults.add(diem);
                    }
                }
            }
        } else if (userType == 2) { // Sinh viên - chỉ thấy điểm của mình
            for (DiemModel diem : allResults) {
                if (isStudentGrade(diem.getMasv())) {
                    filteredResults.add(diem);
                }
            }
        }
        
        return filteredResults;
    }
    
    /**
     * Kiểm tra điểm có thuộc sinh viên hiện tại không
     */
    private boolean isStudentGrade(String masv) {
        // Lấy mã sinh viên từ username
        String currentMasv = model.getMasvByUsername(username);
        return masv.equals(currentMasv);
    }
    
    private void handleExport() {
        showStatusMessage("Chức năng xuất Excel đang được phát triển", MessageType.INFO);
        // TODO: Implement Excel export functionality
    }
    
    private void handleImport() {
        if (userType == 2) {
            showStatusMessage("Sinh viên không có quyền nhập dữ liệu", MessageType.WARNING);
            return;
        }
        
        showStatusMessage("Chức năng nhập Excel đang được phát triển", MessageType.INFO);
        // TODO: Implement Excel import functionality
    }
    
    private void handleRefresh() {
        // Refresh data theo phân quyền
        if (userType == 1) {
            loadDiemForTeacher(); // Sử dụng method mới cho giáo viên
            if (selectedLop != null) {
                showStatusMessage("Đã làm mới dữ liệu môn " + teacherSubject + " của lớp " + selectedLop, MessageType.INFO);
            } else {
                showStatusMessage("Đã làm mới dữ liệu môn " + teacherSubject, MessageType.INFO);
            }
        } else {
            loadInitialData();
            showStatusMessage("Đã làm mới dữ liệu", MessageType.INFO);
        }
        view.clearForm();
        view.setEditingMode(false);
        isEditing = false;
        currentDiem = null;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) { // Single click
            int selectedRow = view.getTable().getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    String masv = view.getTable().getValueAt(selectedRow, 0).toString();
                    String mamon = view.getTable().getValueAt(selectedRow, 1).toString();
                    int hocky = Integer.parseInt(view.getTable().getValueAt(selectedRow, 2).toString());
                    
                    DiemModel selectedDiem = findDiemByKey(masv, mamon, hocky);
                    if (selectedDiem != null) {
                        view.fillForm(selectedDiem);
                        showStatusMessage("Đã chọn điểm của sinh viên " + masv, MessageType.INFO);
                    }
                } catch (Exception ex) {
                    showStatusMessage("Lỗi khi hiển thị thông tin", MessageType.ERROR);
                }
            }
        } else if (e.getClickCount() == 2 && userType != 2) { // Double click for edit
            handleEdit();
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
    
    // Helper methods
    private DiemModel findDiemByKey(String masv, String mamon, int hocky) {
        try {
            ArrayList<DiemModel> allDiem;
            if (userType == 2) {
                allDiem = model.getDiemByUsername(username);
            } else if (userType == 1 && selectedLop != null) {
                allDiem = model.getDiemByLop(selectedLop);
            } else {
                allDiem = model.getAllDiem();
            }
            
            for (DiemModel diem : allDiem) {
                if (diem.getMasv().equals(masv) && 
                    diem.getMamon().equals(mamon) && 
                    diem.getHocky() == hocky) {
                    return diem;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private String validateDiem(DiemModel diem) {
        if (diem.getMasv().isEmpty()) return "Mã sinh viên không được để trống";
        if (diem.getMamon().isEmpty()) return "Mã môn học không được để trống";
        if (diem.getNamhoc().isEmpty()) return "Năm học không được để trống";
        if (diem.getDiemcc() < 0 || diem.getDiemcc() > 10) return "Điểm chuyên cần phải từ 0-10";
        if (diem.getDiemgk() < 0 || diem.getDiemgk() > 10) return "Điểm giữa kỳ phải từ 0-10";
        if (diem.getDiemck() < 0 || diem.getDiemck() > 10) return "Điểm cuối kỳ phải từ 0-10";
        
        return null;
    }
    
    // Status message system
    enum MessageType {
        SUCCESS, ERROR, WARNING, INFO
    }
    
    private void showStatusMessage(String message, MessageType type) {
        SwingUtilities.invokeLater(() -> {
            // For now, just print to console
            // In a real implementation, this would show in a status bar or notification
            String prefix;
            switch (type) {
                case SUCCESS: prefix = "[OK] "; break;
                case ERROR: prefix = "[LỖI] "; break;
                case WARNING: prefix = "[CẢNH BÁO] "; break;
                case INFO: default: prefix = "[INFO] "; break;
            }
            System.out.println(prefix + message);
            
            // For critical errors, show dialog
            if (type == MessageType.ERROR) {
                JOptionPane.showMessageDialog(view, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    /**
     * Kiểm tra điểm đã tồn tại hay chưa
     */
    private boolean checkDiemExists(String masv, String mamon, int hocky) {
        try {
            ArrayList<DiemModel> allDiem;
            if (userType == 1) {
                // Giáo viên chỉ kiểm tra trong phạm vi môn của mình
                allDiem = model.getDiemByMon(teacherSubject);
            } else {
                allDiem = model.getAllDiem();
            }
            
            for (DiemModel diem : allDiem) {
                if (diem.getMasv().equals(masv) && 
                    diem.getMamon().equals(mamon) && 
                    diem.getHocky() == hocky) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}