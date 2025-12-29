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
import Model.KhoaModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.table.TableModel;

/**
 *
 * @author noqok
 */
public class KhoaPanel extends JPanel {

    private KhoaModel k;
    private JLabel lblmakhoa;
    private JLabel lbltenkhoa;
    private JLabel lblsearch;

    private JTextField tfmakhoa;
    private JTextField tftenkhoa;
    private JTextField tfsearch;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnXacnhan;
    private JButton btnHuy;
    private JButton btnSearch;

    private JTable tblKhoa;
    private DefaultTableModel model;

    public KhoaPanel() {

        Display();
        ArrayList<KhoaModel> ds = new ArrayList<>();
        loadtable(ds);
    }

    public void Display() {

        this.setSize(900, 750);

        lblmakhoa = new JLabel("Mã Khoa");
        lbltenkhoa = new JLabel("Tên Khoa");
        lblsearch = new JLabel("Tìm Kiếm");
        tfmakhoa = new JTextField(20);
        tftenkhoa = new JTextField(20);
        tfsearch = new JTextField(20);

        JPanel pnmakhoa = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pntenkhoa = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pns = new JPanel(new GridLayout(1, 3, 5, 5));
        pnmakhoa.add(lblmakhoa);
        pnmakhoa.add(tfmakhoa);
        pntenkhoa.add(lbltenkhoa);
        pntenkhoa.add(tftenkhoa);
        pns.add(lblsearch);
        pns.add(tfsearch);
        btnSearch = new JButton("🔍");
        pns.add(btnSearch);
        JPanel pnForm = new JPanel(new GridLayout(3, 1, 5, 5));
        pnForm.add(pnmakhoa);
        pnForm.add(pntenkhoa);
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
            "Mã Khoa", "Tên Khoa"
        };
        model = new DefaultTableModel(tencot, 0);
        tblKhoa = new JTable(model);

        JPanel pnTable = new JPanel();
        pnTable.add(tblKhoa.getTableHeader());
        pnTable.add(tblKhoa);

        this.setLayout(new GridLayout(3, 1, 5, 5));
        this.add(pnForm);
        this.add(pnButton);
        this.add(pnTable);

    }

    public JTable getTblKhoa() {
        return tblKhoa;
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
        tblKhoa.addMouseListener(a);

    }

    public KhoaModel getformdata() {

        String makhoa = tfmakhoa.getText().trim();
        String tenkhoa = tftenkhoa.getText().trim();

        return new KhoaModel(makhoa, tenkhoa);
    }

    public void fillform(KhoaModel k) {
        if (k == null) {
            return;
        }

        tfmakhoa.setText(k.getMakhoa());
        tftenkhoa.setText(k.getTenkhoa());
    }

    public void clearform() {
        tfmakhoa.setText("");
        tftenkhoa.setText("");

    }

    public void loadtable(ArrayList<KhoaModel> d) {
        model.setRowCount(0);
        for (KhoaModel b : d) {

            Object[] dulieubang = {
                b.getMakhoa(),
                b.getTenkhoa()
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

