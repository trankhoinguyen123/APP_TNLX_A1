package com.example.app_blx_a1.Thi.Model;

public class DeThi {
    private String Dethi_id;
    private String Dethi_time;

    public String getDethi_id() {
        return Dethi_id;
    }

    public void setDethi_id(String dethi_id) {
        Dethi_id = dethi_id;
    }

    public String getDethi_time() {
        return Dethi_time;
    }

    public void setDethi_time(String dethi_time) {
        Dethi_time = dethi_time;
    }

    public DeThi(String dethi_id, String dethi_time) {
        Dethi_id = dethi_id;
        Dethi_time = dethi_time;
    }
}
