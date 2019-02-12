import MovieModel from '../models/MovieModel.js'
import MovieService from '../services/MovieService.js';
import Operator from '../utils/model/operator/index.js';

import { httpStatus } from "../Constants.js";

const app = new Vue({
    el: '#root',
    data() {
        return {
            movies: []
        }
    },
    mounted: function() {
        this.fetchMovies()
    },
    methods: {
        fetchMovies() {
            MovieService.fetch()
                .then((response) => {
                    if (response.status === httpStatus.OK) {
                        this.movies = Operator.map(MovieModel, response.data.movie);
                    }
                }, (error) => {
                    console.log(error);
                })
        }
    }
})