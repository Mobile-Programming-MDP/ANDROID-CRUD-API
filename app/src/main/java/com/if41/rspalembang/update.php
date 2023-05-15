<?php

require('koneksi.php');

$response = array();

if ($_SERVER['REQUEST_METHOD'] == "POST") {
    $id = $_POST['id'];
    $nama = $_POST['nama'];
    $alamat = $_POST['alamat'];
    $telepon = $_POST['telepon'];

    $query = "UPDATE tblrumahsakitpalembang SET nama='$nama',alamat='$alamat',telepon='$telepon' WHERE id = '$id'";
    $execute = mysqli_query($konek, $query);
    $cek = mysqli_affected_rows($konek);

    if($cek > 0) {
        $response['kode'] = 1;
        $response['pesan'] = "Sukses Mengubah Data";
    } else {
        $response['kode'] = 0;
        $response['pesan'] = "Gagal Mengubah Data";
    }
} else {
    $response['kode'] = 0;
    $response['pesan'] = "Tidak ada post data";
}

echo json_encode($response);
mysqli_close($konek);