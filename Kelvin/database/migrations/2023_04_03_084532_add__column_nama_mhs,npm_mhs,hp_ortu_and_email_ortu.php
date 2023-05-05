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
        Schema::table('parent_table', function (Blueprint $table) {
            //
            $table-> string("nama_mhs");
            $table-> char("npm_mhs",11)->unique();
            $table->integer("hp_ortu");
            $table->string("email_ortu");
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::table('parent_table', function (Blueprint $table) {
            //
            $table->dropColumn('nama_mhs');
            $table->dropColumn('npm_mhs');
            $table->dropColumn('hp_ortu');
            $table->dropColumn('email_ortu');
        });
    }
};
