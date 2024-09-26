package com.example.app_blx_a1.Thi.Model;

public class Questions {
    private String cauhoi;
    private String A;
    private String B;
    private String C;
    private int cautraloi;
    private int selectdAns;
    private String img;

    public int getSelectdAns() {
        return selectdAns;
    }

    public void setSelectdAns(int selectdAns) {
        this.selectdAns = selectdAns;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Questions(String cauhoi, String a, String b, String c, int cautraloi,String img, int selectdAns) {
        this.cauhoi = cauhoi;
        A = a;
        B = b;
        C = c;
        this.cautraloi = cautraloi;
        this.img=img;
        this.selectdAns=selectdAns;
    }
public Questions()
{

}
    public String getCauhoi() {
        return cauhoi;
    }

    public void setCauhoi(String cauhoi) {
        this.cauhoi = cauhoi;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public int getCautraloi() {
        return cautraloi;
    }

    public void setCautraloi(int cautraloi) {
        this.cautraloi = cautraloi;
    }
}
