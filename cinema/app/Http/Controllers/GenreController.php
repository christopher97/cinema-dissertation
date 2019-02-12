<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Validator;
use Illuminate\Http\Request;
use App\Models\Genre;

class GenreController extends Controller
{
    protected $genre;

    public function __construct(Genre $genre) {
        $this->genre = $genre;
    }

    public function fetch() {
        $genre = $this->genre->all();
        if (!$genre->isEmpty()) {
            return response()->json(['genre' => $genre], 200);
        }
        return response()->json(['genre' => $genre], 204);
    }

    public function find(int $id) {
        $genre = $this->genre->find($id);
        if ($genre) {
            return response()->json(['genre' => $genre], 200);
        }
        return response()->json(['message' => 'Invalid Genre ID'], 404);
    }

    public function create(Request $request) {
        if ($this->isDataInvalid($request)) {
            return response()->json(['message' => 'Data validation failed'], 422);
        }

        $genre = $this->setAttributes($request);
        $genre->save();
        return response()->json(['message' => 'Genre successfully created', 'genre' => $genre], 200);
    }

    public function update(int $id, Request $request) {
        if ($this->isDataInvalid($request)) {
            return response()->json(['message' => 'Data validation failed'], 422);
        }

        $genre = $this->genre->find($id);
        if ($genre) {
            $genre->name = $request->name;
            $genre->save();
            return response()->json(['genre' => $genre], 200);
        }
        return response()->json(['message' => 'Invalid Genre ID'], 404);
    }

    public function isDataInvalid(Request $request) {
        $validator = Validator::make($request->all(), [
            'name' => 'required|max:255'
        ]);

        return $validator->fails();
    }

    public function setAttributes(Request $request) {
        $genre = new Genre();
        $genre->name = $request->name;

        return $genre;
    }
}
