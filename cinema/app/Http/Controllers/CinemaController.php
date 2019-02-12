<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Validator;
use Illuminate\Http\Request;
use App\Models\Cinema;

class CinemaController extends Controller
{
    protected $cinema;

    public function __construct(Cinema $cinema) {
        $this->cinema = $cinema;
    }

    public function fetch() {
        $cinema = $this->cinema->all();
        if (!$cinema->isEmpty()) {
            return response()->json(['cinema' => $cinema], 200);
        }
        return response()->json(['cinema' => $cinema], 204);
    }

    public function find(int $id) {
        $cinema = $this->cinema->find($id);
        if ($cinema) {
            return response()->json(['cinema' => $cinema], 200);
        }
        return response()->json(['message' => 'Invalid Cinema ID'], 404);
    }

    public function create(Request $request) {
        if ($this->isDataInvalid($request)) {
            return response()->json(['message' => 'Data validation failed'], 422);
        }

        $cinema = $this->setAttributes($request);
        $cinema->save();
        return response()->json(['message' => 'Cinema successfully created', 'cinema' => $cinema], 200);
    }

    public function update(Request $request, int $id) {
        if ($this->isDataInvalid($request)) {
            return response()->json(['message' => 'Data validation failed'], 422);
        }

        $cinema = $this->cinema->find($id);
        if ($cinema) {
            $cinema->name = $request->name;
            $cinema->seat_capacity = $request->seat_capacity;
            $cinema->save();
            return response()->json(['cinema' => $cinema], 200);
        }
        return response()->json(['message' => 'Invalid Cinema ID'], 404);
    }

    public function isDataInvalid(Request $request) {
        $validator = Validator::make($request->all(), [
            'name' => 'required|max:255',
            'seat_capacity' => 'required|integer'
        ]);

        return $validator->fails();
    }

    public function setAttributes(Request $request) {
        $cinema = new Cinema();
        $cinema->name = $request->name;
        $cinema->seat_capacity = $request->seat_capacity;

        return $cinema;
    }
}
