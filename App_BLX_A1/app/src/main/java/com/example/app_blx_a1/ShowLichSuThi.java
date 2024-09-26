package com.example.app_blx_a1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.example.app_blx_a1.Thi.Controller.LichSuThiAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShowLichSuThi extends AppCompatActivity {
    ListView listView_LichSu;
    LichSuThiAdapter adapter;
    FirebaseFirestore firebaseFirestore;
    ImageButton imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lich_su_thi);
        listView_LichSu=findViewById(R.id.listView_LichSu);
        imgBack=findViewById(R.id.imgBack7);
        firebaseFirestore=FirebaseFirestore.getInstance();

        // Khởi tạo danh sách lịch sử thi rỗng
        List<Map<String, Object>> lichSuThiList = new ArrayList<>();
        adapter = new LichSuThiAdapter(this, lichSuThiList);
        listView_LichSu.setAdapter(adapter);

        // Gọi phương thức để lấy dữ liệu lịch sử thi
        showLichSuThi();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowLichSuThi.this.finish();
            }
        });
    }

    private void showLichSuThi() {
        firebaseFirestore.collection("LichSuThi")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Map<String, Object>> lichSuThiList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            lichSuThiList.add(document.getData());
                        }
                        // Cập nhật dữ liệu cho adapter
                        adapter.updateData(lichSuThiList);
                    } else {
                        Toast.makeText(ShowLichSuThi.this, "Lỗi khi lấy dữ liệu lịch sử thi", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
