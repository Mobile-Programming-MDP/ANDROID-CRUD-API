<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('mahasiswas', function (Blueprint $table) {
            $table->id(); //Membuat Kolom Id Primary Key Auto Increment big Int
            $table->char("npm" , 11)->unique();
            $table->string("nama");//Default 255
            $table->string("tempat_lahir",100);
            $table->date("tanggal_lahir");
            $table->timestamps();// Mmebuat Kolom create_At and update_At bertipe date time
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('mahasiswas');
    }
};
