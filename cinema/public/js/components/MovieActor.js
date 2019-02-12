import ActorModel from '../models/ActorModel.js';
import ActorService from '../services/ActorService.js';

import { movieDetails } from "../states/movieDetails.js";
import { actor } from '../states/actor.js';
import { httpStatus } from "../Constants.js";

export default {
    name: 'movie-actor',
    template: `
        <div class="box box-info">
            <!-- Box Header -->
            <div class="box-header with-border">
                <h3 class="box-title">Movie Actor(s)</h3>
            </div>

            <!-- Form Start -->
            <form role="form">
                <div class="box-body" v-for="(line, index) in actors" v-bind:key="index">
                    <div class="input-group">
                        <select class="form-control" v-model="actors[index]">
                            <option selected disabled>Select an actor...</option>
                            <option v-for="(actor) in actorState.actors" v-bind:value="actor">
                                {{ actor.name }}
                            </option>
                        </select>
                        <span class="input-group-btn">
                            <button class="btn" id="actor-add" type="button" @click="addLine">
                                <i class="fa fa-plus"></i>
                            </button>
                        </span>
                        <span class="input-group-btn">
                            <button class="btn" id="actor-remove" type="button" @click="removeLine(index)">
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
            actorState: actor.state,
            actors: movieDetails.state.actors,
            blockRemoval: true
        }
    },
    mounted: function() {
        this.fetchActors();
        this.addLine();
    },
    methods: {
        addLine() {
            let checkEmptyLines = this.actors.filter(actor => actor.id === null);

            if (checkEmptyLines.length >=1 && this.actors.length > 0)
                return;

            this.actors.push({
                id: null,
                name: ''
            });

            this.updateBlockRemoval();
        },
        removeLine(id) {
            if (!this.blockRemoval) {
                this.actors.splice(id, 1);
                this.updateBlockRemoval();
            }
        },
        updateBlockRemoval() {
            this.blockRemoval = this.actors.length <= 1;
        },
        fetchActors() {
            ActorService.fetch()
                .then((response) => {
                    if (response.status === httpStatus.OK) {
                        actor.addActors(ActorModel, response.data.actor);
                    }
                }, (error) => {
                    console.log(error);
                })
        }
    }
}