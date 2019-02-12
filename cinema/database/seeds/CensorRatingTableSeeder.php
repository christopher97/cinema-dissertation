<?php

use Illuminate\Database\Seeder;
use App\Models\CensorRating;

class CensorRatingTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $censorRatings = [
            ['description' => '18+ Contains Adult Material'],
            ['description' => '13+ Suitable for teens'],
            ['description' => 'E Everyone'],
        ];

        foreach($censorRatings as $cr) {
            CensorRating::create($cr);
        }
    }
}
