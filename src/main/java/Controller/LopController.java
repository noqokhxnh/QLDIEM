/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import View.LopPanel;
import Model.LopModel;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author noqok
 */
public class LopController implements MouseListener, ActionListener {

    private LopPanel view;
    private LopModel model;
    int pos = 0;
    int check = 0;

    public LopController(LopPanel view, LopModel model) {
        this.view = view;
        this.model = model;
        view.on_off(true, false);

        view.addActionListerner(this);
        view.addTableMouseAction(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = view.getTblLop().getSelectedRow();
        if (row >= 0) {
            LopModel l = model.getDs().get(row);
            view.fillform(l);
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

    private String validateInput(LopModel l) {
        if (l.getMalop() == null || l.getMalop().trim().isEmpty()) {
            return "Mã lớp không được để trống!";
        }

        if (l.getTenlop() == null || l.getTenlop().trim().isEmpty()) {
            return "Tên lớp không được để trống!";
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
            if (view.getTblLop().getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng để sửa!");
                return;
            }
            check = 2;
            view.on_off(false, true);

        } else if (command.equals("Xóa")) {
            int row = view.getTblLop().getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng để xóa!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa?");
            if (confirm == JOptionPane.YES_OPTION) {
                String malop = view.getTblLop().getValueAt(row, 0).toString();

                if (model.deleteLop(malop)) {
                    JOptionPane.showMessageDialog(view, "Xóa thành công!");
                    view.loadtable(model.getDs());

                    view.clearform();
                } else {
                    JOptionPane.showMessageDialog(view, "Xóa thất bại!");
                }
            }
        } else if (command.equals("Xác nhận")) {
            LopModel l = view.getformdata();

            String validationError = validateInput(l);
            if (validationError != null) {
                JOptionPane.showMessageDialog(view, validationError);
                return;
            }

            if (check == 1) {
                if (model.insertLop(l)) {
                    JOptionPane.showMessageDialog(view, "Thêm thành công!");
                } else {
                    JOptionPane.showMessageDialog(view, "Thêm thất bại!");
                }
            } else if (check == 2) {
                if (model.updateLop(l)) {
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
                ArrayList<LopModel> kq = model.search(keyword);
                view.loadtable(kq);
            }
        }
    }

}

