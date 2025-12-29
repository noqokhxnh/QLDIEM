/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.awt.BorderLayout;
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
import Model.MonHocModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.table.TableModel;

/**
 *
 * @author noqok
 */
public class MonHocPanel extends JPanel {

    private MonHocModel m;
    private JLabel lblmamon;
    private JLabel lbltenmon;
    private JLabel lblsotinchi;
    private JLabel lblsearch;

    private JTextField tfmamon;
    private JTextField tftenmon;
    private JTextField tfsotinchi;
    private JTextField tfsearch;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnXacnhan;
    private JButton btnHuy;
    private JButton btnSearch;

    private JTable tblMonHoc;
    private DefaultTableModel model;

    public MonHocPanel() {

        Display();
        ArrayList<MonHocModel> ds = new ArrayList<>();
        loadtable(ds);
    }

    public void Display() {

        this.setSize(900, 750);

        lblmamon = new JLabel("Mã Môn Học");
        lbltenmon = new JLabel("Tên Môn Học");
        lblsotinchi = new JLabel("Số Tín Chỉ");
        lblsearch = new JLabel("Tìm Kiếm");
        tfmamon = new JTextField(20);
        tftenmon = new JTextField(20);
        tfsotinchi = new JTextField(20);
        tfsearch = new JTextField(20);

        JPanel pnmamon = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pntenmon = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pnsotinchi = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pns = new JPanel(new GridLayout(1, 3, 5, 5));
        pnmamon.add(lblmamon);
        pnmamon.add(tfmamon);
        pntenmon.add(lbltenmon);
        pntenmon.add(tftenmon);
        pnsotinchi.add(lblsotinchi);
        pnsotinchi.add(tfsotinchi);
        pns.add(lblsearch);
        pns.add(tfsearch);
        btnSearch = new JButton("🔍");
        pns.add(btnSearch);
        JPanel pnForm = new JPanel(new GridLayout(4, 1, 5, 5));
        pnForm.add(pnmamon);
        pnForm.add(pntenmon);
        pnForm.add(pnsotinchi);
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
            "Mã Môn", "Tên Môn", "Số Tín Chỉ"
        };
        model = new DefaultTableModel(tencot, 0);
        tblMonHoc = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(tblMonHoc);
        JPanel pnTable = new JPanel(new BorderLayout());
        pnTable.add(scrollPane, BorderLayout.CENTER);

        this.setLayout(new GridLayout(3, 1, 5, 5));
        this.add(pnForm);
        this.add(pnButton);
        this.add(pnTable);

    }

    public JTable getTblMonHoc() {
        return tblMonHoc;
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
        tblMonHoc.addMouseListener(a);

    }

    public MonHocModel getformdata() {

        String mamon = tfmamon.getText().trim();
        String tenmon = tftenmon.getText().trim();

        int sotinchi = 0;
        try {
            String textSotinchi = tfsotinchi.getText().trim();
            if (!textSotinchi.isEmpty()) {
                sotinchi = Integer.parseInt(textSotinchi);
            }
        } catch (Exception e) {
            sotinchi = 0;
        }

        return new MonHocModel(mamon, tenmon, sotinchi);
    }

    public void fillform(MonHocModel m) {
        if (m == null) {
            return;
        }

        tfmamon.setText(m.getMamon());
        tftenmon.setText(m.getTenmon());
        tfsotinchi.setText(String.valueOf(m.getSotinchi()));
    }

    public void clearform() {
        tfmamon.setText("");
        tftenmon.setText("");
        tfsotinchi.setText("");

    }

    public void loadtable(ArrayList<MonHocModel> d) {
        model.setRowCount(0);
        for (MonHocModel b : d) {

            Object[] dulieubang = {
                b.getMamon(),
                b.getTenmon(),
                b.getSotinchi()
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

