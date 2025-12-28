package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DoiMatKhau extends JPanel {

    private JLabel lblmatkhaucu;
    private JLabel lblmatkhaumoi;
    private JLabel lblmaukhaumoi2;

    private JTextField tfmatkhaucu;
    private JTextField tfmatkhaumoi;
    private JTextField tfmaukhoimoi2;

    private String username;
    private JButton btnXacNhan;
    private JButton btnHuy;
    public DoiMatKhau(){

        display();
    }

    public void display(){
        this.setSize(400, 350);

        lblmatkhaucu = new JLabel("Mật khẩu cũ");
        lblmatkhaumoi = new JLabel("Mật khẩu mới");
        lblmaukhaumoi2 = new JLabel("Nhập lại mật khẩu mới");

        tfmatkhaucu = new JTextField("");
        tfmatkhaumoi = new JTextField("");
        tfmaukhoimoi2 = new JTextField("");

        btnXacNhan = new JButton("Xác nhận");
        btnXacNhan.setActionCommand("BTN_XAC_NHAN");
        btnHuy = new JButton("Hủy");
        btnHuy.setActionCommand("BTN_HUY");


        JPanel pnmkc = new JPanel(new GridLayout(1,2,5,5));
        JPanel pnmkm = new JPanel(new GridLayout(1,2,5,5));
        JPanel pnmkm2 = new JPanel(new GridLayout(1,2,5,5));
        JPanel pnbtn = new JPanel(new GridLayout(1,2,5,5));

        pnmkc.add(lblmatkhaucu);
        pnmkc.add(tfmatkhaucu);

        pnmkm.add(lblmatkhaumoi);
        pnmkm.add(tfmatkhaumoi);

        pnmkm2.add(lblmaukhaumoi2);
        pnmkm2.add(tfmaukhoimoi2);

        pnbtn.add(btnXacNhan);
        pnbtn.add(btnHuy);

        this.setLayout(new GridLayout(4,2,5,5));
        this.add(pnmkc);
        this.add(pnmkm);
        this.add(pnmkm2);
        this.add(pnbtn);





    }
    public void btnActionListener(ActionListener a ){
        btnXacNhan.addActionListener(a);
        btnHuy.addActionListener(a);
    }

    public JButton getBtnXacNhan() {
        return btnXacNhan;
    }

    public JButton getBtnHuy() {
        return btnHuy;
    }

    public JTextField getTfmatkhaucu() {
        return tfmatkhaucu;
    }

    public JTextField getTfmatkhaumoi() {
        return tfmatkhaumoi;
    }

    public JTextField getTfmaukhoimoi2() {
        return tfmaukhoimoi2;
    }

    public void getTextField(){
        String matkhaucu = tfmatkhaucu.getText();
        String matkhaumoi = tfmatkhaumoi.getText();
        String matkhaumoi2 = tfmaukhoimoi2.getText();

    }

    public String getUsername() {
        return username;
    }
}
