<?php

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;

class AdminTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('admins')->insert([
            'fullname'  => 'Christopher Lychealdo',
            'email'     => 'christopherlychealdo@yahoo.com',
            'password'  => Hash::make('adminadmin'),
        ]);
    }
}
