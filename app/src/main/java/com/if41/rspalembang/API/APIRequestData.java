package com.if41.rspalembang.API;

import com.if41.rspalembang.Model.ModelResponse;
import com.if41.rspalembang.Model.ModelResponseLogin;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIRequestData {
    @GET("rumahsakit")  //1
    Call<ModelResponse> ardRetrieve();

    @FormUrlEncoded
    @POST("rumahsakit") //2
    Call<ModelResponse> ardCreate(
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("telepon") String telepon
    );
    //@FormUrlEncoded
    @DELETE("rumahsakit/{id}") //3
    Call<ModelResponse> ardDelete(
            @Path("id") int id  //4
    );
    @FormUrlEncoded
    @PUT("rumahsakit/{id}")     //5
    Call<ModelResponse> ardUpdate(
            @Path("id") int id,     //6
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("telepon") String telepon
    );
    //method to login url
    @FormUrlEncoded
    @POST("login")
    Call<ModelResponseLogin> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("device_name") String deviceName
    );

    //method untuk simpan data RS + file
    @Multipart
    @POST("rumahsakit/uploadfile")
    Call<ModelResponse> simpanDataRS(@Part("nama") RequestBody nama,
                                  @Part("alamat") RequestBody alamat,
                                  @Part("telepon") RequestBody telepon,
                                  @Part MultipartBody.Part foto);
}