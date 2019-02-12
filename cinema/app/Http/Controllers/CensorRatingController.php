<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Validator;
use Illuminate\Http\Request;
use App\Models\CensorRating;

class CensorRatingController extends Controller
{
    protected $censorRating;

    public function __construct(CensorRating $cr) {
        $this->censorRating = $cr;
    }

    public function fetch() {
        $cr = $this->censorRating->all();
        if (!$cr->isEmpty()) {
            return response()->json(['censor_rating' => $cr], 200);
        }
        return response()->json(['censor_rating' => $cr], 204);
    }

    public function find(int $id) {
        $cr = $this->censorRating->find($id);

        if ($cr) {
            return response()->json(['censor_rating' => $cr], 200);
        }
        return response()->json(['message' => 'Invalid Censor Rating ID'], 404);
    }

    public function create(Request $request) {
        if ($this->isDataInvalid($request)) {
            return response()->json(['message' => 'Data validation failed'], 422);
        }

        $cr = $this->setAttributes($request);
        $cr->save();

        return response()->json(['message' => 'Censor Rating Successfully Created', 'censor_rating' => $cr], 200);
    }

    public function update(int $id, Request $request) {
        if ($this->isDataInvalid($request)) {
            return response()->json(['message' => 'Data validation failed'], 422);
        }

        $cr = $this->censorRating->find($id);
        if ($cr) {
            $cr->description = $request->description;
            $cr->save();
            return response()->json(['censor_rating' => $cr], 200);
        }
        return response()->json(['message' => 'Invalid Censor Rating ID'], 404);
    }

    public function delete(int $id) {
        $cr = $this->censorRating->find($id);
        if ($cr) {
            $cr->delete();
            return response()->json(['message' => 'Censor Rating Deleted'], 200);
        }
        return response()->json(['message' => 'Invalid Censor Rating ID'], 404);
    }

    public function isDataInvalid(Request $request) {
        $validator = Validator::make($request->all(), [
            'description' => 'required|max:255'
        ]);

        return $validator->fails();
    }

    public function setAttributes(Request $request) {
        $cr = new CensorRating();
        $cr->description = $request->description;

        return $cr;
    }
}
