<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use GuzzleHttp\Client;
use App\Models\User;
use App\Models\Movie;
use App\Helpers\Helper;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Config;
use GuzzleHttp\Exception\GuzzleException;
use Symfony\Component\Process\Process;
use Symfony\Component\Process\Exception\ProcessFailedException;

class FileController extends Controller
{
    protected $user, $movie, $empty, $config;

    public function __construct(User $user, Movie $movie) {
        $this->user = $user;
        $this->movie = $movie;
        $this->empty = true;
        $this->config = Config::get('constants');
    }

    public function runScript(Request $request) {
        // get the user's model file
        $param = Helper::processRequest($this->config['textFileURL']."2", 'GET', null)->original["file"];

        // fetch list of movies
        $output = array();
        $movies = $this->movie->all();
        foreach($movies as $movie) {
            // create bag of words
            $bagOfWords = "";
            $bagOfWords = Helper::processArray($bagOfWords, Helper::objectToArray($movie->casts));
            $bagOfWords = Helper::processArray($bagOfWords, Helper::objectToArray($movie->genres));
            $bagOfWords = Helper::processArray($bagOfWords, Helper::objectToArray($movie->directors));

            $process = new Process("python " . public_path() . "\python\hello.py \"{$param}\" \"{$bagOfWords}\"");
            $process->run();

            // executes after the command finishes
            if (!$process->isSuccessful()) {
                throw new ProcessFailedException($process);
            }

            $matchPercentage = (double) ($process->getOutput());
            array_push($output, $movie->title . " -> " . $matchPercentage);
        }

        dd($output);

        // $output = $process->getOutput();
        // dd($output);
        // dd(json_decode($process->getOutput(), true));
    }

    public function createPreference(Request $request) {
        if (!$this->validateRequest($request)) {
            return response()->json(['error' => 'Invalid Request'], 400);
        }

        $content = "";
        $user = auth()->user();

        $content = Helper::processArray($content, $request->actors);
        $content = Helper::processArray($content, $request->genres);
        $content = Helper::processArray($content, $request->directors);

        $json = [
            'id'    => $user->id,
            'str'   => $content
        ];

        $user->preference_set = true;
        $user->save();

        return Helper::processRequest($this->config['textFileURL'], 'POST', $json);
    }

    public function updateModel(Request $request) {
        if (!$this->validateUpdate($request)) {
            return response()->json(['error' => 'Invalid Request'], 400);
        }

        $content = "";
        $user = auth()->user();

        $movie = $this->movie->find($request->movie_id);
        $content = Helper::processArray($content, Helper::objectToArray($movie->casts));
        $content = Helper::processArray($content, Helper::objectToArray($movie->genres));
        $content = Helper::processArray($content, Helper::objectToArray($movie->directors));

        $json = [
            'id'    => $user->id,
            'str'   => $content
        ];

        return Helper::processRequest($this->config['textFileURL'], 'PUT', $json);
    }

    private function validateUpdate(Request $request) {
        $validator = Validator::make($request->all(), [
            'movie_id'  => 'required'
        ]);

        if ($validator->fails()) {
            return false;
        }
        return true;
    }

    private function validateRequest(Request $request) {
        $validator = Validator::make($request->all(), [
            'actors.*'    => 'required',
            'genres.*'    => 'required',
            'directors.*' => 'required'
        ]);

        if ($validator->fails()) {
            return false;
        }
        return true;
    }
}
