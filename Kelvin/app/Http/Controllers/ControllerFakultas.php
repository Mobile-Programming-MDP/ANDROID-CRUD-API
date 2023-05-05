<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class ControllerFakultas extends Controller
{
    function index(){
        $fakultas = array("Fakultas Ilmu Komputer","Fakultas Ekonomi dan Bisnis", "Fakultas Teknik");
        $kampus= "Universitas Multi Data Palembang ";
        return view("fakultas.index",['fakultas'=>$fakultas],['kampus' =>$kampus]);
    }
}
