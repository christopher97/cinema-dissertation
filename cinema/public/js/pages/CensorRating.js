import CensorRatingModel from '../models/CensorRatingModel.js';
import CensorRatingModal from '../components/CensorRatingModal.js'
import CensorRatingService from '../services/CensorRatingService.js';
import { censorRating } from '../states/censorRating.js';
import { httpStatus } from "../Constants.js";
import toast from "../components/toast.common.js"

const app = new Vue({
    el: '#root',
    data() {
        return {
            crState: censorRating.state,
        }
    },
    components: {
        'censor-rating-modal': CensorRatingModal,
        'toast': toast
    },
    mounted: function() {
        this.fetchRatings();
    },
    methods: {
        fetchRatings() {
            CensorRatingService.fetch()
                .then((response) => {
                    if (response.status === httpStatus.OK) {
                        censorRating.addRatings(CensorRatingModel, response.data.censor_rating);
                    }
                }, (error) => {
                    console.log(error);
                })
        },
        editCR(cr) {
            this.crState.censorRatings[this.crState.censorRatings.indexOf(cr)].onedit = true;
        },
        updateCR(cr) {
            CensorRatingService.update(cr, cr.id)
                .then((response) => {
                    this.crState.censorRatings[this.crState.censorRatings.indexOf(cr)].onedit = false;
                    this.$refs.toast.setMessage('Edit Successful');
                    this.$refs.toast.show();
                }, (error) => {
                    this.$refs.toast.setMessage('Error');
                    this.$refs.toast.show();
                })
        }
    }
});