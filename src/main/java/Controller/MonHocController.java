/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import View.MonHocPanel;
import Model.MonHocModel;
import java.util.ArrayList;

/**
 *
 * @author noqok
 */
public class MonHocController implements MouseListener, ActionListener {

    private MonHocPanel view;
    private MonHocModel model;
    int pos = 0;
    int check = 0;

    public MonHocController(MonHocPanel view, MonHocModel model) {
        this.view = view;
        this.model = model;
        view.on_off(true, false);

        view.addActionListerner(this);
        view.addTableMouseAction(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = view.getTblMonHoc().getSelectedRow();
        if (row >= 0) {
            MonHocModel m = model.getDs().get(row);
            view.fillform(m);
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

    private String validateInput(MonHocModel m) {
        if (m.getMamon() == null || m.getMamon().trim().isEmpty()) {
            return "Mã môn học không được để trống!";
        }

        if (m.getTenmon() == null || m.getTenmon().trim().isEmpty()) {
            return "Tên môn học không được để trống!";
        }

        if (m.getSotinchi() <= 0) {
            return "Số tín chỉ phải lớn hơn 0!";
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
            if (view.getTblMonHoc().getSelectedRow() < 0) {
                System.out.println("Vui lòng chọn dòng để sửa!");
                return;
            }
            check = 2;
            view.on_off(false, true);

        } else if (command.equals("Xóa")) {
            int row = view.getTblMonHoc().getSelectedRow();
            if (row < 0) {
                System.out.println("Vui lòng chọn dòng để xóa!");
                return;
            }
            System.out.println("Bạn có chắc muốn xóa?");
            String mamon = view.getTblMonHoc().getValueAt(row, 0).toString();

            if (model.deleteMonHoc(mamon)) {
                System.out.println("Xóa thành công!");
                view.loadtable(model.getDs());

                view.clearform();
            } else {
                System.out.println("Xóa thất bại!");
            }
        } else if (command.equals("Xác nhận")) {
            MonHocModel m = view.getformdata();

            String validationError = validateInput(m);
            if (validationError != null) {
                System.out.println(validationError);
                return;
            }

            if (check == 1) {
                if (model.insertMonHoc(m)) {
                    System.out.println("Thêm thành công!");
                } else {
                    System.out.println("Thêm thất bại!");
                }
            } else if (check == 2) {
                if (model.updateMonHoc(m)) {
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
                ArrayList<MonHocModel> kq = model.search(keyword);
                view.loadtable(kq);
            }
        }
    }

}

