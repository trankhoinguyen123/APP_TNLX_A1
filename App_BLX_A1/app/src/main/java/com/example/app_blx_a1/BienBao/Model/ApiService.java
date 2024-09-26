package com.example.app_blx_a1.BienBao.Model;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    @Multipart
    @POST("predict-sign/1")
    Call<ResponseBody> uploadImage(
            @Query("api_key") String apiKey,
            @Part MultipartBody.Part image
    );
}
