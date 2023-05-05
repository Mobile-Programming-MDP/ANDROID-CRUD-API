<!--@include('layout.header',['title'=>'Halaman Fakultas'])-->
@extends("layout.master")

@section("content")
<h1>Fakultas</h1>
@foreach($fakultas as $f)
    <li>{{$f}}</li>
@endforeach
@endsection
<!--@include('layout.footer')-->