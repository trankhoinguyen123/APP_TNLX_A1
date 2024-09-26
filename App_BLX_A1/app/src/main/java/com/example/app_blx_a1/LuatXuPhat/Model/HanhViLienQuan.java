package com.example.app_blx_a1.LuatXuPhat.Model;

public class HanhViLienQuan {
    String nguoiDieuKhien;
    String moTaViPham;
    String mucPhat;

    public HanhViLienQuan(String nguoiDieuKhien, String moTaViPham, String mucPhat) {
        this.nguoiDieuKhien = nguoiDieuKhien;
        this.moTaViPham = moTaViPham;
        this.mucPhat = mucPhat;
    }

    public HanhViLienQuan(){

    }

    public String getNguoiDieuKhien() {
        return nguoiDieuKhien;
    }

    public void setNguoiDieuKhien(String nguoiDieuKhien) {
        this.nguoiDieuKhien = nguoiDieuKhien;
    }

    public String getMoTaViPham() {
        return moTaViPham;
    }

    public void setMoTaViPham(String moTaViPham) {
        this.moTaViPham = moTaViPham;
    }

    public String getMucPhat() {
        return mucPhat;
    }

    public void setMucPhat(String mucPhat) {
        this.mucPhat = mucPhat;
    }
}
