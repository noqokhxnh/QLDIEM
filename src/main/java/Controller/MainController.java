package Controller;

import Model.DiemModel;
import Model.SinhVienModel;
import Model.KhoaModel;
import Model.MonHocModel;
import Model.LopModel;
import Model.GiaoVienModel;
import View.DiemPanel;
import View.KhoaPanel;
import View.MonHocPanel;
import View.LopPanel;
import View.GiaoVienPanel;
import View.MainLayout;
import View.ThongTinTaiKhoan;
import Controller.ThongTinTaiKhoanController;
import Controller.DiemController;
import Controller.KhoaController;
import Controller.MonHocController;
import Controller.LopController;
import Controller.GiaoVienController;

import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JPanel;

public class MainController {

    private MainLayout view;
    private String username;
    private int type;
 
    public MainController(MainLayout view, String username, int type) {
        this.view = view;
        this.username = username;
        this.type =  type;
        view.setVisible(true);



        if (type ==0){
            isAdmin();
        }else if (type==1){
            isTeacher();
        }else if (type ==2){
            isStudent();
        }


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

        LopPanel lopPanel = getLopPanelFromLayout();
        if (lopPanel != null) {
            LopModel lopModel = new LopModel();
            LopController lopController = new LopController(lopPanel, lopModel);
            lopPanel.loadtable(lopModel.getDs());
        }

        GiaoVienPanel giaoVienPanel = getGiaoVienPanelFromLayout();
        if (giaoVienPanel != null) {
            GiaoVienModel giaoVienModel = new GiaoVienModel();
            GiaoVienController giaoVienController = new GiaoVienController(giaoVienPanel, giaoVienModel);
            giaoVienPanel.loadtable(giaoVienModel.getDs());
        }

        KhoaPanel khoaPanel = getKhoaPanelFromLayout();
        if (khoaPanel != null) {
            KhoaModel khoaModel = new KhoaModel();
            KhoaController khoaController = new KhoaController(khoaPanel, khoaModel);
            khoaPanel.loadtable(khoaModel.getDs());
        }

        MonHocPanel monHocPanel = getMonHocPanelFromLayout();
        if (monHocPanel != null) {
            MonHocModel monHocModel = new MonHocModel();
            MonHocController monHocController = new MonHocController(monHocPanel, monHocModel);
            monHocPanel.loadtable(monHocModel.getDs());
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
        JPanel mainPanel = view.getMainPanel();
        
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof DiemPanel) {
                return (DiemPanel) comp;
            }
        }
        return null;
    }

    private LopPanel getLopPanelFromLayout() {
        JPanel mainPanel = view.getMainPanel();
        
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof LopPanel) {
                return (LopPanel) comp;
            }
        }
        return null;
    }

    private GiaoVienPanel getGiaoVienPanelFromLayout() {
        JPanel mainPanel = view.getMainPanel();
        
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof GiaoVienPanel) {
                return (GiaoVienPanel) comp;
            }
        }
        return null;
    }

    private KhoaPanel getKhoaPanelFromLayout() {
        JPanel mainPanel = view.getMainPanel();
        
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof KhoaPanel) {
                return (KhoaPanel) comp;
            }
        }
        return null;
    }

    private MonHocPanel getMonHocPanelFromLayout() {
        JPanel mainPanel = view.getMainPanel();
        
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof MonHocPanel) {
                return (MonHocPanel) comp;
            }
        }
        return null;
    }

    private void isAdmin(){


    }

    private void isStudent(){


    }

    private void isTeacher(){


    }


}
