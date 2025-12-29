package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Modern Login View with beautiful design
 */
public class ModernLoginView extends JFrame {
    
    // Color scheme
    private static final Color PRIMARY_COLOR = new Color(63, 81, 181);
    private static final Color PRIMARY_DARK = new Color(48, 63, 159);
    private static final Color ACCENT_COLOR = new Color(255, 64, 129);
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    private static final Color TEXT_SECONDARY = new Color(108, 117, 125);
    private static final Color TEXT_HINT = new Color(173, 181, 189);
    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnExit;
    private JButton btnShowPassword;
    private JComboBox<String> cbUserType;
    private JLabel lblStatus;
    private boolean passwordVisible = false;
    
    public ModernLoginView() {
        initComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initComponents() {
        // Frame setup
        setTitle("QLDIEM - Đăng nhập hệ thống");
        setSize(500, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Set background
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Username field
        txtUsername = createTextField("Tên đăng nhập");
        
        // Password field
        txtPassword = createPasswordField("Mật khẩu");
        
        // User type combo box
        String[] userTypes = {
            "Quản trị viên",
            "Giáo viên", 
            "Sinh viên"
        };
        cbUserType = new JComboBox<>(userTypes);
        cbUserType.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbUserType.setPreferredSize(new Dimension(300, 45));
        cbUserType.setBackground(Color.WHITE);
        cbUserType.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        // Show password button
        btnShowPassword = new JButton("👁");
        btnShowPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnShowPassword.setPreferredSize(new Dimension(45, 45));
        btnShowPassword.setBackground(Color.WHITE);
        btnShowPassword.setBorder(BorderFactory.createLineBorder(new Color(206, 212, 218), 1));
        btnShowPassword.setFocusPainted(false);
        btnShowPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Login button
        btnLogin = createButton("Đăng nhập", PRIMARY_COLOR, Color.WHITE);
        btnLogin.setPreferredSize(new Dimension(300, 50));
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        // Exit button
        btnExit = createButton("Thoát", TEXT_SECONDARY, Color.WHITE);
        btnExit.setPreferredSize(new Dimension(140, 40));
        
        // Status label
        lblStatus = new JLabel(" ");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblStatus.setForeground(ACCENT_COLOR);
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(300, 45));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        // Add placeholder functionality
        field.setForeground(TEXT_HINT);
        field.setText(placeholder);
        
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(TEXT_PRIMARY);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(TEXT_HINT);
                    field.setText(placeholder);
                }
            }
        });
        
        return field;
    }
    
    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(250, 45));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setEchoChar('●');
        
        return field;
    }
    
    private JButton createButton(String text, Color backgroundColor, Color textColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(textColor);
        button.setBackground(backgroundColor);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        Color originalColor = backgroundColor;
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor.brighter());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
        
        return button;
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Logo and title
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel logoLabel = new JLabel("QLDIEM", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        logoLabel.setForeground(PRIMARY_COLOR);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("QLDIEM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Hệ thống Quản lý Điểm sinh viên", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(logoLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setBackground(CARD_COLOR);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Form title
        JLabel formTitle = new JLabel("Đăng nhập vào hệ thống");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        formTitle.setForeground(TEXT_PRIMARY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(formTitle, gbc);
        
        // Username field
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblUsername.setForeground(TEXT_PRIMARY);
        lblUsername.setPreferredSize(new Dimension(120, 25));
        formPanel.add(lblUsername, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtUsername.setPreferredSize(new Dimension(300, 45));
        formPanel.add(txtUsername, gbc);
        
        // Password field
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblPassword.setForeground(TEXT_PRIMARY);
        lblPassword.setPreferredSize(new Dimension(120, 25));
        formPanel.add(lblPassword, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JPanel passwordPanel = new JPanel(new BorderLayout(5, 0));
        passwordPanel.setBackground(CARD_COLOR);
        passwordPanel.setPreferredSize(new Dimension(300, 45));
        txtPassword.setPreferredSize(new Dimension(250, 45));
        passwordPanel.add(txtPassword, BorderLayout.CENTER);
        passwordPanel.add(btnShowPassword, BorderLayout.EAST);
        formPanel.add(passwordPanel, gbc);
        
        // User type field
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel lblUserType = new JLabel("Loại người dùng:");
        lblUserType.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblUserType.setForeground(TEXT_PRIMARY);
        lblUserType.setPreferredSize(new Dimension(120, 25));
        formPanel.add(lblUserType, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cbUserType.setPreferredSize(new Dimension(300, 45));
        formPanel.add(cbUserType, gbc);
        
        // Status label
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        lblStatus.setPreferredSize(new Dimension(300, 20));
        formPanel.add(lblStatus, gbc);
        
        // Button panel
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.add(btnLogin);
        formPanel.add(buttonPanel, gbc);
        
        // Exit button
        gbc.gridy = 6;
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        exitPanel.setBackground(CARD_COLOR);
        exitPanel.add(btnExit);
        formPanel.add(exitPanel, gbc);
        
        // Add to main panel
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(formPanel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(BACKGROUND_COLOR);
        
        JLabel footerLabel = new JLabel("© 2024 QLDIEM - Phát triển bởi Nhóm Dev");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(TEXT_SECONDARY);
        
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        // Show/hide password
        btnShowPassword.addActionListener(e -> togglePasswordVisibility());
        
        // Enter key for login
        txtPassword.addActionListener(e -> btnLogin.doClick());
        
        // Exit button
        btnExit.addActionListener(e -> System.exit(0));
    }
    
    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            txtPassword.setEchoChar((char) 0);
            btnShowPassword.setText("Ẩn");
        } else {
            txtPassword.setEchoChar('●');
            btnShowPassword.setText("Hiện");
        }
    }
    
    // Public methods
    public String getUsername() {
        String username = txtUsername.getText();
        return username.equals("Tên đăng nhập") ? "" : username;
    }
    
    public String getPassword() {
        return new String(txtPassword.getPassword());
    }
    
    public int getUserType() {
        return cbUserType.getSelectedIndex();
    }
    
    public void setStatus(String message, boolean isError) {
        lblStatus.setText(message);
        lblStatus.setForeground(isError ? ACCENT_COLOR : PRIMARY_COLOR);
    }
    
    public void clearForm() {
        txtUsername.setText("Tên đăng nhập");
        txtUsername.setForeground(TEXT_HINT);
        txtPassword.setText("");
        cbUserType.setSelectedIndex(0);
        lblStatus.setText(" ");
    }
    
    public void addLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }
    
    public JButton getBtnLogin() {
        return btnLogin;
    }
}