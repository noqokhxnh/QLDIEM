package Controller;

import View.MainLayout;
import java.awt.CardLayout;

public class MainController {

    private MainLayout view;

    public MainController(MainLayout view) {
        this.view = view;
        initController();
    }

    private void initController() {

        view.onSinhVienClick(e -> show("SV"));
        view.onLopClick(e -> show("LOP"));
        view.onDiemClick(e -> show("DIEM"));
        view.onGVClick(e -> show("GV"));
        view.onKhoaClick(e -> show("KHOA"));
        view.onMonHocClick(e -> show("MONHOC"));
    }

    private void show(String name) {
        CardLayout cl = view.getCardLayout();
        cl.show(view.getMainPanel(), name);
    }
}
