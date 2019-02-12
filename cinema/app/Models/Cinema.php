<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Cinema extends Model
{
    protected $table = "cinemas";
    protected $fillable = ['name', 'seat_capacity'];
    protected $guarded = [];
}
