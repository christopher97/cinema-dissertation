<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Validator;
use Illuminate\Http\Request;
use App\Models\PlayTime;

class PlayTimeController extends Controller
{
    protected $playtime;

    public function __construct(PlayTime $playtime) {
        $this->playtime = $playtime;
    }

    public function fetch(int $movie_id) {
        $playtime = $this->playtime->where('movie_id', '=', $movie_id)->get();
        if (!$playtime->isEmpty()) {
            return response()->json(['playtime' => $playtime], 200);
        }
        return response()->json(['message' => 'Invalid Movie ID'], 404);
    }

    public function find(int $id) {
        $playtime = $this->playtime->find($id);
        if (!$playtime->isEmpty()) {
            return response()->json(['playtime' => $playtime], 200);
        }
        return response()->json(['message' => 'Invalid Playtime ID'], 404);
    }

    public function create(Request $request) {
        // muliple input at once
        // read array input
    }

    public function update(int $id, Request $request) {
        // dunno yet
    }

    public function isDataInvalid(Request $request) {
        $validator = Validator::make($request->all(), [
            'movie_id' => 'required|integer',
            'time' => 'required'
        ]);

        return $validator->fails();
    }

    public function setAttributes(PlayTime $playtime, Request $request) {
        $playtime->time = $request->time;

        return $playtime;
    }
}
