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
import Model.LopModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.table.TableModel;

/**
 *
 * @author noqok
 */
public class LopPanel extends JPanel {

    private LopModel l;
    private JLabel lblmalop;
    private JLabel lbltenlop;
    private JLabel lblmakhoa;
    private JLabel lblmagvcn;
    private JLabel lblsearch;

    private JTextField tfmalop;
    private JTextField tftenlop;
    private JTextField tfmakhoa;
    private JTextField tfmagvcn;
    private JTextField tfsearch;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnXacnhan;
    private JButton btnHuy;
    private JButton btnSearch;

    private JTable tblLop;
    private DefaultTableModel model;

    public LopPanel() {

        Display();
        ArrayList<LopModel> ds = new ArrayList<>();
        loadtable(ds);
    }

    public void Display() {

        this.setSize(900, 750);

        lblmalop = new JLabel("Mã Lớp");
        lbltenlop = new JLabel("Tên Lớp");
        lblmakhoa = new JLabel("Mã Khoa");
        lblmagvcn = new JLabel("Mã GV Chủ Nhiệm");
        lblsearch = new JLabel("Tìm Kiếm");
        tfmalop = new JTextField(20);
        tftenlop = new JTextField(20);
        tfmakhoa = new JTextField(20);
        tfmagvcn = new JTextField(20);
        tfsearch = new JTextField(20);

        JPanel pnmalop = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pntenlop = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pnmakhoa = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pnmagvcn = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel pns = new JPanel(new GridLayout(1, 3, 5, 5));
        pnmalop.add(lblmalop);
        pnmalop.add(tfmalop);
        pntenlop.add(lbltenlop);
        pntenlop.add(tftenlop);
        pnmakhoa.add(lblmakhoa);
        pnmakhoa.add(tfmakhoa);
        pnmagvcn.add(lblmagvcn);
        pnmagvcn.add(tfmagvcn);
        pns.add(lblsearch);
        pns.add(tfsearch);
        btnSearch = new JButton("🔍");
        pns.add(btnSearch);
        JPanel pnForm = new JPanel(new GridLayout(5, 1, 5, 5));
        pnForm.add(pnmalop);
        pnForm.add(pntenlop);
        pnForm.add(pnmakhoa);
        pnForm.add(pnmagvcn);
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
            "Mã Lớp", "Tên Lớp", "Mã Khoa", "Mã GVCN"
        };
        model = new DefaultTableModel(tencot, 0);
        tblLop = new JTable(model);

        JPanel pnTable = new JPanel(new BorderLayout());
        pnTable.add(tblLop.getTableHeader(), BorderLayout.NORTH);
        pnTable.add(tblLop, BorderLayout.CENTER);

        this.setLayout(new GridLayout(3, 1, 5, 5));
        this.add(pnForm);
        this.add(pnButton);
        this.add(pnTable);

    }

    public JTable getTblLop() {
        return tblLop;
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
        tblLop.addMouseListener(a);

    }

    public LopModel getformdata() {

        String malop = tfmalop.getText().trim();
        String tenlop = tftenlop.getText().trim();
        String makhoa = tfmakhoa.getText().trim();
        String magvcn = tfmagvcn.getText().trim();

        return new LopModel(malop, tenlop, makhoa, magvcn);
    }

    public void fillform(LopModel l) {
        if (l == null) {
            return;
        }

        tfmalop.setText(l.getMalop());
        tftenlop.setText(l.getTenlop());
        tfmakhoa.setText(l.getMakhoa());
        tfmagvcn.setText(l.getMagvcn());
    }

    public void clearform() {
        tfmalop.setText("");
        tftenlop.setText("");
        tfmakhoa.setText("");
        tfmagvcn.setText("");

    }

    public void loadtable(ArrayList<LopModel> d) {
        model.setRowCount(0);
        for (LopModel b : d) {

            Object[] dulieubang = {
                b.getMalop(),
                b.getTenlop(),
                b.getMakhoa(),
                b.getMagvcn()
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

