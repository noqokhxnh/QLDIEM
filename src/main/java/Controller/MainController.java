package Controller;

import Model.DiemModel;
import Model.SinhVienModel;
import View.DiemPanel;
import View.MainLayout;
import View.ThongTinTaiKhoan;
import Controller.ThongTinTaiKhoanController;
import Controller.DiemController;

import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JPanel;

public class MainController {

    private MainLayout view;
    private String username;
 
    public MainController(MainLayout view, String username) {
        this.view = view;
        this.username = username;
        view.setVisible(true);

        // Khởi tạo ThongTinTaiKhoan với username
        ThongTinTaiKhoan viewThongTinTaiKhoan = new ThongTinTaiKhoan();
        view.getMainPanel().add(viewThongTinTaiKhoan,"TT");
        SinhVienModel sinhVienModel = new SinhVienModel();
        ThongTinTaiKhoanController thongTinTaiKhoanController = new ThongTinTaiKhoanController(sinhVienModel, viewThongTinTaiKhoan, username);

        DiemPanel diemPanel = getDiemPanelFromLayout();
        if (diemPanel != null) {
            DiemModel diemModel = new DiemModel();
            DiemController diemController = new DiemController(diemPanel, diemModel);
            diemPanel.loadtable(diemModel.getDs());
        }

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

    private DiemPanel getDiemPanelFromLayout() {
        CardLayout cl = view.getCardLayout();
        JPanel mainPanel = view.getMainPanel();
        
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof DiemPanel) {
                return (DiemPanel) comp;
            }
        }
        return null;
    }
}
