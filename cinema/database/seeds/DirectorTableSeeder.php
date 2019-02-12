<?php

use Illuminate\Database\Seeder;
use App\Models\Director;

class DirectorTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $directors = [
            ['name' => 'Steven Spielberg'],
            ['name' => 'James Cameron'],
            ['name' => 'Quentin Tarantino'],
            ['name' => 'James Wan'],
            ['name' => 'Woody Allen'],
            ['name' => 'Ryan Coogler'],
            ['name' => 'Anthony Russo'],
            ['name' => 'Joe Russo'],
            ['name' => 'Joss Whedon'],
            ['name' => 'John G. Avildsen'],
            ['name' => 'Robert Zemeckis'],
            ['name' => 'Sergio Leone'],
        ];

        foreach($directors as $director) {
            Director::create($director);
        }
    }
}
