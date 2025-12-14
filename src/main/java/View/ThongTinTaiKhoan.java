package View;

import Model.SinhVienModel;

import javax.swing.*;
import java.awt.*;

public class ThongTinTaiKhoan extends JPanel {

    private JLabel lblUsername;
    private JLabel lblMaSv;
    private JLabel lblHoTen;
    private JLabel lblNgaySinh;
    private JLabel lblGioiTinh;
    private JLabel lblDiaChi;
    private JLabel lblMaLop;

    private JLabel txtUsername;
    private JLabel txtMaSv;
    private JLabel txtHoTen;
    private JLabel txtNgaySinh;
    private JLabel txtGioiTinh;
    private JLabel txtDiaChi;
    private JLabel txtMaLop;

    private JButton btnDoiMatKhau;
    private JButton btnDangXuat;


    public ThongTinTaiKhoan(){
        display();
    }

    private void display(){
        this.setSize(900, 750);
        lblUsername = new JLabel("Tên tài khoản");
        lblMaSv = new JLabel("Mã sinh viên");
        lblHoTen = new JLabel("Họ tên");
        lblNgaySinh = new JLabel("Ngày sinh");
        lblGioiTinh = new JLabel("Giới tính");
        lblDiaChi = new JLabel("Địa chỉ");
        lblMaLop = new JLabel("Mã Lớp");

        txtUsername = new JLabel("");
        txtMaSv = new JLabel("");
        txtHoTen =new JLabel("");
        txtDiaChi = new JLabel("");
        txtGioiTinh = new JLabel("");
        txtNgaySinh = new JLabel("");
        txtMaLop = new JLabel("");

        btnDoiMatKhau = new JButton("Đổi mật khẩu");
        btnDangXuat = new JButton("Đăng xuất");

        JPanel pnun = new JPanel(new GridLayout(1,2,5,5));
        JPanel pnsv = new JPanel(new GridLayout(1,2,5,5));
        JPanel pnht = new JPanel(new GridLayout(1,2,5,5));
        JPanel pnns = new JPanel(new GridLayout(1,2,5,5));
        JPanel pngt = new JPanel(new GridLayout(1,2,5,5));
        JPanel pndc = new JPanel(new GridLayout(1,2,5,5));
        JPanel pnml = new JPanel(new GridLayout(1,2,5,5));
        JPanel pnbt = new JPanel(new GridLayout(1,2,5,5));


        pnun.add(lblUsername);
        pnun.add(txtUsername);

        pnsv.add(lblMaSv);
        pnsv.add(txtMaSv);

        pnht.add(lblHoTen);
        pnht.add(txtHoTen);

        pnns.add(lblNgaySinh);
        pnns.add(txtNgaySinh);

        pngt.add(lblGioiTinh);
        pngt.add(txtGioiTinh);

        pndc.add(lblDiaChi);
        pndc.add(txtDiaChi);

        pnml.add(lblMaLop);
        pnml.add(txtMaLop);

        pnbt.add(btnDoiMatKhau);
        pnbt.add(btnDangXuat);
        
        this.setLayout(new GridLayout(8,2,0,0));

        this.add(pnun);
        this.add(pnsv);
        this.add(pnht);
        this.add(pnns);
        this.add(pngt);
        this.add(pndc);
        this.add(pnml);
        this.add(pnbt);

    }

    public void fillform(SinhVienModel model){
        txtMaSv.setText(model.getMasv());
        txtHoTen.setText(model.getHoten());
        txtNgaySinh.setText(model.getNgaysinh());
        txtGioiTinh.setText(model.getGioitinh());
        txtDiaChi.setText(model.getDiachi());
        txtMaLop.setText(model.getMalop());
        txtUsername.setText(model.getUsername());
    }

}
