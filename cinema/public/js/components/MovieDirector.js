import DirectorModel from '../models/DirectorModel.js';
import DirectorService from '../services/DirectorService.js';

import { movieDetails } from "../states/movieDetails.js";
import { director } from '../states/director.js';
import { httpStatus } from "../Constants.js";

export default {
    name: 'movie-director',
    template: `
        <div class="box box-info">
            <!-- Box Header -->
            <div class="box-header with-border">
                <h3 class="box-title">Movie Director(s)</h3>
            </div>

            <!-- Form Start -->
            <form role="form">
                <div class="box-body" v-for="(line, index) in directors" v-bind:key="index">
                    <div class="input-group">
                        <select class="form-control" v-model="directors[index]">
                            <option selected disabled>Select a director...</option>
                            <option v-for="(director) in directorState.directors" v-bind:value="director">
                                {{ director.name }}
                            </option>
                        </select>
                        <span class="input-group-btn">
                            <button class="btn" id="director-add" type="button" @click="addLine">
                                <i class="fa fa-plus"></i>
                            </button>
                        </span>
                        <span class="input-group-btn">
                            <button class="btn" id="director-remove" type="button" @click="removeLine(index)">
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
            directorState: director.state,
            directors: movieDetails.state.directors,
            blockRemoval: true
        }
    },
    mounted: function() {
        this.fetchDirectors();
        this.addLine();
    },
    methods: {
        addLine() {
            let checkEmptyLines = this.directors.filter(director => director.id === null);

            if (checkEmptyLines.length >=1 && this.directors.length > 0)
                return;

            this.directors.push({
                id: null,
                name: ''
            });

            this.updateBlockRemoval();
        },
        removeLine(id) {
            if (!this.blockRemoval) {
                this.directors.splice(id, 1);
                this.updateBlockRemoval();
            }
        },
        updateBlockRemoval() {
            this.blockRemoval = this.directors.length <= 1;
        },
        fetchDirectors() {
            DirectorService.fetch()
                .then((response) => {
                    if (response.status === httpStatus.OK) {
                        director.addDirectors(DirectorModel, response.data.director);
                    }
                }, (error) => {
                    console.log(error);
                })
        }
    }
}