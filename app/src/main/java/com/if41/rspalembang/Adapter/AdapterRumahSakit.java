package com.if41.rspalembang.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.if41.rspalembang.API.APIRequestData;
import com.if41.rspalembang.API.RetroServer;
import com.if41.rspalembang.Activity.MainActivity;
import com.if41.rspalembang.Activity.UbahActivity;
import com.if41.rspalembang.Model.ModelResponse;
import com.if41.rspalembang.Model.ModelRumahSakit;
import com.if41.rspalembang.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterRumahSakit extends
        RecyclerView.Adapter<AdapterRumahSakit.HolderData> {
    private Context ctx;
    private List<ModelRumahSakit> listRumahSakit;

    public AdapterRumahSakit(Context ctx, List<ModelRumahSakit> listRumahSakit) {
        this.ctx = ctx;
        this.listRumahSakit = listRumahSakit;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rumah_sakit, parent, false);
        HolderData holder = new HolderData(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelRumahSakit MRS = listRumahSakit.get(position);
        holder.tvId.setText(String.valueOf(MRS.getId()));
        holder.tvNama.setText(MRS.getNama());
        holder.tvAlamat.setText(MRS.getAlamat());
        holder.tvTelepon.setText(MRS.getTelepon());
    }

    @Override
    public int getItemCount() {
        return listRumahSakit.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvId, tvNama, tvAlamat, tvTelepon;
        ImageView ivRumahSakit;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvID);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvAlamat = itemView.findViewById(R.id.tvAlamat);
            tvTelepon = itemView.findViewById(R.id.tvTelp);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int idRumahSakit = Integer.parseInt(tvId.getText().toString());
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
                    dialog.setTitle("Perhatian");
                    dialog.setMessage("Pilih perintah yang diinginkan");
                    dialog.setCancelable(true);
                    dialog.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            hapusRumahSakit(idRumahSakit);
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(ctx, UbahActivity.class);
                            intent.putExtra("xId", tvId.getText().toString());
                            intent.putExtra("xNama", tvNama.getText().toString());
                            intent.putExtra("xAlamat", tvAlamat.getText().toString());
                            intent.putExtra("xTelepon", tvTelepon.getText().toString());
                            ctx.startActivity(intent);
                        }
                    });
                    dialog.show();
                    return false;
                }
            });
        }

        private void hapusRumahSakit(int id) {
            APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = API.ardDelete(id);
            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    Toast.makeText(ctx, "Kode: " + kode + "| Pesan: " + pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).retrieveRumahSakit();
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal menghubungi server:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}