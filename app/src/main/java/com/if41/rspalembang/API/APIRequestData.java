package com.if41.rspalembang.API;

import com.if41.rspalembang.Model.ModelResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIRequestData {
    @GET("rumahsakit")
    Call<ModelResponse> ardRetrieve();
    @FormUrlEncoded
    @POST("rumahsakit")
    Call<ModelResponse> ardCreate(
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("telepon") String telepon
    );
    @DELETE("rumahsakit/{id}")
    Call<ModelResponse> ardDelete(
            @Path("id") int id
    );
    @FormUrlEncoded
    @PUT("rumahsakit/{id}")
    Call<ModelResponse> ardUpdate(
            @Path("id") int id,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("telepon") String telepon
    );
}