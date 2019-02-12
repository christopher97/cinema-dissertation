<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Performance extends Model
{
    protected $table = "performances";
    protected $fillable = ['play_time_id', 'cinema_id', 'date', 'total_seat', 'booked_seat'];
    protected $guarded = [];

    public function cinema() {
        return $this->belongsTo('App\Models\Cinema', 'cinema_id', 'id');
    }

    public function playtime() {
        return $this->belongsTo('App\Models\PlayTime', 'play_time_id', 'id');
    }
}
