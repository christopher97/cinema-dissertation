import CensorRatingModel from '../models/CensorRatingModel.js';
import MovieModel from '../models/MovieModel.js';

import CensorRatingService from '../services/CensorRatingService.js';
import MovieService from '../services/MovieService.js';
import PerformanceService from '../services/PerformanceService.js';

import Operator from '../utils/model/operator/index.js';

import { censorRating } from '../states/censorRating.js';
import { httpStatus } from "../Constants.js";
import { movieDetails } from "../states/movieDetails.js";

import toast from "../components/toast.common.js";
import MovieGenre from '../components/MovieGenre.js';
import MovieActor from '../components/MovieActor.js';
import MovieDirector from '../components/MovieDirector.js';
import MoviePlaytime from '../components/MoviePlaytime.js';

const app = new Vue({
    el: '#root',
    data() {
        return {
            movieDetails: movieDetails.state,
            crState: censorRating.state,

            movie: MovieModel,

        }
    },
    components: {
        'toast': toast,
        'movie-genre': MovieGenre,
        'movie-actor': MovieActor,
        'movie-director': MovieDirector,
        'movie-playtime': MoviePlaytime,
    },
    mounted: function() {
        this.fetchCensorRatings();
    },
    methods: {
        fetchCensorRatings() {
            CensorRatingService.fetch()
                .then((response) => {
                    if (response.status === httpStatus.OK) {
                        censorRating.addRatings(CensorRatingModel, response.data.censor_rating);
                    }
                }, (error) => {
                    console.log(error);
                })
        },
        create() {
            let genres = this.movieDetails.genres.filter(item => item.id !== null).map(a => a.id);
            let casts = this.movieDetails.actors.filter(item => item.id !== null).map(a => a.id);
            let directors = this.movieDetails.directors.filter(item => item.id !== null).map(a => a.id);
            let times = this.movieDetails.playtimes.filter(item => item !== null);

            if (!(this.checkPairings(genres, casts, directors) && this.checkPlaytimes(times))) {
                alert('Invalid values found!');
                return;
            }

            let relationship = {
                genres,
                casts,
                directors
            };

            const url = window.location.origin + 'cinema/public/admin/movie';

            MovieService.create(this.movie)
                .then((response) => {
                    this.movie = Operator.single(MovieModel, response.data.movie);
                    MovieService.pair(relationship, this.movie.id)
                        .then((response) => {
                            // set the request body
                            let playtime = {
                                movie_id: this.movie.id,
                                times
                            }
                            // perform request to create playtime and performance
                            PerformanceService.create(playtime)
                                .then((response) => {
                                    console.log(response);
                                    this.$refs.toast.setMessage('Movie Successfully Created!');
                                    this.$refs.toast.setDuration(2000);
                                    this.$refs.toast.show();

                                    setTimeout(function() {
                                        window.location.href = url;
                                    }, 1500);
                                }, (error) => {
                                    console.log(error);
                                });
                        }, (error) => {
                            console.log(error);
                        });
                }, (error) => {
                    console.log(error);
                })
        },
        checkPairings(genres, casts, directors) {
            if (genres.length && casts.length && directors.length)
                return true;
            return false;
        },
        checkPlaytimes(times) {
            if (times.length === 0)
                return false;
            for(let i=0; i<times.length; i++) {
                if ((times[i] < "10:00") || (times[i] > "23:59")) {
                    alert("Invalid playtime value!");
                    return false;
                }
            }
            return true;
        }
    }
});
