package com.example.app_blx_a1.Thi.Model;

import com.example.app_blx_a1.OnTapCauHoi.Model.DanhSachOnTap;
import com.example.app_blx_a1.Thi.View.DanhSachDeThi;

import java.util.ArrayList;

public class DanhSachDeThiThu {
    private String danhSach_ID;
    private String tenDanhSach;
    private int soCauHoi;

    public DanhSachDeThiThu(String danhSach_ID, String tenDanhSach)
    {
        this.danhSach_ID=danhSach_ID;
        this.tenDanhSach=tenDanhSach;
    }

    public DanhSachDeThiThu(String tenDanhSach, int soCauHoi)
    {
        this.tenDanhSach=tenDanhSach;
        this.soCauHoi=soCauHoi;
    }
    public DanhSachDeThiThu()
    {

    }
    public DanhSachDeThiThu(String danhSach_ID, String tenDanhSach, int soCauHoi) {
        this.danhSach_ID = danhSach_ID;
        this.tenDanhSach = tenDanhSach;
        this.soCauHoi = soCauHoi;
    }

    public String getDanhSachID() {
        return danhSach_ID;
    }

    public void setDanhSachID(String danhSach_ID) {
        this.danhSach_ID = danhSach_ID;
    }

    public String getTenDanhSach() {
        return tenDanhSach;
    }

    public void setTenDanhSach(String tenDanhSach) {
        this.tenDanhSach = tenDanhSach;
    }

    public int getSoCauHoi() {
        return soCauHoi;
    }

    public void setSoCauHoi(int soCauHoi) {
        this.soCauHoi = soCauHoi;
    }
    public static ArrayList<DanhSachDeThiThu> loadDataDeThi()
    {
        ArrayList<DanhSachDeThiThu>list=new ArrayList<>();

        list.add(new DanhSachDeThiThu("1", "ĐỀ THI SỐ 1", 25));
        list.add(new DanhSachDeThiThu("2","ĐỀ THI SỐ 2",25));
        list.add(new DanhSachDeThiThu("3","ĐỀ THI SỐ 3",25));
        list.add(new DanhSachDeThiThu("4","ĐỀ THI SỐ 4",25));
        list.add(new DanhSachDeThiThu("5","ĐỀ THI SỐ 5",25));
        list.add(new DanhSachDeThiThu("6","ĐỀ THI SỐ 6",25));
        list.add(new DanhSachDeThiThu("7","ĐỀ THI SỐ 7",25));
        list.add(new DanhSachDeThiThu("","ĐỀ THI SỐ 8",25));
        return list;
    }
}
