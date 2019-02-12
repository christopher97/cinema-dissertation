<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Validator;
use Illuminate\Http\Request;
use App\Models\Ticket;
use Illuminate\Support\Facades\Config;

class TicketController extends Controller
{
    protected $ticket, $config;

    public function __construct(Ticket $ticket) {
        $this->ticket = $ticket;
        $this->config = Config::get('constants');
    }

    public function fetch(int $user_id) {
        $ticket = $this->ticket->where('user_id', '=', $user_id)->get();
        if (!$ticket->isEmpty()) {
            return response()->json(['ticket' => $ticket], 200);
        }
        return response()->json(['ticket' => $ticket], 204);
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

    public function useTicket(int $id) {
        $ticket = $this->ticket->find($id);

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
