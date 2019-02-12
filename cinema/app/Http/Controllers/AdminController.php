<?php

namespace App\Http\Controllers;

use App\Http\Requests\RecaptchaFormRequest;
use Illuminate\Http\Request;
use App\Models\Admin;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Hash;
use App\Validators\ReCaptcha;

class AdminController extends Controller
{
    protected $admin;

    public function __construct(Admin $admin) {
        $this->admin = $admin;
    }

    public function login(RecaptchaFormRequest $request) {
        // get data from request
        $validator = Validator::make($request->all(), [
            'email'     => 'required',
            'password'  => 'required'
        ]);

        // -- BACK END VALIDATION --
        if ($validator->fails()) {
            return redirect()->back()->withInput($request->only('email'))->withErrors($validator);
        }
        // get admin data
        $admin = auth('web')->attempt([
            'email' => $request->email,
            'password' => $request->password
        ]);

        // if data is correct
        if ($admin) {
            // compare password
            $admin = auth('web')->user();

            if (Hash::needsRehash($admin->password)) {
                $admin->password = Hash::make($request->password);
                $admin->save();
            }

            session()->put('admin', $admin);

            return redirect(route('admin.homepage'));
        }

        session()->flash('flash_fail', 'Incorrect email/password');
        return redirect()->back()->withInput($request->only('email'));
    }

    public function logout(Request $request) {
        auth('web')->logout();
        return redirect(route('admin.login'));
    }
}
