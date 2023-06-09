package com.if41.rspalembang.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.if41.rspalembang.API.APIRequestData;
import com.if41.rspalembang.API.RetroServer;
import com.if41.rspalembang.Adapter.AdapterRumahSakit;
import com.if41.rspalembang.Model.ModelResponse;
import com.if41.rspalembang.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText etNama, etAlamat, etTelepon;
    private Button btnSimpan;
    private String nama, alamat, telepon;

    private Uri fileUri;
    private String mCurrentPhotoPath;
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
                    //tambahRumahSakit();
                    simpanDataRumahSakit();
                }
            }
        });

        //Proses Ambil Foto
        Button btnAmbilFoto = findViewById(R.id.btTakePicture);
        btnAmbilFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCaptureCamera();
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

    //FUNGSI CAPTURE CAMERA
    private void startCaptureCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (ContextCompat.checkSelfPermission(TambahActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    ActivityCompat.requestPermissions(TambahActivity.this, new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    takePictureN();
                } else {
                    takePicture();
                }
            }
        } else {
            takePicture();
        }
    }

    private void takePicture() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(TambahActivity.this, "No camera on this device", Toast.LENGTH_LONG).show();
        } else {
            int cameraId = findFrontFacingCamera();
            if (cameraId < 0) {
                Toast.makeText(TambahActivity.this, "No front facing camera found.", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri(1); //MEDIA_TYPE_IMAGE
                System.out.println("fileUri: " + fileUri.getPath());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                intent.putExtra("android.intent.extras.CAMERA_FACING", cameraId);
                startActivityForResult(intent, 1000);   //CAMERA_CAPTURE_IMAGE_REQUEST_CODE
            }
        }
    }

    private void takePictureN() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(TambahActivity.this, "No camera on this device", Toast.LENGTH_LONG).show();
        } else {
            int cameraId = findFrontFacingCamera();
            if (cameraId < 0) {
                Toast.makeText(TambahActivity.this, "No front facing camera found.", Toast.LENGTH_LONG).show();
            } else {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        System.out.println("Error");
                    }
                    // Continue only if the File was successfully created
                    if (photoFile.exists()) {
                        fileUri = FileProvider.getUriForFile(TambahActivity.this,
                                "com.if41.rspalembang.fileprovider", photoFile); //sesauikan dengan nama package -> namapackage.fileprovider
                        System.out.println("File Uri N : " + fileUri);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(takePictureIntent, 1000); //CAMERA_CAPTURE_IMAGE_REQUEST_CODE
                    } else {
                        Toast.makeText(TambahActivity.this, "File Not Exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "FOTOSELFI"); //IMAGE_DIRECTORY_NAME

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == 1) { //MEDIA_TYPE_IMAGE
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    //dipanggil di dalam method takePictureN()
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath(); //buat variabel mCurrentPhotoPath bertipe string
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                galleryAddPic();
            } else {
                try {
                    String getPath = fileUri.getPath();
                    mCurrentPhotoPath = getPath;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //tampilkan di ImageView
            if(mCurrentPhotoPath != null){
                File imgFile = new File(mCurrentPhotoPath);
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ImageView myImage = (ImageView) findViewById(R.id.previewImage);
                    myImage.setImageBitmap(myBitmap);
                }
            }
            finish();
        } else {
            finish();
        }
    }

    //Khusus Android N, dipanggil dari method onActivityResult
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = null;
        try {
            f = new File(mCurrentPhotoPath);
            fileUri = Uri.fromFile(f);
            mediaScanIntent.setData(fileUri);
            sendBroadcast(mediaScanIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void simpanDataRumahSakit() {
        File file = new File(mCurrentPhotoPath);
        RequestBody reqImage = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto", file.getName(), reqImage);

        RequestBody reqNama = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(nama));
        RequestBody reqAlamat = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(alamat));
        RequestBody reqTelepon = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(telepon));

        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.simpanDataRS(reqNama, reqAlamat, reqTelepon, partImage);
        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                if(response.isSuccessful()){
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    Toast.makeText(TambahActivity.this, "Kode: " + kode + "| Pesan: " + pesan, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Oops Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 125) { //REQUEST_CODE_ASK_CAMERA_N_PERMISSIONS
            Map<String, Integer> perms = new HashMap<>();
            perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);
            if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
            ) {
                takePictureN();     // All Permissions Granted
            } else {
                Toast.makeText(TambahActivity.this, "Akses Camera ditolak!", Toast.LENGTH_SHORT)
                        .show();     // Permission Denied
            }
        } else if (requestCode == 126) { //REQUEST_CODE_ASK_CAMERA_PERMISSIONS
            Map<String, Integer> perms = new HashMap<>();
            perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);

            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);

            if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
            ) {
                takePicture(); // All Permissions Granted
            } else {
                Toast.makeText(TambahActivity.this, "Akses Camera ditolak!", Toast.LENGTH_SHORT)
                        .show();    // Permission Denied
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}