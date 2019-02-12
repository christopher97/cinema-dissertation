<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="{{ asset('images/profile.png') }}" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p>{{ auth('web')->user()->fullname }}</p>
                <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
        </div>
        <!-- search form -->
        <form action="#" method="get" class="sidebar-form">
            <div class="input-group">
                <input type="text" name="q" class="form-control" placeholder="Search...">
                <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                </button>
              </span>
            </div>
        </form>
        <!-- /.search form -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu" data-widget="tree">
            <li class="header">MAIN NAVIGATION</li>
            <li {{ Request::is('admin') ? "class=active" : null }}>
                <a href="#">
                    <i class="fa fa-dashboard"></i> <span>Dashboard</span>
                </a>
            </li>
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-film"></i> <span>Movies</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu">
                    <li {{ Request::is('admin/movie/playing') ? "class=active" : null }}>
                        <a href="{{ route('playingMovie') }}"><i class="fa fa-circle-o"></i>Now Playing</a>
                    </li>
                    <li {{ Request::is('admin/movie') ? "class=active" : null }}>
                        <a href="{{ route('movie') }}"><i class="fa fa-circle-o"></i>All Movies</a>
                    </li>
                </ul>
            </li>
            <li {{ Request::is('admin/cinema') ? "class=active" : null }}>
                <a href="{{ route('cinema') }}">
                    <i class="fa fa-building"></i> <span>Cinemas</span>
                </a>
            </li>
            <li {{ Request::is('admin/genre') ? "class=active" : null }}>
                <a href="{{ route('genre') }}">
                    <i class="fa fa-television"></i> <span>Genres</span>
                </a>
            </li>
            <li {{ Request::is('admin/actor') ? "class=active" : null }}>
                <a href="{{ route('actor') }}">
                    <i class="fa fa-star"></i> <span>Actors</span>
                </a>
            </li>
            <li {{ Request::is('admin/director') ? "class=active" : null }}>
                <a href="{{ route('director') }}">
                    <i class="fa fa-video-camera"></i> <span>Directors</span>
                </a>
            </li>
            <li {{ Request::is('admin/censor-rating') ? "class=active" : null }}>
                <a href="{{ route('censor-rating') }}">
                    <i class="fa fa-child"></i> <span>Censor Ratings</span>
                </a>
            </li>
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>