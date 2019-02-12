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

@section('content')

    <script src="{{ url(env('axios')) }}"></script>
    <script src="{{ url(env('vue')) }}"></script>
    <script src="{{ url(env('http_vue_loader')) }}"></script>


    <div class="content-wrapper" id="root">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Dashboard
                <small>Control panel</small>
            </h1>
        </section>

        <!-- Main Content -->
        <section class="content">
            <div class="row" style="margin-bottom: 12px;">
                <div class="col-xs-12">
                    <a class="btn btn-success" href="{{ route('addMovie') }}">Add New</a>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">List of All Movies</h3>

                            <div class="box-tools">
                                <div class="input-group input-group-sm" style="width: 150px;">
                                    <input type="text" name="table_search" class="form-control pull-right" placeholder="Search">

                                    <div class="input-group-btn">
                                        <button type="submit" class="btn btn-default"><i class="fa fa-search"></i></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body table-responsive no-padding">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Title</th>
                                    <th>Release Date</th>
                                    <th>Start Date</th>
                                    <th>End Date</th>
                                    <th class="text-center">...</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="(movie) in movies">
                                        <td>@{{movie.id}}</td>
                                        <td>@{{movie.title}}</td>
                                        <td>@{{movie.release_date}}</td>
                                        <td>@{{movie.start_date}}</td>
                                        <td>@{{movie.end_date}}</td>
                                        <td class="text-center">
                                            <a :href="'/admin/movie/' + movie.id" class="btn btn-sm btn-success">Details</a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
            </div>
        </section>
    </div>

    <script type="module" src="{{ asset('js/pages/Movie.js') }}"></script>

@endsection