package com.if41.rspalembang.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.if41.rspalembang.API.APIRequestData;
import com.if41.rspalembang.API.RetroServer;
import com.if41.rspalembang.Adapter.AdapterRumahSakit;
import com.if41.rspalembang.Model.ModelResponse;
import com.if41.rspalembang.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText etNama, etAlamat, etTelepon;
    private Button btnSimpan;
    private String nama, alamat, telepon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        etNama = findViewById(R.id.etNamaRS);
        etAlamat = findViewById(R.id.etAlamatRS);
        etTelepon = findViewById(R.id.etTelpRS);
        btnSimpan = findViewById(R.id.btTambah);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = etNama.getText().toString();
                alamat = etAlamat.getText().toString();
                telepon = etTelepon.getText().toString();
                if (nama.trim().equals("")) {
                    etNama.setError("Nama Harus Diisi");
                } else if (alamat.trim().equals("")) {
                    etAlamat.setError("Alamat Harus Diisi");
                } else if (telepon.trim().equals("")) {
                    etTelepon.setError("Telepon Harus Diisi");
                } else {
                    tambahRumahSakit();
                }
            }
        });
    }

    private void tambahRumahSakit() {
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardCreate(nama, alamat, telepon);
        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();
                Toast.makeText(TambahActivity.this, "Kode: " + kode + "| Pesan: " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }
            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal menghubungi server :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}