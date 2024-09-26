package com.example.app_blx_a1.LuatXuPhat.View;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_blx_a1.LuatXuPhat.Controller.HanhViHopNhatAdapter;
import com.example.app_blx_a1.LuatXuPhat.Controller.HanhViLienQuanAdapter;
import com.example.app_blx_a1.LuatXuPhat.Model.HanhViHopNhat;
import com.example.app_blx_a1.LuatXuPhat.Model.HanhViLienQuan;
import com.example.app_blx_a1.LuatXuPhat.Model.LuatGiaoThong;
import com.example.app_blx_a1.R;
import com.google.gson.Gson;

import java.util.List;

public class LuatXuPhatDetailActivity extends AppCompatActivity {
    TextView txtNguoiDieuKhien, txtMoTaHanhVi, txtMucPhat, txtPhatBoSung, txtHanhViHopNhatDetail, txtHanhViLienQuanDetail;
    RecyclerView hanhViHopNhatRecyclerView, hanhViLienQuanRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_luat_xu_phat_detail);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            int currentPaddingLeft = v.getPaddingLeft();
//            int currentPaddingTop = v.getPaddingTop();
//            int currentPaddingRight = v.getPaddingRight();
//            int currentPaddingBottom = v.getPaddingBottom();
//            v.setPadding(
//                    currentPaddingLeft + systemBars.left,
//                    currentPaddingTop + systemBars.top,
//                    currentPaddingRight + systemBars.right,
//                    currentPaddingBottom + systemBars.bottom
//            );
//            return insets;
//        });
        addControls();

        String luatGiaoThongJson = getIntent().getStringExtra("luatGiaoThong");
        Gson gson = new Gson();
        LuatGiaoThong luatGiaoThong = gson.fromJson(luatGiaoThongJson, LuatGiaoThong.class);
        if (luatGiaoThong != null) {
            txtNguoiDieuKhien.setText(luatGiaoThong.getNguoiDieuKhien());
            txtMoTaHanhVi.setText(luatGiaoThong.getMoTaViPham());
            txtMucPhat.setText(luatGiaoThong.getMucPhat());
            String phatBoSung = luatGiaoThong.getPhatBoSung();
            if (phatBoSung == null || phatBoSung.isEmpty()) {
                txtPhatBoSung.setVisibility(View.GONE);
            } else {
                txtPhatBoSung.setText(phatBoSung);
            }

            List<HanhViLienQuan> hanhViLienQuanList = luatGiaoThong.getHanhViLienQuanList();
            List<HanhViHopNhat> hanhViHopNhatList = luatGiaoThong.getHanhViHopNhatList();

            updateRecyclerView(hanhViHopNhatRecyclerView, hanhViHopNhatList, true);
            updateRecyclerView(hanhViLienQuanRecyclerView, hanhViLienQuanList, false);
        }
    }

    private void updateRecyclerView(RecyclerView recyclerView, List<?> dataList, boolean isHanhViHopNhat) {
        if (dataList == null || dataList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            if (isHanhViHopNhat) {
                txtHanhViHopNhatDetail.setVisibility(View.GONE);
            } else {
                txtHanhViLienQuanDetail.setVisibility(View.GONE);
            }
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            if (isHanhViHopNhat) {
                txtHanhViHopNhatDetail.setVisibility(View.VISIBLE);
                HanhViHopNhatAdapter adapter = new HanhViHopNhatAdapter(this, (List<HanhViHopNhat>) dataList);
                recyclerView.setAdapter(adapter);
            } else {
                txtHanhViLienQuanDetail.setVisibility(View.VISIBLE);
                HanhViLienQuanAdapter adapter = new HanhViLienQuanAdapter(this, (List<HanhViLienQuan>) dataList);
                recyclerView.setAdapter(adapter);
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            int verticalMargin = 10; // Set margin value here
            recyclerView.addItemDecoration(new CustomDividerItemDecoration(this, R.drawable.divider, verticalMargin));
        }
    }

    void addControls() {
        txtNguoiDieuKhien = findViewById(R.id.txtNguoiDieuKhienDetail);
        txtMoTaHanhVi = findViewById(R.id.txtMoTaHanhViDetail);
        txtMucPhat = findViewById(R.id.txtMucPhatDetail);
        txtPhatBoSung = findViewById(R.id.txtPhatBoSungDetail);

        hanhViHopNhatRecyclerView = findViewById(R.id.hanhViHopNhatRecyclerView);
        hanhViLienQuanRecyclerView = findViewById(R.id.hanhViLienQuanRecyclerView);

        txtHanhViHopNhatDetail = findViewById(R.id.txtHanhViHopNhatDetail);
        txtHanhViLienQuanDetail = findViewById(R.id.txtHanhViLienQuanDetail);
    }
}
