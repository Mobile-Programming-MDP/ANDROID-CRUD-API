<?php

require('koneksi.php');

$response = array();

if ($_SERVER['REQUEST_METHOD'] == "POST") {
    $id = $_POST['id'];

    $query = "DELETE FROM tblrumahsakitpalembang 
    WHERE id = '$id'";
    $execute = mysqli_query($konek, $query);
    $cek = mysqli_affected_rows($konek);

    if($cek > 0) {
        $response['kode'] = 1;
        $response['pesan'] = "Sukses Menghapus Data";
    } else {
        $response['kode'] = 0;
        $response['pesan'] = "Gagal Menghapus Data";
    }
} else {
    $response['kode'] = 0;
    $response['pesan'] = "Tidak ada post data";
}

echo json_encode($response);
mysqli_close($konek);