<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Validator;
use Illuminate\Http\Request;
use App\Models\Director;

class DirectorController extends Controller
{
    protected $director;

    public function __construct(Director $director) {
        $this->director = $director;
    }

    public function fetch() {
        $directors = $this->director->all();
        if (!$directors->isEmpty()) {
            return response()->json(['director' => $directors], 200);
        }
        return response()->json(['director' => $directors], 204);
    }

    public function find(int $id) {
        $director = $this->director->find($id);
        if ($director) {
            return response()->json(['director' => $director], 200);
        }
        return response()->json(['message' => 'Invalid Director ID'], 404);
    }

    public function create(Request $request) {
        if ($this->isDataInvalid($request)) {
            return response()->json(['message' => 'Data validation failed'], 422);
        }

        $director = $this->setAttributes($request);
        $director->save();
        return response()->json(['message' => 'Director successfully created', 'director' => $director], 200);
    }

    public function update(int $id, Request $request) {
        if ($this->isDataInvalid($request)) {
            return response()->json(['message' => 'Data validation failed'], 422);
        }

        $director = $this->director->find($id);
        if ($director) {
            $director->name = $request->name;
            $director->save();
            return response()->json(['director' => $director], 200);
        }
        return response()->json(['message' => 'Invalid Director ID'], 404);
    }

    public function isDataInvalid(Request $request) {
        $validator = Validator::make($request->all(), [
            'name' => 'required|max:255'
        ]);

        return $validator->fails();
    }

    public function setAttributes(Request $request) {
        $director = new Director();
        $director->name = $request->name;

        return $director;
    }
}
