/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;
/**
 *
 * @author noqok
 */

import Controller.DiemController;
import Controller.ThongTinTaiKhoanController;
import Model.SinhVienModel;
import View.MainLayout;
import Controller.MainController;
import Model.DiemModel;
import View.DiemPanel;
import View.ThongTinTaiKhoan;

import javax.swing.SwingUtilities;

public class testmainlayout {

    public static void main(String[] args) {

                DiemModel diemmodel = new DiemModel();
                DiemPanel diemview = new DiemPanel();
                DiemController diemControl = new DiemController(diemview, diemmodel);
                MainLayout mainLayout = new MainLayout();

        ThongTinTaiKhoan thongTinTaiKhoanview = new ThongTinTaiKhoan();
        SinhVienModel sinhVienModel = new SinhVienModel();
        ThongTinTaiKhoanController thongTinTaiKhoanController = new ThongTinTaiKhoanController(sinhVienModel,  thongTinTaiKhoanview, "sv001");

                mainLayout.getMainPanel().add(diemview, "DIEM");
                mainLayout.getMainPanel().add(thongTinTaiKhoanview, "TT");
                MainController mainControl = new MainController(mainLayout, "sv001");

                mainLayout.setVisible(true);

                diemview.loadtable(diemmodel.getDs());
           
    }
}