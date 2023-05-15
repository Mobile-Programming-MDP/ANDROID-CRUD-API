package com.if41.rspalembang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.if41.rspalembang.API.APIRequestData;
import com.if41.rspalembang.API.RetroServer;
import com.if41.rspalembang.Adapter.AdapterRumahSakit;
import com.if41.rspalembang.Model.ModelResponse;
import com.if41.rspalembang.Model.ModelRumahSakit;
import com.if41.rspalembang.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvRumahSakit;
    private ProgressBar pbRumahSakit;
    private RecyclerView.Adapter adRumahSakit;
    private RecyclerView.LayoutManager lmRumahSakit;
    private List<ModelRumahSakit> listRumahSakit = new
            ArrayList<>();
    private FloatingActionButton fabTambahData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvRumahSakit = findViewById(R.id.rvRumahSakit);
        pbRumahSakit = findViewById(R.id.pbRumahSakit);
        fabTambahData = findViewById(R.id.fabTambahData);
        lmRumahSakit = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rvRumahSakit.setLayoutManager(lmRumahSakit);
        fabTambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,
                        TambahActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveRumahSakit();
    }

    public void retrieveRumahSakit() {
        pbRumahSakit.setVisibility(View.VISIBLE);
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardRetrieve();
        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listRumahSakit = response.body().getData();
                if (kode == 1){
                    adRumahSakit = new AdapterRumahSakit(MainActivity.this, listRumahSakit);
                    rvRumahSakit.setAdapter(adRumahSakit);
                    adRumahSakit.notifyDataSetChanged();
                }else{
                    Toast.makeText(MainActivity.this, pesan.toString(), Toast.LENGTH_SHORT).show();
                }

                pbRumahSakit.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal menghubungi server :" + t.getMessage(),
                Toast.LENGTH_SHORT).show();
                pbRumahSakit.setVisibility(View.GONE);
            }
        });
    }
}