<?php

require('koneksi.php');

$query = "SELECT * FROM tblrumahsakitpalembang";

$execute = mysqli_query($konek, $query);

$cek = mysqli_affected_rows($konek);

if ($cek > 0) {
    $response['kode'] = 1;
    $response['pesan'] = "Data Tersedia";
    $response['data'] = array();

    while($ambil = mysqli_fetch_object($execute)) {
        $temp["id"] = $ambil->id;
        $temp["nama"] = $ambil->nama;
        $temp["alamat"] = $ambil->alamat;
        $temp["telepon"] = $ambil->telepon;
        $temp["foto"] = $ambil->foto;

        array_push($response['data'], $temp);
     }
} else {
    $response['kode'] = 0;
    $response['pesan'] = "Data Tidak Tersedia";
}

echo json_encode($response);
//var_dump($temp);
mysqli_close($konek);