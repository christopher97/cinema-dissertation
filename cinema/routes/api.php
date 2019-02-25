<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

//Route::middleware('jwt.auth')->get('/user', function (Request $request) {
//    return auth('api')->user();
//});

Route::post('login', 'UserController@login');
Route::post('register', 'UserController@register');

Route::group(['middleware' => ['jwt.auth']], function() {
    Route::post('validate-token', 'UserController@validateToken');
    Route::post('logout', 'UserController@logout');
    Route::get('user', 'UserController@getUser');
    Route::put('user/update', 'UserController@update');

    Route::post('ticket/purchase', 'TicketController@purchase');
    Route::put('ticket/invalidate', 'TicketController@useTicket');
    Route::get('tickets', 'TicketController@fetch');
});

Route::get('censor-rating', 'CensorRatingController@fetch');
Route::post('censor-rating', 'CensorRatingController@create');
Route::get('censor-rating/{id}', 'CensorRatingController@find');
Route::put('censor-rating/{id}', 'CensorRatingController@update');
Route::delete('censor-rating/{id}', 'CensorRatingController@delete');

// -- ACTOR -- //
Route::get('actor', 'ActorController@fetch');
Route::get('actor/{id}', 'ActorController@find');
Route::post('actor', 'ActorController@create');
Route::put('actor/{id}', 'ActorController@update');

// -- GENRE -- //
Route::get('genre', 'GenreController@fetch');
Route::get('genre/{id}', 'GenreController@find');
Route::post('genre', 'GenreController@create');
Route::put('genre/{id}', 'GenreController@update');

// -- DIRECTOR -- //
Route::get('director', 'DirectorController@fetch');
Route::get('director/{id}', 'DirectorController@find');
Route::post('director', 'DirectorController@create');
Route::put('director/{id}', 'DirectorController@update');

// -- MOVIE -- //
Route::get('movie', 'MovieController@fetch');
Route::get('movie/{id}', 'MovieController@find');
Route::post('movie', 'MovieController@create');
Route::put('movie/{id}', 'MovieController@update');
Route::post('movie/{id}/pair', 'MovieController@pairMovie');
Route::get('movies/playing', 'MovieController@fetchPlaying');

// -- PLAYTIME -- //
Route::get('movie/playtime/{id}', 'PlayTimeController@fetch');
Route::get('playtime/{id}', 'PlayTimeController@fetch');
Route::post('movie/playtime', 'PerformanceController@createPlaytime');
Route::put('playtime/{id}', 'PlayTimeController@update');

// -- TICKET -- //
// Route::get('ticket/user/{id}', 'TicketController@fetch');
Route::get('ticket/{id}', 'TicketController@find');
Route::post('ticket', 'TicketController@create');
Route::put('ticket/{id}', 'TicketController@update');

// -- CINEMA -- //
Route::get('cinema', 'CinemaController@fetch');
Route::get('cinema/{id}', 'CinemaController@fetch');
Route::post('cinema', 'CinemaController@create');
Route::put('cinema/{id}', 'CinemaController@update');

// --- PERFORMANCE --- //
Route::get('movie/{id}/performance', 'PerformanceController@fetch');
Route::post('performance/check', 'PerformanceController@checkAvailability');
