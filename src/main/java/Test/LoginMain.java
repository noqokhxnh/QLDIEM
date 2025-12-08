/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import Controller.LoginController;
import View.LoginView;
import Model.LoginModel;
/**
 *
 * @author noqok
 */
public class LoginMain {
     public static void main(String[] args) {
        LoginView lgview = new LoginView();
        LoginModel lgmodel = new LoginModel();
        LoginController lgcontrol = new LoginController(lgview, lgmodel);
        lgview.setVisible(true);



    }
}
