<?php

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        // $this->call(UsersTableSeeder::class);
        $this->call(AdminTableSeeder::class);
        $this->call(ActorTableSeeder::class);
        $this->call(CinemaTableSeeder::class);
        $this->call(DirectorTableSeeder::class);
        $this->call(GenreTableSeeder::class);
        $this->call(CensorRatingTableSeeder::class);
    }
}
