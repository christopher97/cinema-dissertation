<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class PlayTime extends Model
{
    public $timestamps = false;
    protected $table = "play_times";
    protected $fillable = ['movie_id', 'time'];
    protected $guarded = [];

    public function movie() {
        return $this->belongsTo('App\Models\Movie', 'movie_id', 'id');
    }
}
