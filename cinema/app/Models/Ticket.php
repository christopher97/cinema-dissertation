<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Ticket extends Model
{
    protected $table = "tickets";
    protected $fillable = ['user_id', 'performance_id', 'qty', 'status'];
    protected $guarded = [];

    public function user() {
        return $this->belongsTo('App\Models\User', 'user_id', 'id');
    }

    public function performance() {
        return $this->hasOne('App\Models\Performance', 'id', 'performance_id');
    }
}
