<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Performance;
use App\Models\Movie;
use App\Models\PlayTime;
use App\Models\Cinema;
use Illuminate\Support\Facades\Validator;
use DateTime;
use DatePeriod;
use DateInterval;
use Carbon\Carbon;
use Illuminate\Support\Facades\Storage;

class PerformanceController extends Controller
{
    protected $movie, $playtime, $performance, $cinema;

    public function __construct(Movie $movie, PlayTime $playtime, Performance $performance, Cinema $cinema) {
        $this->movie = $movie;
        $this->playtime = $playtime;
        $this->performance = $performance;
        $this->cinema = $cinema;
    }

    public function fetch(Request $request, $id) {
        $now = Carbon::now('Asia/Kuala_Lumpur');
        $performances = $this->performance->whereHas('playtime.movie', function($query) use ($id) {
            $query->where('movies.id', '=', $id);
        })->where('date', '>=', $now)->skip(0)->take(50)->get();

        return response()->json(['performance' => $performances], 200);
    }

    public function createPlaytime(Request $request) {
        $validator = Validator::make($request->all(), [
            'movie_id'  => 'required',
            'times'      => 'required|array',
            'times.*'    => 'required'
        ]);

        if ($validator->fails()) {
            return response()->json(['message' => 'Invalid Input'], 422);
        }

        $id = $request->movie_id;
        $movie = $this->movie->find($id);
        if (!$movie) {
            return response()->json(['message' => 'Invalid Movie ID'], 422);
        }

        try {
            $timeInput = $request->times;
            $cinemas = $this->cinema->all();
            foreach($timeInput as $time) {
                $playtime = new Playtime();

                $playtime->movie_id = $id;
                $playtime->time = $time;
                $playtime->save();

                $this->createPerformance($movie, $cinemas, $playtime->id);
            }

            return response()->json(['message' => 'Playtime and Performance created'], 200);
        } catch (\Exception $ex) {
            return response()->json(['message' => 'Error Creating Performance'], 400);
        }
    }

    public function createPerformance(Movie $movie, $cinemas, int $id) {
        foreach($cinemas as $cinema) {
            $begin = new DateTime($movie->start_date);
            $end = (new DateTime($movie->end_date))->add(new DateInterval('P1D'));
            $daterange = new DatePeriod($begin, new DateInterval('P1D'), $end);

            foreach($daterange as $date) {
                $performance = new Performance();

                $performance->play_time_id = $id;
                $performance->cinema_id = $cinema->id;
                $performance->date = $date;
                $performance->total_seat = $cinema->seat_capacity;
                $performance->booked_seat = 0;

                $performance->save();
            }
        }
    }

    public function checkAvailability(Request $request) {
        // $validator = Validator::make($request->all(), [
        //     'play_time_id'  => 'required',
        //     'cinema_id'     => 'required',
        //     'date'          => 'required|date'
        // ]);

        // if ($validator->fails()) {
        //     return response()->json(['error' => 'Incorrect Data'], 400);
        // }

        $playtimeID = $request->play_time_id;
        $cinemaID = $request->cinema_id;
        $date = $request->date;

        $performance = $this->performance->where('play_time_id', '=', $playtimeID)
                                        ->where('cinema_id', '=', $cinemaID)
                                        ->where('date', '=', $date)
                                        ->first();

        if ($performance) {
            return response()->json(['performance' => $performance], 200);
        }
        return response()->json(['error' => 'Not Found'], 404);
    }
}
