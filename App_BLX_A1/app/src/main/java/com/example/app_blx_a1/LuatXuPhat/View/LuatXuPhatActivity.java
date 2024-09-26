package com.example.app_blx_a1.LuatXuPhat.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_blx_a1.LuatXuPhat.Model.HanhViHopNhat;
import com.example.app_blx_a1.LuatXuPhat.Model.HanhViLienQuan;
import com.example.app_blx_a1.LuatXuPhat.Model.LuatGiaoThong;
import com.example.app_blx_a1.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class LuatXuPhatActivity extends AppCompatActivity {
    Button btnPTXeMay, btnPTXeOTo, btnPTXeKhac;
    ImageButton imgBtnHieuLenh,imgBtnChuyenHuong,imgBtnDungXe,imgBtnThietBi,imgBtnTocDo,imgBtnVanChuyen,imgBtnTrangBi,imgBtnDuongCam,imgBtnNongDo,imgBtnGiayTo,imgBtnKhac;
    List<LuatGiaoThong> luatGiaoThongList = new ArrayList<>();
    private String currentVehicle = "xe_may"; // Giá trị mặc định ban đầu

    String urlLuatXuPhat = "https://ap-southeast-1.aws.data.mongodb-api.com/app/data-krbcclc/endpoint/layDanhSachLuatXuPhatVN";
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_luat_xu_phat);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        addControls();
        handleSSLHandshake();
        layDanhSachLuatXuPhatAPI(urlLuatXuPhat);
        addEvents();
    }

    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }

    private void addEvents() {
        chuyenDoiPhuongTien();
        handleImageButtonClicks();
        handleSearch();


    }

    private void handleSearch() {
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAndNavigate(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    // Thêm hàm tìm kiếm
    void searchAndNavigate(String query) {
        List<LuatGiaoThong> filteredLuatGiaoThongList = new ArrayList<>();
        for (LuatGiaoThong luat : luatGiaoThongList) {
            if (luat.getTenPhuongTien().equalsIgnoreCase(currentVehicle) &&
                    luat.getMoTaViPham().toLowerCase().contains(query.toLowerCase())) {
                filteredLuatGiaoThongList.add(luat);
            }
        }

        Gson gson = new Gson();
        String luatGiaoThongJson = gson.toJson(filteredLuatGiaoThongList);

        Intent intent = new Intent(LuatXuPhatActivity.this, DanhSachXuPhatHanhViActivity.class);
        intent.putExtra("luatGiaoThongListJson", luatGiaoThongJson);
        intent.putExtra("searchQuery", query);  // Truyền thêm giá trị tìm kiếm
        startActivity(intent);
    }


    void handleImageButtonClicks() {
        imgBtnHieuLenh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterAndNavigate("hieu_lenh_chi_dan");
            }
        });

        imgBtnChuyenHuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterAndNavigate("chuyen_huong_nhuong_duong");
            }
        });

        imgBtnDungXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterAndNavigate("dung_xe_do_xe");
            }
        });

        imgBtnThietBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterAndNavigate("thiet_bi_uu_tien_coi");
            }
        });
        imgBtnTocDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterAndNavigate("toc_do_khoang_cach_an_toan");
            }
        });
        imgBtnVanChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterAndNavigate("van_chuyen_nguoi_hang_hoa");
            }
        });
        imgBtnTrangBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterAndNavigate("trang_thiet_bi_phuong_tien");
            }
        });
        imgBtnDuongCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterAndNavigate("duong_cam_duong_mot_chieu");
            }
        });
        imgBtnNongDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterAndNavigate("nong_do_con_chat_kich_thich");
            }
        });
        imgBtnGiayTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterAndNavigate("giay_to_xe");
            }
        });
        imgBtnKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterAndNavigate("khac");
            }
        });
    }
    void filterAndNavigate(String tenHanhVi) {
        List<LuatGiaoThong> filteredLuatGiaoThongList = new ArrayList<>();
        for (LuatGiaoThong luat : luatGiaoThongList) {
            if (luat.getTenPhuongTien().equalsIgnoreCase(currentVehicle) && luat.getTenHanhVi().equalsIgnoreCase(tenHanhVi)) {
                filteredLuatGiaoThongList.add(luat);
            }
        }

        // Chuyển đổi danh sách thành JSON
        Gson gson = new Gson();
        String luatGiaoThongJson = gson.toJson(filteredLuatGiaoThongList);

        // Truyền JSON qua Intent
        Intent intent = new Intent(LuatXuPhatActivity.this, DanhSachXuPhatHanhViActivity.class);
        intent.putExtra("luatGiaoThongListJson", luatGiaoThongJson);
        startActivity(intent);
    }


    void chuyenDoiPhuongTien(){
        btnPTXeMay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện nút "Xe Máy" được nhấn
                setButtonStatePhuongTien(true,false,false);
            }
        });

        btnPTXeOTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện nút "Xe Ô Tô" được nhấn
                setButtonStatePhuongTien(false,true,false);

            }
        });

        btnPTXeKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện nút "Xe Khác" được nhấn
                setButtonStatePhuongTien(false,false,true);

            }
        });
    }

    void layDanhSachLuatXuPhatAPI(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(LuatXuPhatActivity.this);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Log.d("check", s);
                        try {
                            readJson(new String(s.getBytes("ISO-8859-1"), "UTF-8"));
                        } catch (JSONException e) {
                            Log.e("JSONException", "JSON parsing error", e);
                        } catch (UnsupportedEncodingException e) {
                            Log.e("EncodingException", "Unsupported encoding", e);
                        }
//                        LuatGiaoThong luatGiaoThong = luatGiaoThongList.get(0);
//                        String chech = luatGiaoThong.getMoTaViPham();
                        //Log.d("check list",chech);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(LuatXuPhatActivity.this, "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(request);
    }

    void readJson(String s) throws JSONException {
        JSONArray jsonArray = new JSONArray(s);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        //JSONObject jsonObject = new JSONObject(s);
        JSONArray danhSachLuatXuPhat = jsonObject.getJSONArray("danh_sach_luat_xu_phat");

        for (int i = 0; i < danhSachLuatXuPhat.length(); i++) {
            JSONObject item = danhSachLuatXuPhat.getJSONObject(i);

            String nguoiDieuKhien = item.getString("nguoi_dieu_khien");
            String moTaViPham = item.getString("mo_ta_vi_pham");
            String mucPhat = item.getString("muc_phat");
            String phatBoSung = item.optString("phat_bo_sung", "");
            String tenPhuongTien = item.getString("ten_phuong_tien");
            String tenHanhVi = item.getString("ten_hanh_vi");

            // Xử lý hanhViHopNhat
            List<HanhViHopNhat> hanhViHopNhatList = new ArrayList<>();
            if (item.has("hanh_vi_hop_nhat")) {
                JSONArray hanhViHopNhatArray = item.getJSONArray("hanh_vi_hop_nhat");
                for (int j = 0; j < hanhViHopNhatArray.length(); j++) {
                    JSONObject hanhViHopNhatItem = hanhViHopNhatArray.getJSONObject(j);
                    HanhViHopNhat hanhViHopNhat = new HanhViHopNhat(
                            hanhViHopNhatItem.getString("nguoi_dieu_khien"),
                            hanhViHopNhatItem.getString("mo_ta_vi_pham"),
                            hanhViHopNhatItem.getString("muc_phat"),
                            hanhViHopNhatItem.optString("phat_bo_sung", "")
                    );
                    hanhViHopNhatList.add(hanhViHopNhat);
                }
            }

            // Xử lý hanhViLienQuan
            List<HanhViLienQuan> hanhViLienQuanList = new ArrayList<>();
            if (item.has("hanh_vi_lien_quan")) {
                JSONArray hanhViLienQuanArray = item.getJSONArray("hanh_vi_lien_quan");
                for (int k = 0; k < hanhViLienQuanArray.length(); k++) {
                    JSONObject hanhViLienQuanItem = hanhViLienQuanArray.getJSONObject(k);
                    HanhViLienQuan hanhViLienQuan = new HanhViLienQuan(
                            hanhViLienQuanItem.getString("nguoi_dieu_khien"),
                            hanhViLienQuanItem.getString("mo_ta_vi_pham"),
                            hanhViLienQuanItem.getString("muc_phat")
                    );
                    hanhViLienQuanList.add(hanhViLienQuan);
                }
            }

            // Tạo đối tượng LuatGiaoThong
            LuatGiaoThong luatGiaoThong = new LuatGiaoThong(
                    nguoiDieuKhien,
                    moTaViPham,
                    mucPhat,
                    phatBoSung,
                    hanhViHopNhatList,
                    hanhViLienQuanList,
                    tenPhuongTien,
                    tenHanhVi
            );

            // Thêm vào danh sách
            luatGiaoThongList.add(luatGiaoThong);
        }
    }


    void setButtonStatePhuongTien(boolean btnPTXeMayS, boolean btnPTXeOToS, boolean btnPTXeKhacS) {
        btnPTXeMay.setSelected(btnPTXeMayS);
        btnPTXeOTo.setSelected(btnPTXeOToS);
        btnPTXeKhac.setSelected(btnPTXeKhacS);
        if (btnPTXeMayS) currentVehicle = "xe_may";
        else if (btnPTXeOToS) currentVehicle = "xe_o_to";
        else if (btnPTXeKhacS) currentVehicle = "xe_khac";

    }

    void addControls() {
        btnPTXeMay = findViewById(R.id.btnPhuongTienXeMay);
        btnPTXeOTo = findViewById(R.id.btnPhuongTienXeOTo);
        btnPTXeKhac = findViewById(R.id.btnPhuongTienKhac);

        imgBtnHieuLenh = findViewById(R.id.imgBtnHieuLenh);
        imgBtnChuyenHuong = findViewById(R.id.imgBtnChuyenHuong);
        imgBtnDungXe = findViewById(R.id.imgBtnDungXe);
        imgBtnThietBi = findViewById(R.id.imgBtnThietBi);
        imgBtnTocDo = findViewById(R.id.imgBtnTocDo);
        imgBtnVanChuyen = findViewById(R.id.imgBtnVanChuyen);
        imgBtnTrangBi = findViewById(R.id.imgBtnTrangBi);
        imgBtnDuongCam = findViewById(R.id.imgBtnDuongCam);
        imgBtnNongDo = findViewById(R.id.imgBtnNongDo);
        imgBtnGiayTo = findViewById(R.id.imgBtnGiayTo);
        imgBtnKhac = findViewById(R.id.imgBtnKhac);

        searchView = findViewById(R.id.searchViewLuatGiaoThong);

        setButtonStatePhuongTien(true,false,false);

    }





}
