<!--
/**
 * Created by PhpStorm.
 * User: Christopher Lycheald
 * Date: 28/01/2019
 * Time: 04:45
 */

/**
 * TODO
 *
 * Update Movie Model
 *
 * * add a poster picture URL
 *
 * Create a form
 *
 * * Input the movie basic data
 * * Input the movie's actors
 * * Input the movie's directors
 * * Input the movie's genres
 *
 */

 -->

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

    <link rel="stylesheet" href="{{ asset('css/style.css') }}">

    <div class="content-wrapper" id="root">
        <toast ref="toast"></toast>
        <section class="content-header">
            <h1>
                Add New Movie
            </h1>
        </section>

        <!-- Main Content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-primary">
                        <!-- Box Header -->
                        <div class="box-header with-border">
                            <h3 class="box-title">Input Movie Details</h3>
                        </div>

                        <!-- Form Start -->
                        <form role="form">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="title">Title</label>
                                    <input type="text" class="form-control" id="title" placeholder="Movie Title"
                                           name="title" v-model="movie.title" required>
                                </div>
                                <div class="form-group">
                                    <label for="runningTime">Running Time (Minutes)</label>
                                    <input type="number" class="form-control" id="runningTime" name="running_time"
                                           placeholder="Running Time (Minutes)" v-model="movie.running_time" required>
                                </div>
                                <div class="form-group">
                                    <label for="language">Language</label>
                                    <input type="text" class="form-control" id="language" placeholder="Language"
                                            name="language" v-model="movie.language" required>
                                </div>
                                <div class="form-group">
                                    <label for="release_date">Release Date</label>
                                    <div class="input-group date">
                                        <div class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                        <input type="date" class="form-control pull-right" id="release_date"
                                               name="release_date" v-model="movie.release_date"
                                               data-date-format="dd-MM-yyyy" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="distributor">Distributor</label>
                                    <input type="text" class="form-control" id="distributor" placeholder="Distributor"
                                           name="distributor" v-model="movie.distributor" required>
                                </div>
                                <div class="form-group">
                                    <label for="synopsis">Synopsis</label>
                                    <textarea class="form-control" id="synopsis" placeholder="Movie Synopsis"
                                              name="synopsis" v-model="movie.synopsis"></textarea>
                                </div>
                                <div class="form-group">
                                    <label for="synopsis">Image URL</label>
                                    <input type="text" class="form-control" id="image_link" placeholder="Image URL"
                                           name="image_link" v-model="movie.image_url" required>
                                </div>
                                <div class="form-group">
                                    <label for="rating_id">Censor Rating</label>
                                    <select class="form-control" v-model="movie.rating">
                                        <option value="" selected disabled>Select a censor rating...</option>
                                        <option v-for="(cr) in crState.censorRatings" v-bind:value="cr">
                                            @{{ cr.description }}
                                        </option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="start_date">Play Start Date</label>
                                    <div class="input-group date">
                                        <div class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                        <input type="date" class="form-control pull-right" id="start_date"
                                               name="start_date" v-model="movie.start_date"
                                               data-date-format="dd-MM-yyyy" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="end_date">Play End Date</label>
                                    <div class="input-group date">
                                        <div class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                        <input type="date" class="form-control pull-right" id="end_date"
                                               name="end_date" v-model="movie.end_date"
                                               data-date-format="dd-MM-yyyy" required>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>

                    <movie-genre></movie-genre>

                    <movie-actor></movie-actor>

                    <movie-director></movie-director>

                    <movie-playtime></movie-playtime>

                    <div class="text-center msb-container">
                        <button type="button" class="btn btn-success movie-submit-button"
                            @click="create">Submit</button>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <script type="module" src="{{ asset('js/pages/MovieAdd.js') }}"></script>

@endsection