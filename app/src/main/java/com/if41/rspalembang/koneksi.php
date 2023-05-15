<?php

$host = 'localhost:3306';
$user = 'developer';
$pass = 'developer';
$db = 'dbrspalembang';

$konek = mysqli_connect($host, $user, $pass, $db) or die("Database MySQL Tidak Terhubung");