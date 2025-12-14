package Controller;

import Model.DiemModel;
import Model.SinhVienModel;
import View.DiemPanel;
import View.MainLayout;
import View.ThongTinTaiKhoan;

import java.awt.CardLayout;

public class MainController {

    private MainLayout view;
    private String username;

    public MainController(MainLayout view, String username) {
        this.view = view;
        this.username = username;
        view.setVisible(true);

        ThongTinTaiKhoan viewThongTinTaiKhoan = new ThongTinTaiKhoan();
        view.getMainPanel().add(viewThongTinTaiKhoan,"TT");
        SinhVienModel sinhVienModel = new SinhVienModel();
        ThongTinTaiKhoanController thongTinTaiKhoanController = new ThongTinTaiKhoanController(sinhVienModel, viewThongTinTaiKhoan, username);



        initController();

    }

    private void initController() {

        view.onSinhVienClick(e -> show("SV"));
        view.onLopClick(e -> show("LOP"));
        view.onDiemClick(e -> show("DIEM"));
        view.onGVClick(e -> show("GV"));
        view.onKhoaClick(e -> show("KHOA"));
        view.onMonHocClick(e -> show("MONHOC"));
        view.onTTClick(e-> show("TT"));
    }

    private void show(String name) {
        CardLayout cl = view.getCardLayout();
        cl.show(view.getMainPanel(), name);
    }
}
