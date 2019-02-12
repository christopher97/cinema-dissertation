<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class CensorRating extends Model
{
    public $timestamps = false;
    protected $table = "censor_ratings";
    protected $fillable = ['description'];
    protected $guarded = [];
}
