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
            String malop = view.getTblLop().getValueAt(row, 0).toString();
            String tenlop = view.getTblLop().getValueAt(row, 1).toString();
            String makhoa = view.getTblLop().getValueAt(row, 2) != null ? view.getTblLop().getValueAt(row, 2).toString() : "";
            String magvcn = view.getTblLop().getValueAt(row, 3) != null ? view.getTblLop().getValueAt(row, 3).toString() : "";
            LopModel l = new LopModel(malop, tenlop, makhoa, magvcn);
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
                System.out.println("Vui lòng chọn dòng để sửa!");
                return;
            }
            check = 2;
            view.on_off(false, true);

        } else if (command.equals("Xóa")) {
            int row = view.getTblLop().getSelectedRow();
            if (row < 0) {
                System.out.println("Vui lòng chọn dòng để xóa!");
                return;
            }
            System.out.println("Bạn có chắc muốn xóa?");
            String malop = view.getTblLop().getValueAt(row, 0).toString();

            if (model.deleteLop(malop)) {
                System.out.println("Xóa thành công!");
                view.loadtable(model.getDs());

                view.clearform();
            } else {
                System.out.println("Xóa thất bại!");
            }
        } else if (command.equals("Xác nhận")) {
            LopModel l = view.getformdata();

            String validationError = validateInput(l);
            if (validationError != null) {
                System.out.println(validationError);
                return;
            }

            if (check == 1) {
                if (model.insertLop(l)) {
                    System.out.println("Thêm thành công!");
                } else {
                    System.out.println("Thêm thất bại!");
                }
            } else if (check == 2) {
                if (model.updateLop(l)) {
                    System.out.println("Sửa thành công!");
                } else {
                    System.out.println("Sửa thất bại!");
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

