package com.example.app_blx_a1.BienBao.Model;

public class BienBao {
    String ten_bien;
    String mo_ta;
    String hinh_anh;
    boolean isPinned;
    public BienBao(String ten_bien, String mo_ta, String hinh_anh) {
        this.ten_bien = ten_bien;
        this.mo_ta = mo_ta;
        this.hinh_anh = hinh_anh;
        this.isPinned = false;
    }

    public BienBao() {

    }
    // Getter và Setter cho isPinned
    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public String getTen_bien() {
        return ten_bien;
    }

    public void setTen_bien(String ten_bien) {
        this.ten_bien = ten_bien;
    }

    public String getMo_ta() {
        return mo_ta;
    }

    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public String getHinh_anh() {
        return hinh_anh;
    }

    public void setHinh_anh(String hinh_anh) {
        this.hinh_anh = hinh_anh;
    }
    public String getId() {
        return ten_bien; // Giả sử ten_bien là duy nhất cho mỗi biển báo
    }
}
