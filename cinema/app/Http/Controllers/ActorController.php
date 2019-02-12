<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Validator;
use Illuminate\Http\Request;
use App\Models\Actor;

class ActorController extends Controller
{
    protected $actor;

    public function __construct(Actor $actor) {
        $this->actor = $actor;
    }

    public function fetch() {
        $actor = $this->actor->all();

        if (!$actor->isEmpty()) {
            return response()->json(['actor' => $actor], 200);
        }
        return response()->json(['actor' => $actor], 204);
    }

    public function find(int $id) {
        $actor = $this->actor->find($id);

        if ($actor) {
            return response()->json(['actor' => $actor], 200);
        }
        return response()->json(['message' => 'Invalid Actor ID'], 404);
    }

    public function create(Request $request) {
        if ($this->isDataInvalid($request)) {
            return response()->json(['message' => 'Data validation failed'], 422);
        }

        $actor = $this->setAttributes($request);
        $actor->save();

        return response()->json(['message' => 'Actor successfully created', 'actor' => $actor], 200);
    }

    public function update(int $id, Request $request) {
        if ($this->isDataInvalid($request)) {
            return response()->json(['message' => 'Data validation failed'], 422);
        }

        $actor = $this->actor->find($id);
        if ($actor) {
            $actor->name = $request->name;
            $actor->save();
            return response()->json(['actor' => $actor], 200);
        }
        return response()->json(['message' => 'Invalid Actor ID'], 404);
    }

    public function isDataInvalid(Request $request) {
        $validator = Validator::make($request->all(), [
            'name' => 'required|max:255'
        ]);

        return $validator->fails();
    }

    public function setAttributes(Request $request) {
        $actor = new Actor();
        $actor->name = $request->name;

        return $actor;
    }
}
