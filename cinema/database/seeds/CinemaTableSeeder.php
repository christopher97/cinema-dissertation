<?php

use Illuminate\Database\Seeder;
use App\Models\Cinema;

class CinemaTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $cinemas = [
            ['name' => 'Sunway Pyramid', 'seat_capacity' => 50],
            ['name' => 'Mid Valley', 'seat_capacity' => 50],
            ['name' => 'Pavilion KL', 'seat_capacity' => 60],
            ['name' => 'IOI City Mall', 'seat_capacity' => 50],
            ['name' => 'Johor Cinema', 'seat_capacity' => 25],
            ['name' => 'Kuching XXI', 'seat_capacity' => 25],
            ['name' => 'Damansara XXI', 'seat_capacity' => 25],
        ];

        foreach($cinemas as $cinema) {
            Cinema::create($cinema);
        }
    }
}
