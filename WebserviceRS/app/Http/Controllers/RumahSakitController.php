<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\RumahSakit;

class RumahSakitController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $listrs = RumahSakit::all();
        if(count($listrs) > 0) {
            return response()->json(
                [
                    'kode' => 1,
                    'pesan' => "Data Tersedia",
                    'data' => $listrs,
                ]
            );   
        }else{
            return response()->json(
                [
                    'kode' => 0,
                    'pesan' => "Data Tidak Tersedia",
                ]
            );  
        }
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        //
    }

    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, string $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        //
    }
}
