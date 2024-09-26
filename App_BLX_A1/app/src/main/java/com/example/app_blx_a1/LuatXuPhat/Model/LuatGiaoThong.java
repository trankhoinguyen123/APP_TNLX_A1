package com.example.app_blx_a1.LuatXuPhat.Model;

import java.io.Serializable;
import java.util.List;

public class LuatGiaoThong implements Serializable {
    String nguoiDieuKhien;
    String moTaViPham;
    String mucPhat;
    String phatBoSung;
    List<HanhViHopNhat> hanhViHopNhatList;
    List<HanhViLienQuan> hanhViLienQuanList;

    String tenPhuongTien;
    String tenHanhVi;

    public LuatGiaoThong(String nguoiDieuKhien, String moTaViPham, String mucPhat, String phatBoSung, List<HanhViHopNhat> hanhViHopNhatList, List<HanhViLienQuan> hanhViLienQuanList, String tenPhuongTien, String tenHanhVi) {
        this.nguoiDieuKhien = nguoiDieuKhien;
        this.moTaViPham = moTaViPham;
        this.mucPhat = mucPhat;
        this.phatBoSung = phatBoSung;
        this.hanhViHopNhatList = hanhViHopNhatList;
        this.hanhViLienQuanList = hanhViLienQuanList;
        this.tenPhuongTien = tenPhuongTien;
        this.tenHanhVi = tenHanhVi;
    }

    public LuatGiaoThong() {

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

    public List<HanhViHopNhat> getHanhViHopNhatList() {
        return hanhViHopNhatList;
    }

    public void setHanhViHopNhatList(List<HanhViHopNhat> hanhViHopNhatList) {
        this.hanhViHopNhatList = hanhViHopNhatList;
    }

    public List<HanhViLienQuan> getHanhViLienQuanList() {
        return hanhViLienQuanList;
    }

    public void setHanhViLienQuanList(List<HanhViLienQuan> hanhViLienQuanList) {
        this.hanhViLienQuanList = hanhViLienQuanList;
    }

    public String getTenPhuongTien() {
        return tenPhuongTien;
    }

    public void setTenPhuongTien(String tenPhuongTien) {
        this.tenPhuongTien = tenPhuongTien;
    }

    public String getTenHanhVi() {
        return tenHanhVi;
    }

    public void setTenHanhVi(String tenHanhVi) {
        this.tenHanhVi = tenHanhVi;
    }
}
