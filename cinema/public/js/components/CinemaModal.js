import Operator from '../utils/model/operator/index.js';
import CinemaService from '../services/CinemaService.js';
import CinemaModel from '../models/CinemaModel.js';
import { cinema } from '../states/cinema.js';

export default {
    name: 'cinema-modal',
    props: {
        title: String,
    },
    template:`
        <div class="modal fade" tabindex="-1" role="dialog" id="addModal" aria-labelledby="modelLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h5 class="modal-title">Add New {{title}}</h5>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <input class="form-control" type="text" v-bind:placeholder="title + ' name'"
                                    v-model="item.name">
                        </div>
                        <div class="form-group">
                            <input class="form-control" type="text" placeholder="Seat Capacity"
                                    v-model="item.seat_capacity">
                        </div>
                        <p v-show="showSuccess" class="text-success">{{title}} successfully added!</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" 
                            v-on:click="submit">Submit</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal"
                            @click="showSuccess=false">Close</button>
                    </div>
                </div>
            </div>
        </div>
    `,
    data() {
        return {
            item: CinemaModel,
            showSuccess: false,
        }
    },
    methods: {
        submit() {
            CinemaService.create(this.item)
                .then((response) => {
                    this.item = Operator.single(CinemaModel, response.data.cinema);
                    cinema.addCinema(this.item);
                    this.item = Operator.reset(CinemaModel);
                    this.showSuccess = true;
                }, (error) => {
                    console.log(error);
                })
        }
    },
}