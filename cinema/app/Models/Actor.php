<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Actor extends Model
{
    protected $table = "actors";
    protected $fillable = ['name'];
    protected $guarded = [];
}
