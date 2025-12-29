/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import View.GiaoVienPanel;
import Model.GiaoVienModel;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author noqok
 */
public class GiaoVienController implements MouseListener, ActionListener {

    private GiaoVienPanel view;
    private GiaoVienModel model;
    int pos = 0;
    int check = 0;

    public GiaoVienController(GiaoVienPanel view, GiaoVienModel model) {
        this.view = view;
        this.model = model;
        view.on_off(true, false);

        view.addActionListerner(this);
        view.addTableMouseAction(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = view.getTblGiaoVien().getSelectedRow();
        if (row >= 0) {
            GiaoVienModel gv = model.getDs().get(row);
            view.fillform(gv);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private String validateInput(GiaoVienModel gv) {
        if (gv.getMagv() == null || gv.getMagv().trim().isEmpty()) {
            return "Mã giáo viên không được để trống!";
        }

        if (gv.getHoten() == null || gv.getHoten().trim().isEmpty()) {
            return "Họ tên không được để trống!";
        }

        if (gv.getEmail() != null && !gv.getEmail().trim().isEmpty()) {
            if (!gv.getEmail().contains("@") || !gv.getEmail().contains(".")) {
                return "Email không hợp lệ!";
            }
        }

        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Thêm")) {
            check = 1;
            view.clearform();
            view.on_off(false, true);

        } else if (command.equals("Sửa")) {
            if (view.getTblGiaoVien().getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng để sửa!");
                return;
            }
            check = 2;
            view.on_off(false, true);

        } else if (command.equals("Xóa")) {
            int row = view.getTblGiaoVien().getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng để xóa!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa?");
            if (confirm == JOptionPane.YES_OPTION) {
                String magv = view.getTblGiaoVien().getValueAt(row, 0).toString();

                if (model.deleteGiaoVien(magv)) {
                    JOptionPane.showMessageDialog(view, "Xóa thành công!");
                    view.loadtable(model.getDs());

                    view.clearform();
                } else {
                    JOptionPane.showMessageDialog(view, "Xóa thất bại!");
                }
            }
        } else if (command.equals("Xác nhận")) {
            GiaoVienModel gv = view.getformdata();

            String validationError = validateInput(gv);
            if (validationError != null) {
                JOptionPane.showMessageDialog(view, validationError);
                return;
            }

            if (check == 1) {
                if (model.insertGiaoVien(gv)) {
                    JOptionPane.showMessageDialog(view, "Thêm thành công!");
                } else {
                    JOptionPane.showMessageDialog(view, "Thêm thất bại!");
                }
            } else if (check == 2) {
                if (model.updateGiaoVien(gv)) {
                    JOptionPane.showMessageDialog(view, "Sửa thành công!");
                } else {
                    JOptionPane.showMessageDialog(view, "Sửa thất bại!");
                }
            }

            check = 0;
            view.loadtable(model.getDs());
            view.on_off(true, false);
            view.clearform();
        } else if (command.equals("Hủy")) {
            check = 0;
            view.clearform();
            view.on_off(true, false);
        } else if (command.equals("🔍")) {
            String keyword = view.getSearchKeyword();
            if (keyword.isEmpty()) {
                view.loadtable(model.getDs());
            } else {
                ArrayList<GiaoVienModel> kq = model.search(keyword);
                view.loadtable(kq);
            }
        }
    }

}

