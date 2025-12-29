/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import View.SinhVienPanel;
import Model.SinhVienModel;
import java.util.ArrayList;

/**
 *
 * @author noqok
 */
public class SinhVienController implements MouseListener, ActionListener {

    private SinhVienPanel view;
    private SinhVienModel model;
    int pos = 0;
    int check = 0;

    public SinhVienController(SinhVienPanel view, SinhVienModel model) {
        this.view = view;
        this.model = model;
        view.on_off(true, false);

        view.addActionListerner(this);
        view.addTableMouseAction(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = view.getTblSinhVien().getSelectedRow();
        if (row >= 0) {
            String masv = view.getTblSinhVien().getValueAt(row, 0).toString();
            String hoten = view.getTblSinhVien().getValueAt(row, 1).toString();
            String ngaysinh = view.getTblSinhVien().getValueAt(row, 2) != null ? view.getTblSinhVien().getValueAt(row, 2).toString() : "";
            String gioitinh = view.getTblSinhVien().getValueAt(row, 3) != null ? view.getTblSinhVien().getValueAt(row, 3).toString() : "";
            String diachi = view.getTblSinhVien().getValueAt(row, 4) != null ? view.getTblSinhVien().getValueAt(row, 4).toString() : "";
            String malop = view.getTblSinhVien().getValueAt(row, 5) != null ? view.getTblSinhVien().getValueAt(row, 5).toString() : "";
            String username = view.getTblSinhVien().getValueAt(row, 6) != null ? view.getTblSinhVien().getValueAt(row, 6).toString() : "";
            SinhVienModel sv = new SinhVienModel(username, masv, hoten, ngaysinh, gioitinh, diachi, malop);
            view.fillform(sv);
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

    private String validateInput(SinhVienModel sv) {
        if (sv.getMasv() == null || sv.getMasv().trim().isEmpty()) {
            return "Mã sinh viên không được để trống!";
        }

        if (sv.getHoten() == null || sv.getHoten().trim().isEmpty()) {
            return "Họ tên không được để trống!";
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
            if (view.getTblSinhVien().getSelectedRow() < 0) {
                System.out.println("Vui lòng chọn dòng để sửa!");
                return;
            }
            check = 2;
            view.on_off(false, true);

        } else if (command.equals("Xóa")) {
            int row = view.getTblSinhVien().getSelectedRow();
            if (row < 0) {
                System.out.println("Vui lòng chọn dòng để xóa!");
                return;
            }
            System.out.println("Bạn có chắc muốn xóa?");
            String masv = view.getTblSinhVien().getValueAt(row, 0).toString();

            if (model.deleteSinhVien(masv)) {
                System.out.println("Xóa thành công!");
                view.loadtable(model.getDs());

                view.clearform();
            } else {
                System.out.println("Xóa thất bại!");
            }
        } else if (command.equals("Xác nhận")) {
            SinhVienModel sv = view.getformdata();

            String validationError = validateInput(sv);
            if (validationError != null) {
                System.out.println(validationError);
                return;
            }

            if (check == 1) {
                if (model.insertSinhVien(sv)) {
                    System.out.println("Thêm thành công!");
                } else {
                    System.out.println("Thêm thất bại!");
                }
            } else if (check == 2) {
                if (model.updateSinhVien(sv)) {
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
                ArrayList<SinhVienModel> kq = model.search(keyword);
                view.loadtable(kq);
            }
        }
    }

}

