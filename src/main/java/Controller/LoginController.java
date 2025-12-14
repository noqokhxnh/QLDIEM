/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author noqok
 */
import Model.LoginModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import View.LoginView;
import View.MainLayout;

public class LoginController implements ActionListener, MouseListener {

    private LoginView view;
    private LoginModel model;


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String cmd = actionEvent.getActionCommand();
        if (cmd.equals("Đăng nhập")) {
            String username = view.getTxtUsername().getText();
            String password = new String(view.getTxtPassword().getPassword());

            if (model.checklogin(username, password)) {
                System.out.println(username+" da dang nhap");                
                view.dispose();
                MainLayout mainLayout = new MainLayout();
                MainController mainController = new MainController(mainLayout, username);

            } else {
                System.out.println("Dang nhap that bai");
            }

        }
  

    }

    public LoginController(LoginView view, LoginModel model) {
        this.model = model;
        this.view = view;
        this.view.addActionListener(this);
        this.view.getBtnShow().addMouseListener(this);
        this.view.getBtnClose().addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == view.getBtnShow()) {
            view.getTxtPassword().setEchoChar((char) 0);
            view.showPassword(false, true);
        } else if (e.getSource() == view.getBtnClose()) {
            view.getTxtPassword().setEchoChar('•');
            view.showPassword(true, false);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

}
