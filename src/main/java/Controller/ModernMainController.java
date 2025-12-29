package Controller;

import Model.DiemModel;
import View.ModernMainLayout;
import View.ModernDiemPanel;
import View.QuanLyLopPanel;
import View.PhanCongGiaoVienPanel;
import View.QuanLySinhVienPanel;
import View.QuanLyGiaoVienPanel;
import Controller.QuanLyLopController;
import Controller.PhanCongGiaoVienController;
import Controller.QuanLySinhVienController;
import Controller.QuanLyGiaoVienController;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Modern MainController with enhanced functionality
 */
public class ModernMainController {
    
    private ModernMainLayout view;
    private String username;
    private int userType;
    private ModernDiemPanel diemPanel;
    
    // Controllers as instance variables for refresh capability
    private QuanLyLopController lopController;
    private PhanCongGiaoVienController phanCongController;
    private QuanLySinhVienController svController;
    private QuanLyGiaoVienController gvController;
    
    public ModernMainController(ModernMainLayout view, String username, int userType) {
        this.view = view;
        this.username = username;
        this.userType = userType;
        
        initializePanels();
        setupEventHandlers();
        
        // Show dashboard by default
        view.showPanel("DASHBOARD");
        view.setVisible(true);
    }
    
    private void initializePanels() {
        // Initialize DiemPanel with modern design
        diemPanel = new ModernDiemPanel(username, userType);
        view.getMainContentPanel().add(diemPanel, "DIEM");
        
        // Initialize DiemController
        ModernDiemController diemController = new ModernDiemController(diemPanel, username, userType);
        
        // Initialize Admin panels
        if (userType == 0) {
            // Quản lý Lớp
            QuanLyLopPanel lopPanel = new QuanLyLopPanel();
            view.getMainContentPanel().add(lopPanel, "LOP");
            lopController = new QuanLyLopController(lopPanel, this); // Pass main controller for refresh
            
            // Phân lớp cho Giáo viên
            PhanCongGiaoVienPanel phanCongPanel = new PhanCongGiaoVienPanel();
            view.getMainContentPanel().add(phanCongPanel, "PHANCONG");
            phanCongController = new PhanCongGiaoVienController(phanCongPanel);
            
            // Quản lý Sinh viên
            QuanLySinhVienPanel svPanel = new QuanLySinhVienPanel();
            view.getMainContentPanel().add(svPanel, "SINHVIEN");
            svController = new QuanLySinhVienController(svPanel);
            
            // Quản lý Giáo viên
            QuanLyGiaoVienPanel gvPanel = new QuanLyGiaoVienPanel();
            view.getMainContentPanel().add(gvPanel, "GIAOVIEN");
            gvController = new QuanLyGiaoVienController(gvPanel);
        }
    }
    
    private void setupEventHandlers() {
        // Navigation event handlers
        view.onDashboardClick(e -> view.showPanel("DASHBOARD"));
        view.onDiemClick(e -> view.showPanel("DIEM"));
        
        if (userType == 0) { // Admin
            view.onSinhVienClick(e -> view.showPanel("SINHVIEN"));
            view.onGiaoVienClick(e -> view.showPanel("GIAOVIEN")); // Quản lý giáo viên
            view.onLopClick(e -> view.showPanel("PHANCONG")); // Phân lớp
            view.onQuanLyLopClick(e -> view.showPanel("LOP")); // Quản lý lớp
        }
        
        // Logout handler
        view.onDangXuatClick(e -> handleLogout());
    }
    
    /**
     * Refresh all class-related data across panels
     */
    public void refreshClassData() {
        if (userType == 0) { // Admin only
            if (svController != null) {
                svController.refreshLopData();
            }
            if (phanCongController != null) {
                phanCongController.refreshLopData();
            }
        }
    }
    
    /**
     * Refresh all data across panels (including student data)
     */
    public void refreshAllData() {
        if (userType == 0) { // Admin only
            // Refresh class data first
            refreshClassData();
            
            // Refresh student data (in case student-class relationship changed)
            if (svController != null) {
                svController.refreshAllData();
            }
        }
    }
    
    private void handleLogout() {
        int option = JOptionPane.showConfirmDialog(
            view,
            "Bạn có chắc chắn muốn đăng xuất?",
            "Xác nhận đăng xuất",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (option == JOptionPane.YES_OPTION) {
            view.dispose();
            
            // Return to login screen
            SwingUtilities.invokeLater(() -> {
                try {
                    Class<?> loginMainClass = Class.forName("Test.LoginMain");
                    loginMainClass.getMethod("main", String[].class).invoke(null, (Object) new String[]{});
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
    }
}