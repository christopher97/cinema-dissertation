<?php

namespace App\Http\Middleware;

use Closure;

class AuthAdmin
{
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle($request, Closure $next)
    {
        if (session()->has('admin')) {
            return $next($request);
        }
        return redirect(route('admin.login'), 302)->with('flash_fail', 'You are not logged in.');
    }
}
