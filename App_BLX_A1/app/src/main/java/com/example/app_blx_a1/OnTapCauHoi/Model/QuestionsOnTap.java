package com.example.app_blx_a1.OnTapCauHoi.Model;

public class QuestionsOnTap {
    private String tencau;
    private String A;
    private String B;
    private String C;
    private String answer;
    private int selectdAns;
    private String img;

    public QuestionsOnTap(String tencau, String a, String b, String c, String answer, int selectdAns, String img) {
        this.tencau = tencau;
        A = a;
        B = b;
        C = c;
        this.answer = answer;
        this.selectdAns = selectdAns;
        this.img = img;
    }
public QuestionsOnTap()
{

}
    public String getTencau() {
        return tencau;
    }

    public void setTencau(String tencau) {
        this.tencau = tencau;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

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
}
