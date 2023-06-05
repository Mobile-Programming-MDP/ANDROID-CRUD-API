<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "api" middleware group. Make something great!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});
use App\Http\Controllers\API\AuthController;
Route::post("login", [AuthController::class, "login"]);
use App\Http\Controllers\RumahSakitController;
Route::resource('rumahsakit', RumahSakitController::class);

//route untuk simpan data rs + photo
Route::post('rumahsakit/uploadfile', [RumahSakitController::class, 'storeWithPhoto']);


