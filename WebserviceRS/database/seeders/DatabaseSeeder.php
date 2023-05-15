<?php

namespace Database\Seeders;

// use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use App\Models\RumahSakit;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     */
    public function run(): void
    {
        RumahSakit::create([
            'nama' => 'RS Bari', 
            'alamat'=> 'Kertapati',
            'telepon' => '0821252525',
            'foto' => 'https://www.garnesia.com/images/vendor/5266_18b702f6fb23e81d060bb62643310299.jpg']);
    }
}
