/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
/**
 *
 * @author noqok
 */
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainLayout extends JFrame {

    private CardLayout cardLayout;
    private JPanel panel_main;

    private JButton btnDiem;
    private JButton btnLop;
    private JButton btnSinhVien;
    private JButton btnGiaoVien;
    private JButton btnKhoa;
    private JButton btnMonHoc;
    private JButton btnTTTK;



    public MainLayout() {
        initUI();
    }

    private void initUI() {
        setTitle("Quản Lý Điểm");
        setSize(1250, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        btnDiem = new JButton("Quản Lý Điểm");
        btnLop = new JButton("Quản Lý Lớp");
        btnSinhVien = new JButton("Quản Lý Sinh Viên");
        btnGiaoVien = new JButton("Quản Lý Giáo Viên");
        btnKhoa = new JButton("Quản Lý Khoa");
        btnMonHoc = new JButton("Quản Lý Môn Học");
        btnTTTK = new JButton("Thông tin tài khoản");
        JPanel panel_menu = new JPanel(new GridLayout(7,1, 5, 5));
        panel_menu.add(btnDiem);
        panel_menu.add(btnLop);
        panel_menu.add(btnSinhVien);
        panel_menu.add(btnGiaoVien);
        panel_menu.add(btnKhoa);
        panel_menu.add(btnMonHoc);
        panel_menu.add(btnTTTK);
        cardLayout = new CardLayout();
        panel_main = new JPanel(cardLayout);


        panel_main.add(new SinhVienPanel(), "SV");
        panel_main.add(new LopPanel(), "LOP");
        panel_main.add(new DiemPanel(), "DIEM");
        panel_main.add(new GiaoVienPanel(), "GV");
        panel_main.add(new KhoaPanel(), "KHOA");
        panel_main.add(new MonHocPanel(), "MONHOC");
//        panel_main.add(new ThongTinTaiKhoan(), "TT"); // Đã được khởi tạo trong MainController với username

        add(panel_menu, BorderLayout.WEST);
        add(panel_main, BorderLayout.CENTER);

        setVisible(true);
    }

    public JPanel getMainPanel() {
        return panel_main;
    }

    public CardLayout getCardLayout() {
        return (CardLayout) this.panel_main.getLayout();
    }

    public void onSinhVienClick(ActionListener l) { btnSinhVien.addActionListener(l); }
    public void onLopClick(ActionListener l) { btnLop.addActionListener(l); }
    public void onDiemClick(ActionListener l) { btnDiem.addActionListener(l); }
    public void onGVClick(ActionListener l) { btnGiaoVien.addActionListener(l); }
    public void onKhoaClick(ActionListener l) { btnKhoa.addActionListener(l); }
    public void onMonHocClick(ActionListener l) { btnMonHoc.addActionListener(l); }
    public void onTTClick(ActionListener l ){btnTTTK.addActionListener(l);}

}
