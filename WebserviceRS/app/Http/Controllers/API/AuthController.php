<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Validation\ValidationException;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;

class AuthController extends Controller
{
    public function login(Request $request){
        $request->validate([
            'email' => 'required',
            'password' => 'required',
            'device_name' => 'required' //Untuk Menentukan Nama Token
        ]);

        $user = User::where("email", $request->email)->first();
        if(!$user || !Hash::check($request->password, $user->password)){
           return response()->json(
            [
                'status' => false, 
                'token' => null, 
                'message' => "User tidak ditemukan!"
            ]);
        }
        //Generate Token access User
        $token = $user->createToken($request->device_name)->plainTestToken();
        return response()->json(
            [
                'status' => true, 
                'token' => $token,
                'message' => "Login sukses!"
            ]);
    }
}
