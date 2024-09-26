package com.example.app_blx_a1.LuatXuPhat.View;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_blx_a1.LuatXuPhat.Controller.AdapterXuPhat;
import com.example.app_blx_a1.LuatXuPhat.Model.LuatGiaoThong;
import com.example.app_blx_a1.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DanhSachXuPhatHanhViActivity extends AppCompatActivity {
    RecyclerView recyclerViewHanhVi;
    AdapterXuPhat adapterXuPhat;
    SearchView searchViewDanhSach;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danh_sach_xu_phat_hanh_vi);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        recyclerViewHanhVi = findViewById(R.id.recyclerViewHanhVi);
        searchViewDanhSach = findViewById(R.id.searchViewDanhSach);
        recyclerViewHanhVi.setLayoutManager(new LinearLayoutManager(this));

        // Nhận JSON từ Intent
        String luatGiaoThongJson = getIntent().getStringExtra("luatGiaoThongListJson");
        String searchQuery = getIntent().getStringExtra("searchQuery");  // Nhận giá trị tìm kiếm


        // Chuyển đổi JSON thành đối tượng
        Gson gson = new Gson();
        Type listType = new TypeToken<List<LuatGiaoThong>>() {}.getType();
        List<LuatGiaoThong> luatGiaoThongList = gson.fromJson(luatGiaoThongJson, listType);

        adapterXuPhat = new AdapterXuPhat(luatGiaoThongList,DanhSachXuPhatHanhViActivity.this);
        recyclerViewHanhVi.setAdapter(adapterXuPhat);

        // Thêm DividerItemDecoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.custom_divider);
        if (drawable != null) {
            dividerItemDecoration.setDrawable(drawable);
        }

        recyclerViewHanhVi.addItemDecoration(dividerItemDecoration);

        // Thiết lập giá trị tìm kiếm cho SearchView
        if (searchQuery != null && !searchQuery.isEmpty()) {
            searchViewDanhSach.setQuery(searchQuery, false);
        }

        // Thiết lập listener cho SearchView
        searchViewDanhSach.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterXuPhat.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterXuPhat.getFilter().filter(newText);
                return false;
            }
        });
    }


}