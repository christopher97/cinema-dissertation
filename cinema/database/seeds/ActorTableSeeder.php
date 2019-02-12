<?php

use Illuminate\Database\Seeder;
use App\Models\Actor;

class ActorTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $actors = [
            ['name' => 'Tom Cruise'],
            ['name' => 'Arnold Schwarzenegger'],
            ['name' => 'Clint Eastwood'],
            ['name' => 'Michael J. Fox'],
            ['name' => 'Vin Diesel'],
            ['name' => 'Michelle Rodriguez'],
            ['name' => 'Jennifer Lawrence'],
            ['name' => 'Scarlett Johansson'],
            ['name' => 'Emma Stone'],
            ['name' => 'Gwyneth Paltrow'],
            ['name' => 'Elizabeth Olsen'],
            ['name' => 'Robert Downey Jr.'],
            ['name' => 'Chris Evans'],
            ['name' => 'Chris Hemsworth'],
            ['name' => 'Dwayne Johnson'],
            ['name' => 'Tyrese Gibson'],
            ['name' => 'Jason Statham'],
            ['name' => 'Ludacris'],
            ['name' => 'Tom Holland'],
            ['name' => 'Josh Brolin'],
            ['name' => 'Benedict Cumberbatch'],
            ['name' => 'Mark Ruffalo'],
            ['name' => 'Chadwick Boseman'],
            ['name' => 'Letitia Wright'],
            ['name' => 'Michael B. Jordan'],
            ['name' => 'Sylvester Stallone'],
            ['name' => 'Christopher Lloyd'],
        ];

        foreach($actors as $actor) {
            Actor::create($actor);
        }
    }
}
