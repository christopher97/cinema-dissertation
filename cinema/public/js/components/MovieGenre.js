import GenreModel from '../models/GenreModel.js';
import GenreService from '../services/GenreService.js';

import { movieDetails } from "../states/movieDetails.js";
import { genre } from '../states/genre.js';
import { httpStatus } from "../Constants.js";

export default {
    name: 'movie-genre',
    template: `
        <div class="box box-info">
            <!-- Box Header -->
            <div class="box-header with-border">
                <h3 class="box-title">Movie Genre(s)</h3>
            </div>

            <!-- Form Start -->
            <form role="form">
                <div class="box-body" v-for="(line, index) in genres" v-bind:key="index">
                    <div class="input-group">
                        <select class="form-control" v-model="genres[index]">
                            <option selected disabled>Select a genre...</option>
                            <option v-for="(genre) in genreState.genres" v-bind:value="genre">
                                {{ genre.name }}
                            </option>
                        </select>
                        <span class="input-group-btn">
                            <button class="btn" id="genre-add" type="button" @click="addLine">
                                <i class="fa fa-plus"></i>
                            </button>
                        </span>
                        <span class="input-group-btn">
                            <button class="btn" id="genre-remove" type="button" @click="removeLine(index)">
                                <i class="fa fa-trash"></i>
                            </button>
                        </span>
                    </div>
                </div>
            </form>
        </div>
    `,
    data() {
        return {
            genreState: genre.state,
            genres: movieDetails.state.genres,
            blockRemoval: true
        }
    },
    mounted: function() {
        this.fetchGenres();
        this.addLine();
    },
    methods: {
        addLine() {
            let checkEmptyLines = this.genres.filter(genre => genre.id === null);

            if (checkEmptyLines.length >=1 && this.genres.length > 0)
                return;

            this.genres.push({
                id: null,
                name: ''
            });

            this.updateBlockRemoval();
        },
        removeLine(id) {
            if (!this.blockRemoval) {
                this.genres.splice(id, 1);
                this.updateBlockRemoval();
            }
        },
        updateBlockRemoval() {
            this.blockRemoval = this.genres.length <= 1;
        },
        fetchGenres() {
            GenreService.fetch()
                .then((response) => {
                    if (response.status === httpStatus.OK) {
                        genre.addGenres(GenreModel, response.data.genre);
                    }
                }, (error) => {
                    console.log(error);
                })
        }
    }
}