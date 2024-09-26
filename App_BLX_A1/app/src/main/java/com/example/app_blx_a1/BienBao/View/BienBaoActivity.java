package com.example.app_blx_a1.BienBao.View;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_blx_a1.BienBao.Controller.AdapterBienBao;
import com.example.app_blx_a1.BienBao.Model.ApiClient;
import com.example.app_blx_a1.BienBao.Model.ApiService;
import com.example.app_blx_a1.BienBao.Model.BienBao;
import com.example.app_blx_a1.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class BienBaoActivity extends AppCompatActivity implements AdapterBienBao.OnItemClickListener,AdapterBienBao.OnItemLongClickListener{
    RecyclerView revDanhSachBienBao;
    AdapterBienBao adapterBienBao;
    SearchView searchView;
    Button btnTatCa,btnCam,btnHieuLenh,btnChiDan,btnNguyHiem,btnPhu;
    ImageButton imgBtnPicture;

    List<BienBao> bienBaos = new ArrayList<>();
    List<BienBao> danhSachBienBaoCam = new ArrayList<>();
    List<BienBao> danhSachBienBaoHieuLenh = new ArrayList<>();
    List<BienBao> danhSachBienBaoChiDan = new ArrayList<>();
    List<BienBao> danhSachBienBaoNguyHiem = new ArrayList<>();
    List<BienBao> danhSachBienBaoPhu = new ArrayList<>();

    List<BienBao> filterList;
    private static final String urlDSBB = "https://ap-southeast-1.aws.data.mongodb-api.com/app/data-krbcclc/endpoint/layDanhSachBienBao";

    private static final String API_KEY = "bzaOvoTucC3QchbusRPg";
    private static final int REQUEST_IMAGE_GALLERY = 2;

    private ProgressBar progressBar;

    private Handler timeoutHandler;
    private Runnable timeoutRunnable;
    private static final int TIMEOUT_DURATION = 100000; // 1m
    private Call<ResponseBody> currentCall;


    private static final String PREFS_NAME = "BienBaoPrefs";
    private static final String PREF_PINNED_PREFIX = "pinned_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bien_bao);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        addControls();



        // Initialize timeout handler and runnable
        timeoutHandler = new Handler();
        timeoutRunnable = new Runnable() {
            @Override
            public void run() {
                if (currentCall != null && !currentCall.isExecuted()) {
                    currentCall.cancel();
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(BienBaoActivity.this, "Thời gian quá hạn", Toast.LENGTH_SHORT).show();
                }
            }
        };

        handleSSLHandshake();

        layDanhSachBienBaoAPI(urlDSBB);

        events();

    }

    void events(){

        eventSearchView();

        chuyenDanhSachBien();

        chonHinhAnh();
    }

    private void loadPinnedState() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        for (BienBao bienBao : bienBaos) {
            boolean isPinned = prefs.getBoolean(PREF_PINNED_PREFIX + bienBao.getId(), false);
            bienBao.setPinned(isPinned);
        }
        sortList();
    }

    private void savePinnedState(BienBao bienBao) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(PREF_PINNED_PREFIX + bienBao.getId(), bienBao.isPinned());
        editor.apply();
    }


    public void chonHinhAnh(){
        imgBtnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageGallery();
            }
        });
    }

    private void openImageGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                uploadImage(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage(Bitmap imageBitmap) {
        progressBar.setVisibility(View.VISIBLE);

        // Chuyển đổi Bitmap thành File
        File file = new File(getCacheDir(), "image.jpg");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Tạo RequestBody và MultipartBody.Part
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        currentCall = apiService.uploadImage(API_KEY, body);

        // Set timeout
        timeoutHandler.postDelayed(() -> {
            if (currentCall != null && !currentCall.isExecuted()) {
                currentCall.cancel();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(BienBaoActivity.this, "Thời gian quá hạn", Toast.LENGTH_SHORT).show();
            }
        }, TIMEOUT_DURATION);

        currentCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                // Cancel timeout
                timeoutHandler.removeCallbacks(timeoutRunnable);

                if (!call.isCanceled()) {
                    progressBar.setVisibility(View.GONE); // Đảm bảo ẩn ProgressBar khi có phản hồi
                    if (response.isSuccessful()) {
                        try {
                            String result = response.body().string();
                            Log.d("API Response", result);

                            // Trích xuất giá trị "class" từ phản hồi API
                            extractClassAndSearch(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(BienBaoActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Cancel timeout
                timeoutHandler.removeCallbacks(timeoutRunnable);

                if (!call.isCanceled()) {
                    progressBar.setVisibility(View.GONE); // Đảm bảo ẩn ProgressBar khi gặp lỗi
                    if (t instanceof java.net.SocketTimeoutException) {
                        Toast.makeText(BienBaoActivity.this, "Request timed out. Please try again.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BienBaoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    Log.d("API Error", t.getMessage());
                }
            }
        });
    }



    private void extractClassAndSearch(String result) {
        try {
            // Phân tích JSON từ chuỗi `result`
            JSONObject jsonObject = new JSONObject(result);
            JSONArray predictions = jsonObject.getJSONArray("predictions");

            if (predictions.length() > 0) {
                StringBuilder classNames = new StringBuilder();

                for (int i = 0; i < predictions.length(); i++) {
                    JSONObject prediction = predictions.getJSONObject(i);
                    String className = prediction.getString("class");

                    // Thêm className vào StringBuilder, cách nhau bằng dấu phẩy
                    if (classNames.length() > 0) {
                        classNames.append(", ");
                    }
                    classNames.append(className);
                }

                // Tự động điền giá trị `class` vào SearchView
                searchView.setQuery(classNames.toString(), true); // `true` để thực hiện tìm kiếm ngay lập tức
            } else {
                // Nếu không có dữ liệu trong predictions
                Toast.makeText(BienBaoActivity.this, "Không có kết quả", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onItemLongClick(BienBao bienBao) {
        bienBao.setPinned(!bienBao.isPinned());
        savePinnedState(bienBao);
        sortList();
        Toast.makeText(this, bienBao.isPinned() ? "Item pinned" : "Item unpinned", Toast.LENGTH_SHORT).show();
    }

    private void sortList() {
        Collections.sort(bienBaos, new Comparator<BienBao>() {
            @Override
            public int compare(BienBao o1, BienBao o2) {
                return Boolean.compare(o2.isPinned(), o1.isPinned());
            }
        });
        adapterBienBao.notifyDataSetChanged();
    }

    private void sortListLoaiBienBao(String loaiBienBao) {
        // Tạo map để lưu trữ các danh sách biển báo
        Map<String, List<BienBao>> mapDanhSachBienBao = new HashMap<>();
        mapDanhSachBienBao.put("bienBaoCam", danhSachBienBaoCam);
        mapDanhSachBienBao.put("bienBaoHieuLenh", danhSachBienBaoHieuLenh);
        mapDanhSachBienBao.put("bienBaoChiDan", danhSachBienBaoChiDan);
        mapDanhSachBienBao.put("bienBaoNguyHiem", danhSachBienBaoNguyHiem);
        mapDanhSachBienBao.put("bienBaoPhu", danhSachBienBaoPhu);

        // Lấy danh sách biển báo dựa trên loại
        List<BienBao> danhSachBienBao = mapDanhSachBienBao.get(loaiBienBao);

        if (danhSachBienBao != null) {
            // Sắp xếp danh sách
            Collections.sort(danhSachBienBao, new Comparator<BienBao>() {
                @Override
                public int compare(BienBao o1, BienBao o2) {
                    return Boolean.compare(o2.isPinned(), o1.isPinned());
                }
            });
            adapterBienBao.notifyDataSetChanged();
        }
    }



    private void setButtonState(boolean btnTatCaSelected, boolean btnCamSelected, boolean btnPhuSelected, boolean btnChiDanSelected, boolean btnHieuLenhSelected, boolean btnNguyHiemSelected, List<BienBao> danhSach) {
        btnTatCa.setSelected(btnTatCaSelected);
        btnCam.setSelected(btnCamSelected);
        btnPhu.setSelected(btnPhuSelected);
        btnChiDan.setSelected(btnChiDanSelected);
        btnHieuLenh.setSelected(btnHieuLenhSelected);
        btnNguyHiem.setSelected(btnNguyHiemSelected);
        adapterBienBao.setFilterList(danhSach);
    }

    void chuyenDanhSachBien(){
        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortListLoaiBienBao("bienBaoCam");
                setButtonState(false, true, false, false, false, false, danhSachBienBaoCam);
                //Toast.makeText(BienBaoActivity.this, "Danh sách biển báo cấm", Toast.LENGTH_SHORT).show();

            }
        });

        btnHieuLenh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortListLoaiBienBao("bienBaoHieuLenh");
                setButtonState(false, false, false, false, true, false, danhSachBienBaoHieuLenh);
                //Toast.makeText(BienBaoActivity.this, "Danh sách biển báo hiệu lệnh", Toast.LENGTH_SHORT).show();
            }
        });

        btnChiDan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortListLoaiBienBao("bienBaoChiDan");
                setButtonState(false, false, false, true, false, false, danhSachBienBaoChiDan);
                //Toast.makeText(BienBaoActivity.this, "Danh sách biển báo chỉ dẫn", Toast.LENGTH_SHORT).show();
            }
        });

        btnNguyHiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortListLoaiBienBao("bienBaoNguyHiem");
                setButtonState(false, false, false, false, false, true, danhSachBienBaoNguyHiem);
                //Toast.makeText(BienBaoActivity.this, "Danh sách biển báo nguy hiểm", Toast.LENGTH_SHORT).show();
            }
        });

        btnPhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortListLoaiBienBao("bienBaoPhu");
                setButtonState(false, false, true, false, false, false, danhSachBienBaoPhu);
                //Toast.makeText(BienBaoActivity.this, "Danh sách biển báo phụ", Toast.LENGTH_SHORT).show();
            }
        });

        btnTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonState(true, false, false, false, false, false, bienBaos);
                //Toast.makeText(BienBaoActivity.this, "Danh sách tất cả biển báo", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemClick(BienBao bienBao) {
        openDialog(bienBao);
    }

    public void openDialog(BienBao bienBao){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_item_detail_bien_bao);

        Window window = dialog.getWindow();

        if(window == null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowA = window.getAttributes();
        windowA.gravity = Gravity.BOTTOM;
        window.setAttributes(windowA);

        dialog.setCancelable(true);


        //gan noi dung
        ImageView imageView = dialog.findViewById(R.id.img_deltail_bien_bao);
        TextView txtTen = dialog.findViewById(R.id.txt_detail_ten_bien);
        TextView txtMoTa = dialog.findViewById(R.id.txt_detail_mo_ta);

        Picasso.get().load(bienBao.getHinh_anh()).resize(250,250).into(imageView);
        txtTen.setText(bienBao.getTen_bien());
        txtMoTa.setText(bienBao.getMo_ta());


        ImageButton imageButton = dialog.findViewById(R.id.img_button_close_detail);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void eventSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Thực hiện tìm kiếm khi người dùng nhấn enter
                filterList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    // Tải lại dữ liệu ban đầu khi văn bản tìm kiếm trống
                    adapterBienBao.setFilterList(bienBaos);
                }
                return false;
            }
        });
    }



    private void filterList(String query) {
        String[] keywords = query.split(","); // Tách các từ khóa bằng dấu phẩy
        filterList = new ArrayList<>();

        for (String keyword : keywords) {
            keyword = keyword.trim(); // Xóa khoảng trắng ở đầu và cuối từ khóa
            for (BienBao bienBao : bienBaos) {
                if (bienBao.getTen_bien().contains(keyword) || bienBao.getMo_ta().contains(keyword)) {
                    if (!filterList.contains(bienBao)) {
                        filterList.add(bienBao);
                    }
                }
            }
        }

        adapterBienBao.setFilterList(filterList);
    }

    void loadLayoutData() {
        revDanhSachBienBao.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        adapterBienBao = new AdapterBienBao(bienBaos, this,this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());

        revDanhSachBienBao.setLayoutManager(manager);

        revDanhSachBienBao.setItemAnimator(new DefaultItemAnimator());
        revDanhSachBienBao.setAdapter(adapterBienBao);
    }


    void layDanhSachBienBaoAPI(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(BienBaoActivity.this);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            Log.d("API Response", s);
                            readJson(new String(s.getBytes("ISO-8859-1"), "UTF-8"));
                            loadPinnedState();
                        } catch (JSONException e) {
                            Log.e("JSON Error", e.toString());
                            Toast.makeText(BienBaoActivity.this, "JSON Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException e) {
                            Log.e("Encoding Error", e.toString());
                            Toast.makeText(BienBaoActivity.this, "Encoding Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                        Log.e("Volley Error", volleyError.toString());
                        Toast.makeText(BienBaoActivity.this, "Khong the lay du lieu", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(request);
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

    void readJson(String jsonString) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonString);

        // Duyệt qua các mảng JSON và thêm các đối tượng BienBao vào danh sách bienBaos
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            // Duyệt qua các loại biển báo và thêm vào danh sách
            String[] loaiBienBao = {"Biển báo cấm", "Biển hiệu lệnh", "Biển chỉ dẫn", "Biển báo nguy hiểm và cảnh báo", "Biển phụ"};
            for (String loai : loaiBienBao) {
                if (jsonObject.has(loai)) {
                    JSONArray jsonArrayLoai = jsonObject.getJSONArray(loai);
                    for (int j = 0; j < jsonArrayLoai.length(); j++) {
                        JSONObject object = jsonArrayLoai.getJSONObject(j);
                        String ten_bien = object.getString("ten_bien");
                        String mo_ta = object.getString("mo_ta");
                        String hinh_anh = object.getString("hinh_anh");

                        BienBao bienBao = new BienBao(ten_bien, mo_ta, hinh_anh);
                        bienBaos.add(bienBao);
                        switch (loai) {
                            case "Biển báo cấm":
                                danhSachBienBaoCam.add(bienBao);
                                break;
                            case "Biển hiệu lệnh":
                                danhSachBienBaoHieuLenh.add(bienBao);
                                break;
                            case "Biển chỉ dẫn":
                                danhSachBienBaoChiDan.add(bienBao);
                                break;
                            case "Biển báo nguy hiểm và cảnh báo":
                                danhSachBienBaoNguyHiem.add(bienBao);
                                break;
                            case "Biển phụ":
                                danhSachBienBaoPhu.add(bienBao);
                                break;
                        }
                    }
                }
            }
        }
        loadLayoutData();


//        BienBao bienBao = bienBaos.get(0);
//        Log.d("check", bienBao.getTen_bien());
    }


    void addControls(){
        revDanhSachBienBao = findViewById(R.id.revDanhSachBienBao);
        searchView = findViewById(R.id.searchViewBienBao);
        searchView.clearFocus();

        btnTatCa = findViewById(R.id.btnBBTatCa);
        btnCam = findViewById(R.id.btnBBCam);
        btnHieuLenh = findViewById(R.id.btnBBHieuLenh);
        btnNguyHiem = findViewById(R.id.btnBBNguyHiem);
        btnChiDan = findViewById(R.id.btnBBChiDan);
        btnPhu = findViewById(R.id.btnBBPhu);

        btnTatCa.setSelected(true);
        //btnTatCa,btnCam,btnHieuLenh,btnChiDan,btnNguyHiem,btnPhu;
        imgBtnPicture = findViewById(R.id.imgBtnPicture);
        progressBar = findViewById(R.id.progressBar);
    }
}