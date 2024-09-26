package com.example.app_blx_a1.LuatXuPhat.Model;

public class HanhViHopNhat {
    String nguoiDieuKhien;
    String moTaViPham;
    String mucPhat;
    String phatBoSung;

    public HanhViHopNhat(String nguoiDieuKhien, String moTaViPham, String mucPhat, String phatBoSung) {
        this.nguoiDieuKhien = nguoiDieuKhien;
        this.moTaViPham = moTaViPham;
        this.mucPhat = mucPhat;
        this.phatBoSung = phatBoSung;
    }

    public HanhViHopNhat(){

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

    public String getPhatBoSung() {
        return phatBoSung;
    }

    public void setPhatBoSung(String phatBoSung) {
        this.phatBoSung = phatBoSung;
    }
}
