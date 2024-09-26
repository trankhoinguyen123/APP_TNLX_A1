package com.example.app_blx_a1.Thi.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.app_blx_a1.R;
import com.example.app_blx_a1.Thi.Controller.DanhSachDeThiThuApdapter;
import com.example.app_blx_a1.Thi.Model.DanhSachDeThiThu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DanhSachDeThi extends AppCompatActivity {
    GridView gridView;
    public static String selectedDanhSachID;
    public static String selectedTenDanhSach;
    public static int selectSoCauHoi;
    DanhSachDeThiThuApdapter apdapter;
    public static List<DanhSachDeThiThu> listDanhSach = new ArrayList<>();
    FirebaseFirestore firebaseFirestore;
    ImageButton imgBack3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_de_thi);

        gridView = findViewById(R.id.danh_sach_de_thi);
        imgBack3=findViewById(R.id.imgBack3);
        firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference reference = firebaseFirestore.collection("DeThi");
        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot snapshots = task.getResult();
                    for (QueryDocumentSnapshot documentSnapshot : snapshots) {
                        DanhSachDeThiThu danhSachDeThiThu = new DanhSachDeThiThu();
                        danhSachDeThiThu.setDanhSachID(documentSnapshot.getId());
                        if (documentSnapshot.contains("tenDanhSach") && documentSnapshot.get("tenDanhSach") != null) {
                            danhSachDeThiThu.setTenDanhSach(documentSnapshot.get("tenDanhSach").toString());
                        } else {
                            danhSachDeThiThu.setTenDanhSach("Unknown");
                        }

                        if (documentSnapshot.contains("soCauHoi") && documentSnapshot.get("soCauHoi") != null) {
                            danhSachDeThiThu.setSoCauHoi(Integer.parseInt(documentSnapshot.get("soCauHoi").toString()));
                        } else {
                            danhSachDeThiThu.setSoCauHoi(0);
                        }

                        listDanhSach.add(danhSachDeThiThu);
                    }
                    apdapter = new DanhSachDeThiThuApdapter(listDanhSach);
                    gridView.setAdapter(apdapter);
                } else {
                    Toast.makeText(DanhSachDeThi.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        imgBack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DanhSachDeThi.this.finish();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedDanhSachID = listDanhSach.get(position).getDanhSachID();
                selectSoCauHoi=listDanhSach.get(position).getSoCauHoi();
                selectedTenDanhSach=listDanhSach.get(position).getTenDanhSach();
                Intent intent=new Intent(getApplicationContext(),CauHoiThi.class);
                startActivity(intent);

            }
        });
    }

}
