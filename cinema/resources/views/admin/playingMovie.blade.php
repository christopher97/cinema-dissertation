<!--
/**
 * Created by PhpStorm.
 * User: Christopher Lycheald
 * Date: 28/01/2019
 * Time: 04:04
 */ -->
<!-- /**
 * Created by PhpStorm.
 * User: Christopher Lycheald
 * Date: 26/01/2019
 * Time: 05:00
 */ -->

@extends('admin.layout')

        <!--
         * TODO
         *
         * USE BOOTSTRAP's CARD
        -->

@section('content')

    <div class="content-wrapper">
        <section class="content-header">
            <h1>
                Now Playing
            </h1>
        </section>

        <!-- Main Content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    @if(!count($movies))
                        <div class="alert alert-danger">
                            No movie is playing at the moment.
                        </div>
                    @endif

                    <div class="row">
                        @foreach($movies as $movie)
                        <div class="col-md-4 col-xs-6">
                            <a href="/admin/movie/{{ $movie->id }}">
                                <div class="card">
                                    <img src="{{ $movie->image_url }}" alt="Avatar" style="width:100%">
                                    <div class="container">
                                        <h4 class="text-black"><b>{{ $movie->title }}</b></h4>
                                    </div>
                                </div>
                            </a>
                        </div>
                        @endforeach
                    </div>
                </div>
            </div>
        </section>
    </div>

    <style>
    .card {
        /* Add shadows to create the "card" effect */
        box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
        transition: 0.3s;
    }

    /* On mouse-over, add a deeper shadow */
    .card:hover {
        box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
    }

    /* Add some padding inside the card container */
    .container {
        padding: 2px 16px;
    }
    </style>

@endsection