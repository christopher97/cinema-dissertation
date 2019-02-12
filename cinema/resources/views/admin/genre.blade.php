<!-- /**
 * Created by PhpStorm.
 * User: Christopher Lycheald
 * Date: 27/01/2019
 * Time: 08:04
 */ -->

@extends('admin.layout')

@section('content')

    <style type="text/css">
        [toast] .toast-wrapper {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 80px;
            z-index: 99999;
            padding: 15px;
            transform: translateY(-150px);
            transition: all 0.5s cubic-bezier(0.19, 1, 0.22, 1);
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

        [toast] .toast-wrapper.active {
            transform: translateY(0px);
        }

        [toast] .message {
            background: #222222;
            color: white;
            text-align: center;
            font-weight: bold;
            padding: 10px 15px;
            border-radius: 4px;
            font-size: 16px;
            width: 300px;
            height: auto;
        }
    </style>

    <script src="{{ url(env('axios')) }}"></script>
    <script src="{{ url(env('vue')) }}"></script>
    <script src="{{ url(env('http_vue_loader')) }}"></script>


    <div class="content-wrapper" id="root">
        <toast ref="toast"></toast>
        <genre-modal :title="'Genre'">
        </genre-modal>
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
                    <button type="button" class="btn btn-success" data-toggle="modal"
                            data-target="#addModal">Add New</button>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">List of Genres</h3>

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
                                        <th>Name</th>
                                        <th>Date</th>
                                        <th class="text-center">...</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(genre) in genreState.genres">
                                        <td>@{{genre.id}}</td>
                                        <td><span v-if="!genre.onedit">@{{genre.name}}</span>
                                            <input type="text" class="form-control" v-model="genre.name"
                                                   v-if="genre.onedit" placeholder="genre Name">
                                        </td>
                                        <td>@{{genre.created_at}}</td>
                                        <td class="text-center">
                                            <button type="button" class="btn btn-sm btn-success" v-if="!genre.onedit"
                                                    @click="editItem(genre)">Edit</button>
                                            <button type="button" class="btn btn-sm btn-success" v-if="genre.onedit"
                                                    :disabled="genre.description === ''"
                                                    @click="updateItem(genre)">Save</button>
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

    <script type="module" src="{{ asset('js/pages/Genre.js') }}"></script>

@endsection