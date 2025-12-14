package Controller;

import Model.SinhVienModel;
import View.ThongTinTaiKhoan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ThongTinTaiKhoanController implements ActionListener {

  private SinhVienModel model;
  private ThongTinTaiKhoan view;
  private String username;

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public ThongTinTaiKhoanController(SinhVienModel model, ThongTinTaiKhoan view, String username) {
        this.model = model;
        this.view = view;
        this.username = username;
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


}
