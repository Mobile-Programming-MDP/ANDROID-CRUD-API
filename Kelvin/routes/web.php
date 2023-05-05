<?php

use App\Http\Controllers\ControllerFakultas;
use App\Http\Controllers\ControllerMahasiswa;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "web" middleware group. Make something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});
//Cara Membuat Route
Route::get('/halo11', function () {
    echo "<h1>Hallo Semua</h1>";
});
Route::get('/halo2', function () {
    echo "<h1>Hallo Semua, Selamat Siang</h1>";
});
/*
Route::get('/mahasiswa/{nama?}', function ($nama = "Albert") {
    echo "<h1>Hallo $nama</h1>";
});
*/
Route::get('/mahasiswa2/{nama?}/{jurusan?}', function ($nama = "Albert", $jurusan = "Informatika") {
    echo "<h1>nama = $nama</h1>";
    echo "<h1>jurusan = $jurusan</h1>";
});
Route::get('/mahasiswa3/{nama?}/{jurusan?}', function ($nama = "albert", $jurusan = "informatika") {
    echo "<h1>nama = $nama</h1>";
    echo "<h1>jurusan = $jurusan</h1>";
})->where('nama', '[A-Z]+');
Route::get('/hubungi', function () {
    echo "<h1>Hubungi kami</h1>";
})->name('call');
Route::get('/halo1', function () {
    echo "<a href ='" . route('call') . "'>" . route('call') . "</a>";
});

//Redirect Route
Route::get('/halo', function () {
    return "<a href = " . route('call') . ">" . route('call') . "</a>";
});
Route::get('/halo2', function () {
    return "<a href = " . route('call') . ">Contoh 2</a>";
});

// Route Group
// Route::get('/mhs/nama', function() {
//     return "<h2>nama</h2>";
// });
// Route::get('/mhs/jurusan', function() {
//     return "<h2>jurusan</h2>";
// });
// Route Group untuk mengelompokkan URL
Route::prefix('/mhs')->group(function () {
    Route::get('/nama', function () {
        return "<h2>Nama</h2>";
    });
    Route::get('/jurusan', function () {
        return "<h2>Jurusan</h2>";
    });
});


//Method GET, POST, PUT, PATCH, DELETE
// Route::get('/profil', function() {
//     return view("profil");
// });

Route::get("/profile/{nama?}",function($nama = "Kelvin"){
    return view("profile",['namaMahasiswa '=> $nama]);
});

Route::get("/dosen", function(){
    return view("dosen.index");
});

/*Route::get("/fakultas", function(){
    $fakultas = array("Fakultas Ilmu Komputer","Fakultas Ekonomi dan Bisnis", "Fakultas Teknik");
    $kampus= "Universitas Multi Data Palembang ";
    return view("fakultas.index",['fakultas'=>$fakultas],['kampus' =>$kampus]);
});*/
Route::get("/fakultas",[ControllerFakultas::class,'index']);
//Route::get("/mahasiswa/create",[ControllerMahasiswa::class,'create']);
Route::resource("/Mahasiswa",ControllerMahasiswa::class);