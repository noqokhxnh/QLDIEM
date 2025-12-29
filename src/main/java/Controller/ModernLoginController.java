package Controller;

import Model.LoginModel;
import View.ModernLoginView;
import View.ModernMainLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Modern Login Controller with enhanced UI feedback
 */
public class ModernLoginController implements ActionListener {
    
    private ModernLoginView view;
    private LoginModel model;
    
    public ModernLoginController(ModernLoginView view, LoginModel model) {
        this.view = view;
        this.model = model;
        this.view.addLoginListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnLogin()) {
            handleLogin();
        }
    }
    
    private void handleLogin() {
        // Get credentials
        String username = view.getUsername().trim();
        String password = view.getPassword();
        
        // Basic validation
        if (username.isEmpty()) {
            view.setStatus("Vui lòng nhập tên đăng nhập", true);
            return;
        }
        
        if (password.isEmpty()) {
            view.setStatus("Vui lòng nhập mật khẩu", true);
            return;
        }
        
        // Show loading state
        view.setStatus("Đang kiểm tra thông tin đăng nhập...", false);
        view.getBtnLogin().setEnabled(false);
        view.getBtnLogin().setText("⏳ Đang xử lý...");
        
        // Use SwingWorker for background processing
        SwingWorker<Integer, Void> loginWorker = new SwingWorker<Integer, Void>() {
            @Override
            protected Integer doInBackground() throws Exception {
                // Simulate some processing time for better UX
                Thread.sleep(800);
                return model.checklogin(username, password);
            }
            
            @Override
            protected void done() {
                try {
                    Integer userType = get();
                    
                    if (userType != null) {
                        handleLoginSuccess(username, userType);
                    } else {
                        handleLoginFailure();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    view.setStatus("Lỗi hệ thống: " + ex.getMessage(), true);
                    resetLoginButton();
                }
            }
        };
        
        loginWorker.execute();
    }
    
    private void handleLoginSuccess(String username, int userType) {
        view.setStatus("Đăng nhập thành công!", false);
        
        // Small delay for user to see success message
        Timer timer = new Timer(500, e -> {
            view.dispose();
            
            // Create and show modern main layout
            SwingUtilities.invokeLater(() -> {
                try {
                    ModernMainLayout mainLayout = new ModernMainLayout(username, userType);
                    ModernMainController mainController = new ModernMainController(mainLayout, username, userType);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, 
                        "Lỗi khi khởi tạo giao diện chính: " + ex.getMessage(),
                        "Lỗi hệ thống", 
                        JOptionPane.ERROR_MESSAGE);
                }
            });
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void handleLoginFailure() {
        view.setStatus("Tên đăng nhập hoặc mật khẩu không chính xác", true);
        resetLoginButton();
        
        // Clear password field
        view.clearForm();
    }
    
    private void resetLoginButton() {
        view.getBtnLogin().setEnabled(true);
        view.getBtnLogin().setText("Đăng nhập");
    }
}