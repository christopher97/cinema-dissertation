<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Validator;
use Illuminate\Http\Request;
use App\Models\Ticket;
use App\Models\User;
use App\Models\Performance;
use Illuminate\Support\Facades\Config;

class TicketController extends Controller
{
    protected $ticket, $config, $user, $performance;

    public function __construct(Ticket $ticket, User $user, Performance $performance) {
        $this->ticket = $ticket;
        $this->user = $user;
        $this->performance = $performance;
        $this->config = Config::get('constants');
    }

    public function purchase(Request $request) {
        $ticket = new Ticket();
        $user = auth()->user();

        $performance = $this->performance->find($request->performance_id);
        if (!$performance) {
            return response()->json(['error' => 'Invalid Performance ID'], 400);
        }

        $ticket->user_id = $user->id;
        $ticket->performance_id = $performance->id;
        $ticket->qty = $request->qty;
        $ticket->status = $this->config['Valid'];

        try {
            $ticket->save();
            $performance->booked_seat = $performance->booked_seat + $ticket->qty;
            $performance->save();

            return response()->json(['message' => 'Ticket has been purchased!'], 200);
        } catch (\Exception $e) {
            return response()->json(['error' => 'An error occurred'], 400);
        }
    }

    public function fetch(Request $request) {
        $user = auth()->user();
        // fix this
        $ticket = $this->ticket->with('performance', 'performance.cinema',
                                        'performance.playtime', 'performance.playtime.movie')
                            ->where('user_id', '=', $user->id)->get();
        if (!$ticket->isEmpty()) {
            return response()->json(['tickets' => $ticket], 200);
        }
        return response()->json(['tickets' => $ticket], 204);
    }

    public function find(int $id) {
        $ticket = $this->ticket->find($id);
        if ($ticket) {
            return response()->json(['ticket' => $ticket], 200);
        }
        return response()->json(['message' => 'Invalid Ticket ID', 404]);
    }

    public function create(int $id, Request $request) {
        if ($this->isDataInvalid($request)) {
            return response()->json(['message' => 'Data validation failed'], 422);
        }

        $ticket = new Ticket();
        $ticket = $this->setAttributes($request);
        $ticket->save();

        return response()->json(['message' => 'You ticket has been issued!', 'ticket' => $ticket], 200);
    }

    public function useTicket(Request $request) {
        $ticket = $this->ticket->find($request->id);

        if (!$ticket) {
            return response()->json(['message' => 'Invalid Ticket ID'], 404);
        }
        $ticket->status = $this->config['Used'];
        $ticket->save();

        return response()->json(['message' => 'Your ticket has been used!'], 200);
    }

    public function isDataInvalid(Request $request) {
        $validator = Validator::make($request->all(), [
            'user_id' => 'required|integer',
            'playtime_id' => 'required|integer',
            'cinema_id' => 'required|integer',
            'qty'   => 'required|integer',
            'status'    => 'required|boolean'
        ]);

        return $validator->false();
    }

    public function setAttributes(Ticket $ticket, Request $request) {
        $ticket->user_id = $request->user_id;
        $ticket->playtime_id = $request->playtime_id;
        $ticket->cinema_id = $request->cinema_id;
        $ticket->qty = $request->qty;
        $ticket->status = $this->config['Valid'];

        return $ticket;
    }
}
