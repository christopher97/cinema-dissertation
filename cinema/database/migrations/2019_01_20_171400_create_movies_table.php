<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateMoviesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('movies', function (Blueprint $table) {
            $table->increments('id');
            $table->string('title');
            $table->integer('running_time');
            $table->string('language');
            $table->date('release_date');
            $table->text('synopsis');
            $table->text('image_url');
            $table->string('distributor');
            $table->integer('rating_id')->unsigned();
            $table->integer('ticket_price')->unsigned();
            $table->date('start_date');
            $table->date('end_date');
            $table->timestamps();
        });

        Schema::table('movies', function (Blueprint $table) {
            $table->foreign('rating_id')
                    ->references('id')->on('censor_ratings')
                    ->onDelete('cascade')->onUpdate('cascade');
        });

        Schema::create('play_times', function (Blueprint $table) {
            $table->increments('id');
            $table->integer('movie_id')->unsigned();
            $table->time('time');

            $table->foreign('movie_id')
                    ->references('id')->on('movies')
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
        Schema::dropIfExists('movies');
    }
}
