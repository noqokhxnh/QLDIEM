package Controller;

import Model.LoginModel;
import Model.SinhVienModel;
import View.DoiMatKhau;
import View.ThongTinTaiKhoan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ThongTinTaiKhoanController implements ActionListener {

  private SinhVienModel model;
  private ThongTinTaiKhoan view;
  private String username;
  private DoiMatKhau doiMatKhauview;
  private LoginModel loginModel;
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("BTN_DOI_MAT_KHAU")) {
            showDoiMatKhauPanel();
        } else if (cmd.equals("BTN_XAC_NHAN")) {
            xacnhanDoiMatKhau();

        } else if (cmd.equals("BTN_HUY")) {
            huy();
        }

    }



    private void showDoiMatKhauPanel() {
        view.removeAll();
        this.doiMatKhauview = new DoiMatKhau();
        DoiMatKhau doiMatKhauPanel = new DoiMatKhau();
        this.doiMatKhauview = doiMatKhauPanel;
        doiMatKhauPanel.btnActionListener(this);
        view.setLayout(new BorderLayout());
        view.add(doiMatKhauPanel, BorderLayout.CENTER);
        view.revalidate();
        view.repaint();
    }

    public ThongTinTaiKhoanController(SinhVienModel model, ThongTinTaiKhoan view, String username) {
        this.model = model;
        this.view = view;
        this.username = username;
        this.loginModel = new LoginModel();
        view.setDoiMatKhauActionListener(this);
        
        try
        {
            getData();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private void getData() throws SQLException {
        SinhVienModel sinhVienModel = model.getData(username);
        if (sinhVienModel!=null){
            view.fillform(sinhVienModel);

        }
    }

private void xacnhanDoiMatKhau(){
        String mkmoi = doiMatKhauview.getTfmatkhaumoi().getText();
        String mkmoi2 = doiMatKhauview.getTfmaukhoimoi2().getText();

        if (mkmoi.isEmpty()){
            System.out.println("mat khau moi bi trong");
        }else if (mkmoi2.isEmpty()){
            System.out.println("mat khau moi 2 bi trong");
        }
        else if (mkmoi.equals(mkmoi2)){
            loginModel.DoiMatKhau(this.username, mkmoi);
            System.out.println("doi mat khau thanh cong");
            huy();
        }else{
            System.out.println("mat khau moi khong trung nhau");
        }

}

private void huy(){
    view.removeAll();
    view.display();
    view.setDoiMatKhauActionListener(this);
    try {
        getData();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    view.revalidate();
    view.repaint();
}


}
