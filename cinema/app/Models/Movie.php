<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use App\Models\Actor as Actor;
use App\Models\Genre as Genre;
use App\Models\Director as Director;

class Movie extends Model
{
    protected $table = "movies";
    protected $fillable = ['title', 'running_time', 'language', 'release_date', 'synopsis', 'image_url',
                            'distributor', 'rating_id', 'ticket_price', 'start_date', 'end_date'];
    protected $guarded = [];

    public function casts() {
        return $this->belongsToMany(Actor::class, 'movie_cast');
    }

    public function genres() {
        return $this->belongsToMany(Genre::class, 'movie_genres');
    }

    public function directors() {
        return $this->belongsToMany(Director::class, 'movie_directors');
    }

    public function playtimes() {
        return $this->hasMany('App\Models\PlayTime', 'movie_id', 'id');
    }

    public function rating() {
        return $this->hasOne('App\Models\CensorRating', 'id', 'rating_id');
    }
}
