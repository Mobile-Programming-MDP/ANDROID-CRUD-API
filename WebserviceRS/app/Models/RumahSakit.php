<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class RumahSakit extends Model
{
    use HasFactory;
    //tambahkan $quarded / $fillable agar
    //memungkinkan untuk mass assignment
    protected $guarded = []; 
}
