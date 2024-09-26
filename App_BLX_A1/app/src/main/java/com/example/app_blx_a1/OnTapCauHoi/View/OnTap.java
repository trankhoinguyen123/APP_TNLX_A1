package com.example.app_blx_a1.OnTapCauHoi.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.app_blx_a1.LuatXuPhat.Model.LuatGiaoThong;
import com.example.app_blx_a1.LuatXuPhat.View.DanhSachXuPhatHanhViActivity;
import com.example.app_blx_a1.LuatXuPhat.View.LuatXuPhatActivity;
import com.example.app_blx_a1.OnTapCauHoi.Model.DanhSachOnTap;
//import com.example.app_blx_a1.OnTapCauHoi.Model.DanhSachOnTapApdapter;
import com.example.app_blx_a1.OnTapCauHoi.Model.DanhSachOnTapApdapter;
import com.example.app_blx_a1.OnTapCauHoi.Model.QuestionsOnTap;
import com.example.app_blx_a1.OnTapCauHoi.Model.QuestionsOnTapAdapter;
import com.example.app_blx_a1.R;
import com.example.app_blx_a1.Thi.Controller.DanhSachDeThiThuApdapter;
import com.example.app_blx_a1.Thi.Model.DanhSachDeThiThu;
import com.example.app_blx_a1.Thi.View.CauHoiThi;
import com.example.app_blx_a1.Thi.View.DanhSachDeThi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class OnTap extends AppCompatActivity {
GridView gridView;
List<DanhSachOnTap>listOnTap=new ArrayList<>();
FirebaseFirestore firebaseFirestore;
DanhSachOnTapApdapter apdapter;
ImageButton btnBack2;
List<QuestionsOnTap>list=new ArrayList<>();
public static String getSelectId;
//SearchView searchCauHoiOnTap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_tap);
        gridView=findViewById(R.id.danh_sach_on_tap);
//        searchCauHoiOnTap=findViewById(R.id.searchCauHoiOnTap);
        btnBack2=findViewById(R.id.imgBack2);

        firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference reference = firebaseFirestore.collection("OnTap");
        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot snapshots = task.getResult();
                    for (QueryDocumentSnapshot documentSnapshot : snapshots) {
                        DanhSachOnTap danhSachOnTap = new DanhSachOnTap();
                        danhSachOnTap.setOntap_id(documentSnapshot.getId());
                        if (documentSnapshot.contains("loaicau") && documentSnapshot.get("loaicau") != null) {
                            danhSachOnTap.setLoaicau(documentSnapshot.get("loaicau").toString());
                        } else {
                            danhSachOnTap.setLoaicau("Unknown");
                        }

                        if (documentSnapshot.contains("socau") && documentSnapshot.get("socau") != null) {
                            danhSachOnTap.setSocau(Integer.parseInt(documentSnapshot.get("socau").toString()));
                        } else {
                            danhSachOnTap.setSocau(0);
                        }

                        listOnTap.add(danhSachOnTap);
                    }
                    apdapter = new DanhSachOnTapApdapter(listOnTap);
                    gridView.setAdapter(apdapter);
                } else {
                    Toast.makeText(OnTap.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnTap.this.finish();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getSelectId = listOnTap.get(position).getOntap_id();
                Intent intent=new Intent(getApplicationContext(),CauHoiOnTap.class);
                startActivity(intent);

            }
        });
//        searchCauHoiOnTap.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                searchAndNavigate(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
    }


}