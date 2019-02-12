import Operator from '../utils/model/operator/index.js';
import CensorRatingService from '../services/CensorRatingService.js';
import CensorRatingModel from '../models/CensorRatingModel.js';
import { censorRating } from '../states/censorRating.js';

export default {
    name: 'censor-rating-modal',
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
                            <input class="form-control" type="text" v-bind:placeholder="title + ' Description'"
                                    v-model="item.description">
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
            item: CensorRatingModel,
            showSuccess: false,
        }
    },
    methods: {
        submit() {
            CensorRatingService.create(this.item)
                .then((response) => {
                    this.item = Operator.single(CensorRatingModel, response.data.censor_rating);
                    censorRating.addRating(this.item);
                    this.item = Operator.reset(CensorRatingModel);
                    this.showSuccess = true;
                }, (error) => {
                    console.log(error);
                })
        }
    },
}