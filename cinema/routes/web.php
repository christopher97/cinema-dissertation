<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

Route::prefix('admin')->group(function () {

    Route::get('/login', function() {
        return view('admin.login');
    })->name('admin.loginPage');

    Route::post('/login', "AdminController@login")->name('admin.login');

    Route::group(['middleware' => ['AuthAdmin']], function() {
        Route::get('/', function () {
            return view('admin.homepage');
        })->name('admin.homepage');
        Route::post('/logout', "AdminController@logout")->name('admin.logout');

        Route::get('/cinema', function() {
            return view('admin.cinema');
        })->name('cinema');

        Route::get('/genre', function() {
            return view('admin.genre');
        })->name('genre');

        Route::get('/actor', function() {
            return view('admin.actor');
        })->name('actor');

        Route::get('/director',  function() {
            return view('admin.director');
        })->name('director');

        Route::get('/censor-rating', function() {
            return view('admin.censorRating');
        })->name('censor-rating');

        Route::get('/movie', function() {
            return view('admin.movie');
        })->name('movie');

        Route::get('/movie/playing', "movieController@showPlaying")->name('playingMovie');

        Route::get('/movie/add', function() {
            return view('admin.addMovie');
        })->name('addMovie');

        Route::get('/movie/{id}', "movieController@showDetail");
    });
});