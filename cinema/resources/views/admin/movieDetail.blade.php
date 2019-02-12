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
    <link rel="stylesheet" href="{{ asset('css/style.css') }}">

    <div class="content-wrapper" id="root">
        <toast ref="toast"></toast>
        <section class="content-header">
            <h1>
                View Movie Details
            </h1>
        </section>

        <!-- Main Content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-primary">
                        <!-- Box Header -->
                        <div class="box-header with-border">
                            <h3 class="box-title">Movie Details</h3>
                        </div>

                        <!-- Form Start -->
                        <form role="form">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="title">Title</label>
                                    <p class="form-control">{{ $movie->title }}</p>
                                </div>
                                <div class="form-group">
                                    <label for="runningTime">Running Time (Minutes)</label>
                                    <p class="form-control">{{ $movie->running_time }}</p>
                                </div>
                                <div class="form-group">
                                    <label for="language">Language</label>
                                    <p class="form-control">{{ $movie->language }}</p>
                                </div>
                                <div class="form-group">
                                    <label for="release_date">Release Date</label>
                                    <p class="form-control">{{ $movie->release_date }}</p>
                                </div>
                                <div class="form-group">
                                    <label for="distributor">Distributor</label>
                                    <p class="form-control">{{ $movie->distributor }}</p>
                                </div>
                                <div class="form-group">
                                    <label for="synopsis">Synopsis</label>
                                    <textarea readonly class="form-control">{{ $movie->synopsis }}</textarea>
                                </div>
                                <div class="form-group">
                                    <img src="{{ $movie->image_url }}">
                                </div>
                                <div class="form-group">
                                    <label for="rating_id">Censor Rating</label>
                                    <p class="form-control">{{ $movie->rating->description }}</p>
                                </div>
                                <div class="form-group">
                                    <label for="start_date">Play Start Date</label>
                                    <p class="form-control">{{ $movie->start_date }}</p>
                                </div>
                                <div class="form-group">
                                    <label for="end_date">Play End Date</label>
                                    <p class="form-control">{{ $movie->end_date }}</p>
                                </div>
                            </div>
                        </form>
                    </div>

                    <!-- list of genres -->
                    <div class="box box-info">
                        <!-- Box Header -->
                        <div class="box-header with-border">
                            <h3 class="box-title">Movie Genre(s)</h3>
                        </div>

                        <!-- Form Start -->
                        <form role="form">
                            <div class="box-body" v-for="(line, index) in genres" v-bind:key="index">
                                <div class="input-group">
                                    @foreach($movie->genres as $genre)
                                        <p>{{ $genre->name }}</p>
                                    @endforeach
                                </div>
                            </div>
                        </form>
                    </div>

                    <!-- list of actors -->
                    <div class="box box-info">
                        <!-- Box Header -->
                        <div class="box-header with-border">
                            <h3 class="box-title">Movie Actors(s)</h3>
                        </div>

                        <!-- Form Start -->
                        <form role="form">
                            <div class="box-body" v-for="(line, index) in genres" v-bind:key="index">
                                <div class="input-group">
                                    @foreach($movie->casts as $actor)
                                        <p>{{ $actor->name }}</p>
                                    @endforeach
                                </div>
                            </div>
                        </form>
                    </div>

                    <!-- list of directors -->
                    <div class="box box-info">
                        <!-- Box Header -->
                        <div class="box-header with-border">
                            <h3 class="box-title">Movie Director(s)</h3>
                        </div>

                        <!-- Form Start -->
                        <form role="form">
                            <div class="box-body" v-for="(line, index) in genres" v-bind:key="index">
                                <div class="input-group">
                                    @foreach($movie->directors as $director)
                                        <p>{{ $director->name }}</p>
                                    @endforeach
                                </div>
                            </div>
                        </form>
                    </div>

                    <!-- list of directors -->
                    <div class="box box-info">
                        <!-- Box Header -->
                        <div class="box-header with-border">
                            <h3 class="box-title">Movie Playtime(s)</h3>
                        </div>

                        <!-- Form Start -->
                        <form role="form">
                            <div class="box-body" v-for="(line, index) in genres" v-bind:key="index">
                                <div class="input-group">
                                    @foreach($movie->playtimes as $playtime)
                                        <p>{{ $playtime->time }}</p>
                                    @endforeach
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </div>

@endsection