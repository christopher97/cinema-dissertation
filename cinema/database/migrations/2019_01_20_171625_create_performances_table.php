<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreatePerformancesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('performances', function (Blueprint $table) {
            $table->increments('id');
            $table->unsignedInteger('play_time_id');
            $table->unsignedInteger('cinema_id');
            $table->date('date');
            $table->unsignedInteger('total_seat');
            $table->unsignedInteger('booked_seat');
            $table->timestamps();
        });

        Schema::table('performances', function (Blueprint $table) {
            $table->foreign('play_time_id')
                    ->references('id')->on('play_times')
                    ->onDelete('cascade')->onUpdate('cascade');

            $table->foreign('cinema_id')
                    ->references('id')->on('cinemas')
                    ->onDelete('cascade')->onUpdate('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('performances');
    }
}
