<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Validator;
use Illuminate\Http\Request;
use App\Models\Movie;
use App\Models\Actor;
use App\Models\Genre;
use App\Models\Director;
use Carbon\Carbon;
use DateTime;
use DatePeriod;
use DateInterval;

class MovieController extends Controller
{
    protected $movie, $actor, $genre, $director;

    public function __construct(Movie $movie, Actor $actor, Genre $genre, Director $director) {
        $this->movie = $movie;
        $this->actor = $actor;
        $this->genre = $genre;
        $this->director = $director;
    }

    public function showDetail(int $id) {
        $movie = $this->movie->find($id);
        if ($movie) {
            return view('admin.movieDetail')->with('movie', $movie);
        }
        return redirect()->back();
    }

    public function showPlaying(Request $request) {
        $now = Carbon::now('Asia/Kuala_Lumpur');
        $movies = $this->movie->where('start_date', '<=', $now)->where('end_date', '>=', $now)->get();

        return view('admin.playingMovie')->with('movies', $movies);
    }

    public function fetchPlaying() {
        $now = Carbon::now('Asia/Kuala_Lumpur');
        $movies = $this->movie->with('rating', 'casts', 'genres', 'directors')
                                ->where('start_date', '<=', $now)
                                ->where('end_date', '>=', $now)->get();

        if ($movies) {
            return response()->json(['movies' => $movies], 200);
        }

        return response()->json(['message' => 'No movies playing at the moment'], 204);
    }

    public function fetch() {
        $movie = $this->movie->all();
        if (!$movie->isEmpty()) {
            return response()->json(['movie' => $movie], 200);
        }
        return response()->json(['movie' => $movie], 204);
    }

    public function find(int $id) {
        $movie = $this->movie->find($id);
        if ($movie) {
            return response()->json($movie, 200);
        }
        return response()->json(['message' => 'Invalid Movie ID'], 404);
    }

    public function create(Request $request) {
        if ($this->isDataInvalid($request)) {
            return request()->json(['message' => 'Data validation failed'], 422);
        }

        $movie = new Movie();
        $movie = $this->setAttributes($movie, $request);
        $movie->save();

        return response()->json(['message' => 'Movie successfully created', 'movie' => $movie], 200);
    }

    public function update(int $id, Request $request) {
        if ($this->isDataInvalid($request)) {
            return request()->json(['message' => 'Data validation failed'], 422);
        }

        $movie = $this->movie->find($id);
        if ($movie) {
            $movie = $this->setAttributes($movie, $request);
            $movie->save();

            return response()->json(['message' => 'Movie successfully created', 'movie' => $movie], 200);
        }
        return response()->json(['message' => 'Invalid Movie ID'], 404);
    }

    public function pairMovie(int $id, Request $request) {
        $movie = $this->movie->find($id);

        $actors = array();
        $genres = array();
        $directors = array();

        $n = count($request->casts);
        for($i=0; $i<$n; $i++) {
            $item = $this->actor->find($request->casts[$i]);
            if (!$item) {
                return response()->json(['message' => 'Invalid actor ID '.$request->casts[$i]], 422);
            }
            array_push($actors, $item);
        }

        $n = count($request->genres);
        for($i=0; $i<$n; $i++) {
            $item = $this->genre->find($request->genres[$i]);
            if (!$item) {
                return response()->json(['message' => 'Invalid actor ID '.$request->genres[$i]], 422);
            }
            array_push($genres, $item);
        }

        $n = count($request->directors);
        for($i=0; $i<$n; $i++) {
            $item = $this->director->find($request->directors[$i]);
            if (!$item) {
                return response()->json(['message' => 'Invalid actor ID '.$request->directors[$i]], 422);
            }
            array_push($directors, $item);
        }

        // attach actors
        for($i=0; $i<sizeof($actors); $i++) {
            $movie->casts()->attach($actors[$i]);
        }

        // attach genres
        for($i=0; $i<sizeof($genres); $i++) {
            $movie->genres()->attach($genres[$i]);
        }

        // attach directors
        for($i=0; $i<sizeof($directors); $i++) {
            $movie->directors()->attach($directors[$i]);
        }

        return response()->json(['message' => 'Relationship successfully attached'], 200);
    }

    public function isDataInvalid(Request $request) {
        $validator = Validator::make($request->all(), [
            'title' => 'required|max:255',
            'running_time' => 'required|numeric',
            'language' => 'required|max:255',
            'release_date' => 'required|date',
            'synopsis' => 'required',
            'image_url' => 'required',
            'distributor' => 'required',
            'ticket_price' => 'required',
            'rating' => 'required|array',
            'start_date' => 'required|date',
            'end_date' => 'required|date'
        ]);

        return $validator->fails();
    }

    public function setAttributes(Movie $movie, Request $request) {
        $movie->title           = $request->title;
        $movie->running_time    = $request->running_time;
        $movie->language        = $request->language;
        $movie->release_date    = $request->release_date;
        $movie->distributor     = $request->distributor;
        $movie->image_url       = $request->image_url;
        $movie->synopsis        = $request->synopsis;
        $movie->ticket_price    = $request->ticket_price;
        $movie->rating_id       = $request->rating['id'];
        $movie->start_date      = $request->start_date;
        $movie->end_date        = $request->end_date;

        return $movie;
    }
}
