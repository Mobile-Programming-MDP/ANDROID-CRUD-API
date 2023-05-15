package com.if41.rspalembang.Model;

import java.util.List;

public class ModelResponse {
    private int kode;
    private String pesan;
    private List<ModelRumahSakit> data;

    public int getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelRumahSakit> getData() {
        return data;
    }
}