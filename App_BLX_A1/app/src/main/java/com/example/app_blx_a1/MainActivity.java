package com.example.app_blx_a1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.app_blx_a1.BienBao.View.BienBaoActivity;
import com.example.app_blx_a1.LuatXuPhat.View.LuatXuPhatActivity;
import com.example.app_blx_a1.OnTapCauHoi.View.OnTap;
import com.example.app_blx_a1.Thi.View.DanhSachDeThi;

public class MainActivity extends AppCompatActivity {
    LinearLayout layout_Luat,layout_TraCuuBienBao,layout_OnTap,layout_ThiThu,layout_Meo_Ghi_Nho,layout_Diem_Liet, layout_LichSuThi;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    void addEvents(){
        layout_Luat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LuatXuPhatActivity.class);
                startActivity(intent);
            }
        });
        layout_TraCuuBienBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BienBaoActivity.class);
                startActivity(intent);
            }
        });
        layout_ThiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, DanhSachDeThi.class);
                startActivity(intent);
            }
        });
        layout_OnTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, OnTap.class);
                startActivity(intent);
            }
        });
        layout_Meo_Ghi_Nho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, MeoGhiNho.class);
                startActivity(intent);
            }
        });
        layout_Diem_Liet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, DiemLiet.class);
                startActivity(intent);
            }
        });
        layout_LichSuThi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ShowLichSuThi.class);
                startActivity(intent);
            }
        });
    }

    void addControls() {
        layout_Luat = findViewById(R.id.layout_TraCuuLuat);
        layout_TraCuuBienBao = findViewById(R.id.layout_TraCuuBienBao);
        layout_ThiThu=findViewById(R.id.layout_ThiThu);
        layout_Meo_Ghi_Nho=findViewById(R.id.layout_MeoGhiNho);
        toolbar=findViewById(R.id.toolbar);
        layout_OnTap=findViewById(R.id.layout_OnTap);
        layout_Diem_Liet=findViewById(R.id.layout_DiemLiet);
        layout_LichSuThi=findViewById(R.id.layout_LichSuThi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }
}