<!DOCTYPE html>
<html>
<head>
    <title>Cinema Booking System Admin Page Login</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="msapplication-tap-highlight" content="no" />

    <link rel="stylesheet" href="{{ url('https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css') }}">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800" rel="stylesheet">

    <style type="text/css">
        #logo {
            height: 240px;
            width: auto;
        }

        @media screen and (max-width: 800px) {
            #logo {
                height: 150px;
                width: auto;
            }
        }
    </style>

    <script src='{{ url('https://www.google.com/recaptcha/api.js') }}'></script>
</head>
<body>
<div class="container py-5">
    <div class="row">
        <div class="col-12 text-center pb-5">
            <img src="{{ asset('images/clapperboard.png') }}" id="logo">
        </div>
    </div>

    @if (session()->has('flash_fail'))
        <div class="row">
            <div class="col-8 offset-2 text-center alert alert-danger">
                {{ session()->get('flash_fail')}}
            </div>
        </div>
    @endif

    <div class="row">
        <div class="col-12">
            <form method="POST" action="{{ route('admin.login') }}">
                {{ csrf_field() }}
                <div class="form-group row">
                    <label for="email" class="col-2 col-form-label offset-2">Email</label>
                    <div class="col-6">
                        <input type="email" class="form-control" id="email" name="email"
                               placeholder="Email" value="{{ old('email') }}" required autofocus>

                        @if ($errors->has('email'))
                            <span class="help-block">
                                    <strong>{{ $errors->first('email') }}</strong>
                            </span>
                        @endif
                    </div>
                </div>
                <div class="form-group row">
                    <label for="password" class="col-2 col-form-label offset-2">Password</label>
                    <div class="col-6">
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="Password" required>

                        @if ($errors->has('password'))
                            <span class="help-block">
                                    <strong>{{ $errors->first('password') }}</strong>
                            </span>
                        @endif
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-8 offset-4">
                        <div class="g-recaptcha" data-sitekey="{{ env('GOOGLE_RECAPTCHA_KEY') }}"></div>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-2 offset-4">
                        <button type="submit" class="btn btn-secondary btn-block btn-md btn-sm p-2">Sign In</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="row mt-5">
        <div class="col-12 text-center">
            <div>Icons made by
                <a href="https://www.flaticon.com/authors/pixel-perfect" title="Pixel perfect">Pixel perfect</a> from
                <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by
                <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a>
            </div>
        </div>
    </div>

</div>
</body>
</html>