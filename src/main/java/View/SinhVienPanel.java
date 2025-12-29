/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import Model.SinhVienModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.table.TableModel;

/**
 *
 * @author noqok
 */
public class SinhVienPanel extends JPanel {

    private SinhVienModel sv;
    private JLabel lblmasv;
    private JLabel lblhoten;
    private JLabel lblngaysinh;
    private JLabel lblgioitinh;
    private JLabel lbldiachi;
    private JLabel lblmalop;
    private JLabel lblusername;
    private JLabel lblsearch;

    private JTextField tfmasv;
    private JTextField tfhoten;
    private JTextField tfngaysinh;
    private JTextField tfgioitinh;
    private JTextField tfdiachi;
    private JTextField tfmalop;
    private JTextField tfusername;
    private JTextField tfsearch;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnXacnhan;
    private JButton btnHuy;
    private JButton btnSearch;

    private JTable tblSinhVien;
    private DefaultTableModel model;

    public SinhVienPanel() {

        Display();
        ArrayList<SinhVienModel> ds = new ArrayList<>();
        loadtable(ds);
    }

    public void Display() {

        this.setSize(900, 750);

        lblmasv = new JLabel("Mã Sinh Viên");
        lblhoten = new JLabel("Họ Tên");
        lblngaysinh = new JLabel("Ngày Sinh (yyyy-MM-dd)");
        lblgioitinh = new JLabel("Giới Tính");
        lbldiachi = new JLabel("Địa Chỉ");
        lblmalop = new JLabel("Mã Lớp");
        lblusername = new JLabel("Username");
        lblsearch = new JLabel("Tìm Kiếm");
        tfmasv = new JTextField(20);
        tfhoten = new JTextField(20);
        tfngaysinh = new JTextField(20);
        tfgioitinh = new JTextField(20);
        tfdiachi = new JTextField(20);
        tfmalop = new JTextField(20);
        tfusername = new JTextField(20);
        tfsearch = new JTextField(20);

        JPanel pnmasv = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pnhoten = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pnngaysinh = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pngioitinh = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pndiachi = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pnmalop = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pnusername = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pns = new JPanel(new GridLayout(1, 3, 5, 5));
        pnmasv.add(lblmasv);
        pnmasv.add(tfmasv);
        pnhoten.add(lblhoten);
        pnhoten.add(tfhoten);
        pnngaysinh.add(lblngaysinh);
        pnngaysinh.add(tfngaysinh);
        pngioitinh.add(lblgioitinh);
        pngioitinh.add(tfgioitinh);
        pndiachi.add(lbldiachi);
        pndiachi.add(tfdiachi);
        pnmalop.add(lblmalop);
        pnmalop.add(tfmalop);
        pnusername.add(lblusername);
        pnusername.add(tfusername);
        pns.add(lblsearch);
        pns.add(tfsearch);
        btnSearch = new JButton("🔍");
        pns.add(btnSearch);
        JPanel pnForm = new JPanel(new GridLayout(8, 1, 5, 5));
        pnForm.add(pnmasv);
        pnForm.add(pnhoten);
        pnForm.add(pnngaysinh);
        pnForm.add(pngioitinh);
        pnForm.add(pndiachi);
        pnForm.add(pnmalop);
        pnForm.add(pnusername);
        pnForm.add(pns);

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnXacnhan = new JButton("Xác nhận");
        btnHuy = new JButton("Hủy");

        JPanel pnButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnButton.add(btnThem);
        pnButton.add(btnSua);
        pnButton.add(btnXoa);
        pnButton.add(btnXacnhan);
        pnButton.add(btnHuy);

        String[] tencot = {
            "Mã SV", "Họ Tên", "Ngày Sinh", "Giới Tính",
            "Địa Chỉ", "Mã Lớp", "Username"
        };
        model = new DefaultTableModel(tencot, 0);
        tblSinhVien = new JTable(model);
        tblSinhVien.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(tblSinhVien);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        JPanel pnTable = new JPanel(new BorderLayout());
        pnTable.add(scrollPane, BorderLayout.CENTER);

        this.setLayout(new GridLayout(3, 1, 5, 5));
        this.add(pnForm);
        this.add(pnButton);
        this.add(pnTable);

    }

    public JTable getTblSinhVien() {
        return tblSinhVien;
    }

    public void addActionListerner(ActionListener a) {
        btnThem.addActionListener(a);
        btnSua.addActionListener(a);
        btnXoa.addActionListener(a);
        btnXacnhan.addActionListener(a);
        btnHuy.addActionListener(a);
        btnSearch.addActionListener(a);

    }

    public void addTableMouseAction(MouseListener a) {
        tblSinhVien.addMouseListener(a);

    }

    public SinhVienModel getformdata() {

        String masv = tfmasv.getText().trim();
        String hoten = tfhoten.getText().trim();
        String ngaysinh = tfngaysinh.getText().trim();
        String gioitinh = tfgioitinh.getText().trim();
        String diachi = tfdiachi.getText().trim();
        String malop = tfmalop.getText().trim();
        String username = tfusername.getText().trim();

        return new SinhVienModel(username, masv, hoten, ngaysinh, gioitinh, diachi, malop);
    }

    public void fillform(SinhVienModel sv) {
        if (sv == null) {
            return;
        }

        tfmasv.setText(sv.getMasv());
        tfhoten.setText(sv.getHoten());
        tfngaysinh.setText(sv.getNgaysinh());
        tfgioitinh.setText(sv.getGioitinh());
        tfdiachi.setText(sv.getDiachi());
        tfmalop.setText(sv.getMalop());
        tfusername.setText(sv.getUsername());
    }

    public void clearform() {
        tfmasv.setText("");
        tfhoten.setText("");
        tfngaysinh.setText("");
        tfgioitinh.setText("");
        tfdiachi.setText("");
        tfmalop.setText("");
        tfusername.setText("");

    }

    public void loadtable(ArrayList<SinhVienModel> d) {
        model.setRowCount(0);
        for (SinhVienModel b : d) {

            Object[] dulieubang = {
                b.getMasv(),
                b.getHoten(),
                b.getNgaysinh(),
                b.getGioitinh(),
                b.getDiachi(),
                b.getMalop(),
                b.getUsername()
            };
            model.addRow(dulieubang);
        }
        model.fireTableDataChanged();
        this.revalidate();
        this.repaint();
    }

    public void on_off(boolean a, boolean b) {
        btnThem.setVisible(a);
        btnSua.setVisible(a);
        btnXoa.setVisible(a);
        btnXacnhan.setVisible(b);
        btnHuy.setVisible(b);
    }

    public String getSearchKeyword() {
        return tfsearch.getText().trim();
    }
}
