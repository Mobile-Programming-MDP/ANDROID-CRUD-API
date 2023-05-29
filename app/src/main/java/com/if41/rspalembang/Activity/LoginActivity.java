package com.if41.rspalembang.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.if41.rspalembang.API.APIRequestData;
import com.if41.rspalembang.API.RetroServer;
import com.if41.rspalembang.Model.ModelResponseLogin;
import com.if41.rspalembang.R;
import com.if41.rspalembang.Utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static final String LOGIN_USERNAME = "LOGIN_USERNAME";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private final Utilities utilities = new Utilities();
    private EditText etUsername, etPassword;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);

        btLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            //call login
            login(username, password, "Android1");

            etUsername.setText("");
            etPassword.setText("");
        });
    }

    private void login(String email, String password, String deviceName) {
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponseLogin> proses = API.login(email, password, deviceName);
        proses.enqueue(new Callback<ModelResponseLogin>() {
            @Override
            public void onResponse(Call<ModelResponseLogin> call, Response<ModelResponseLogin> response) {
                if (response.isSuccessful() && response.body().getStatus()){
                    utilities.setPreferences(LoginActivity.this, LOGIN_USERNAME, email);
                    utilities.setPreferences(LoginActivity.this, ACCESS_TOKEN, response.body().getToken());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ModelResponseLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal menghubungi server :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}