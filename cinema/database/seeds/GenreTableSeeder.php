<?php

use Illuminate\Database\Seeder;
use App\Models\Genre;

class GenreTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $genres = [
            ['name' => 'Drama'],
            ['name' => 'Thriller'],
            ['name' => 'Western'],
            ['name' => 'Comedy'],
            ['name' => 'Music'],
            ['name' => 'Horror'],
            ['name' => 'Science Fiction'],
            ['name' => 'Adventure'],
            ['name' => 'Action'],
            ['name' => 'Documentary'],
            ['name' => 'Animation'],
            ['name' => 'History'],
            ['name' => 'Crime'],
            ['name' => 'Fantasy'],
            ['name' => 'Mystery'],
        ];

        foreach($genres as $genre) {
            Genre::create($genre);
        }
    }
}
