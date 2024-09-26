package com.example.app_blx_a1.Thi.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_blx_a1.R;
import com.example.app_blx_a1.Thi.Controller.LichSuThiAdapter;
import com.example.app_blx_a1.Thi.Controller.ShowQuestionsAdapter;
import com.example.app_blx_a1.Thi.Model.Questions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ChamDiemThi extends AppCompatActivity {
    TextView txtTongDiem, txtThoiGianThi, txtTongCauHoi, txtCorrect, txtFailed;
    FirebaseFirestore firebaseFirestore;
    ImageButton imgBack4;
    Button btnLuuLai, btnAnswer;
    List<String> answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cham_diem_thi);

        txtTongDiem = findViewById(R.id.txtTongDiem);
        txtThoiGianThi = findViewById(R.id.txtThoiGianThi);
        txtTongCauHoi = findViewById(R.id.txtTongCauHoi);
        txtCorrect = findViewById(R.id.txt_Correct);
        txtFailed = findViewById(R.id.txt_Failed);
        btnLuuLai = findViewById(R.id.btnThuLai);
        btnAnswer = findViewById(R.id.btn_ViewANSWER);
        imgBack4=findViewById(R.id.imgBack4);

        firebaseFirestore = FirebaseFirestore.getInstance();
        answers = new ArrayList<>();

        int soCauDung = getIntent().getIntExtra("SO_CAU_DUNG", 0);
        int soCauSai = getIntent().getIntExtra("SO_CAU_SAI", 0);
        long thoiGianThi = getIntent().getLongExtra("THOI_GIAN_THI", 0);
        int tongDiem = getIntent().getIntExtra("TONG_DIEM", 0);
        String danhSachID = getIntent().getStringExtra("DANH_SACH_ID");
        String time = String.format("%02d:%02d min",
                TimeUnit.MILLISECONDS.toMinutes(thoiGianThi),
                TimeUnit.MILLISECONDS.toSeconds(thoiGianThi) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(thoiGianThi)));

        txtThoiGianThi.setText(time);
        txtCorrect.setText(String.valueOf(soCauDung));
        txtFailed.setText(String.valueOf(soCauSai));
        txtTongDiem.setText(String.valueOf(tongDiem));

        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChamDiemThi.this, ShowDapAn.class);
                startActivity(intent);
            }
        });
        imgBack4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CauHoiThi.listQuestions.clear();
                ChamDiemThi.this.finish();
            }
        });
        btnLuuLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luuLichSuThi(danhSachID, time, soCauDung, soCauSai, tongDiem);

            }
        });

    }
    private void luuLichSuThi(String danhSachID, String time, int soCauDung, int soCauSai, int tongDiem) {
        Map<String, Object> lichSuThi = new HashMap<>();
        lichSuThi.put("thoiGianThi", time);
        lichSuThi.put("soCauDung", soCauDung);
        lichSuThi.put("soCauSai", soCauSai);
        lichSuThi.put("tongDiem", tongDiem);
        lichSuThi.put("danhSach_ID",danhSachID);

        // Lưu bản ghi vào collection bộ đề thi cụ thể
        firebaseFirestore.collection("LichSuThi")
                .add(lichSuThi)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(ChamDiemThi.this, "Lưu lịch sử thi thành công!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ChamDiemThi.this, "Lỗi khi lưu lịch sử thi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
