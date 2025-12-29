/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import Model.GiaoVienModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.table.TableModel;

/**
 *
 * @author noqok
 */
public class GiaoVienPanel extends JPanel {

    private GiaoVienModel gv;
    private JLabel lblmagv;
    private JLabel lblhoten;
    private JLabel lblgioitinh;
    private JLabel lblngaysinh;
    private JLabel lblemail;
    private JLabel lblsdt;
    private JLabel lblmakhoa;
    private JLabel lblusername;
    private JLabel lblsearch;

    private JTextField tfmagv;
    private JTextField tfhoten;
    private JTextField tfgioitinh;
    private JTextField tfngaysinh;
    private JTextField tfemail;
    private JTextField tfsdt;
    private JTextField tfmakhoa;
    private JTextField tfusername;
    private JTextField tfsearch;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnXacnhan;
    private JButton btnHuy;
    private JButton btnSearch;

    private JTable tblGiaoVien;
    private DefaultTableModel model;

    public GiaoVienPanel() {

        Display();
        ArrayList<GiaoVienModel> ds = new ArrayList<>();
        loadtable(ds);
    }

    public void Display() {

        this.setSize(900, 750);

        lblmagv = new JLabel("Mã Giáo Viên");
        lblhoten = new JLabel("Họ Tên");
        lblgioitinh = new JLabel("Giới Tính");
        lblngaysinh = new JLabel("Ngày Sinh (yyyy-MM-dd)");
        lblemail = new JLabel("Email");
        lblsdt = new JLabel("Số Điện Thoại");
        lblmakhoa = new JLabel("Mã Khoa");
        lblusername = new JLabel("Username");
        lblsearch = new JLabel("Tìm Kiếm");
        tfmagv = new JTextField(20);
        tfhoten = new JTextField(20);
        tfgioitinh = new JTextField(20);
        tfngaysinh = new JTextField(20);
        tfemail = new JTextField(20);
        tfsdt = new JTextField(20);
        tfmakhoa = new JTextField(20);
        tfusername = new JTextField(20);
        tfsearch = new JTextField(20);

        JPanel pnmagv = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pnhoten = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pngioitinh = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pnngaysinh = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pnemail = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pnsdt = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pnmakhoa = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pnusername = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pns = new JPanel(new GridLayout(1, 3, 5, 5));
        pnmagv.add(lblmagv);
        pnmagv.add(tfmagv);
        pnhoten.add(lblhoten);
        pnhoten.add(tfhoten);
        pngioitinh.add(lblgioitinh);
        pngioitinh.add(tfgioitinh);
        pnngaysinh.add(lblngaysinh);
        pnngaysinh.add(tfngaysinh);
        pnemail.add(lblemail);
        pnemail.add(tfemail);
        pnsdt.add(lblsdt);
        pnsdt.add(tfsdt);
        pnmakhoa.add(lblmakhoa);
        pnmakhoa.add(tfmakhoa);
        pnusername.add(lblusername);
        pnusername.add(tfusername);
        pns.add(lblsearch);
        pns.add(tfsearch);
        btnSearch = new JButton("🔍");
        pns.add(btnSearch);
        JPanel pnForm = new JPanel(new GridLayout(9, 1, 5, 5));
        pnForm.add(pnmagv);
        pnForm.add(pnhoten);
        pnForm.add(pngioitinh);
        pnForm.add(pnngaysinh);
        pnForm.add(pnemail);
        pnForm.add(pnsdt);
        pnForm.add(pnmakhoa);
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
            "Mã GV", "Họ Tên", "Giới Tính", "Ngày Sinh",
            "Email", "SĐT", "Mã Khoa", "Username"
        };
        model = new DefaultTableModel(tencot, 0);
        tblGiaoVien = new JTable(model);

        JPanel pnTable = new JPanel();
        pnTable.add(tblGiaoVien.getTableHeader());
        pnTable.add(tblGiaoVien);

        this.setLayout(new GridLayout(3, 1, 5, 5));
        this.add(pnForm);
        this.add(pnButton);
        this.add(pnTable);

    }

    public JTable getTblGiaoVien() {
        return tblGiaoVien;
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
        tblGiaoVien.addMouseListener(a);

    }

    public GiaoVienModel getformdata() {

        String magv = tfmagv.getText().trim();
        String hoten = tfhoten.getText().trim();
        String gioitinh = tfgioitinh.getText().trim();
        String ngaysinh = tfngaysinh.getText().trim();
        String email = tfemail.getText().trim();
        String sdt = tfsdt.getText().trim();
        String makhoa = tfmakhoa.getText().trim();
        String username = tfusername.getText().trim();

        return new GiaoVienModel(magv, hoten, gioitinh, ngaysinh, email, sdt, makhoa, username);
    }

    public void fillform(GiaoVienModel gv) {
        if (gv == null) {
            return;
        }

        tfmagv.setText(gv.getMagv());
        tfhoten.setText(gv.getHoten());
        tfgioitinh.setText(gv.getGioitinh());
        tfngaysinh.setText(gv.getNgaysinh());
        tfemail.setText(gv.getEmail());
        tfsdt.setText(gv.getSdt());
        tfmakhoa.setText(gv.getMakhoa());
        tfusername.setText(gv.getUsername());
    }

    public void clearform() {
        tfmagv.setText("");
        tfhoten.setText("");
        tfgioitinh.setText("");
        tfngaysinh.setText("");
        tfemail.setText("");
        tfsdt.setText("");
        tfmakhoa.setText("");
        tfusername.setText("");

    }

    public void loadtable(ArrayList<GiaoVienModel> d) {
        model.setRowCount(0);
        for (GiaoVienModel b : d) {

            Object[] dulieubang = {
                b.getMagv(),
                b.getHoten(),
                b.getGioitinh(),
                b.getNgaysinh(),
                b.getEmail(),
                b.getSdt(),
                b.getMakhoa(),
                b.getUsername()
            };
            model.addRow(dulieubang);
        }
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

