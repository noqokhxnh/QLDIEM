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
import View.MainLayout;
import Controller.MainController;
import Model.DiemModel;
import View.DiemPanel;
import javax.swing.SwingUtilities;

public class testmainlayout {

    public static void main(String[] args) {

                DiemModel diemmodel = new DiemModel();
                DiemPanel diemview = new DiemPanel();
                DiemController diemControl = new DiemController(diemview, diemmodel);

                MainLayout mainLayout = new MainLayout();

                mainLayout.getMainPanel().add(diemview, "DIEM");

                MainController mainControl = new MainController(mainLayout);

                mainLayout.setVisible(true);

                // Load initial data
                diemview.loadtable(diemmodel.getDs());
           
    }
}