package com.example.app_blx_a1.OnTapCauHoi.Model;

import com.example.app_blx_a1.Thi.View.DanhSachDeThi;

import java.util.ArrayList;

public class DanhSachOnTap {
    private String loaicau;
    private String Ontap_id;
    private int socau;
    public DanhSachOnTap()
    {

    }
    public String getLoaicau() {
        return loaicau;
    }

    public void setLoaicau(String loaicau) {
        this.loaicau = loaicau;
    }

    public String getOntap_id() {
        return Ontap_id;
    }

    public void setOntap_id(String ontap_id) {
        Ontap_id = ontap_id;
    }

    public int getSocau() {
        return socau;
    }

    public void setSocau(int socau) {
        this.socau = socau;
    }

    public DanhSachOnTap(String loaicau, String ontap_id, int socau) {
        this.loaicau = loaicau;
        Ontap_id = ontap_id;
        this.socau = socau;
    }
}
