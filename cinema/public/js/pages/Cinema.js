import CinemaModel from '../models/CinemaModel.js';
import CinemaModal from '../components/CinemaModal.js'
import CinemaService from '../services/CinemaService.js';
import { cinema } from '../states/cinema.js';
import { httpStatus } from "../Constants.js";
import toast from "../components/toast.common.js"

const app = new Vue({
    el: '#root',
    data() {
        return {
            cinemaState: cinema.state,
        }
    },
    components: {
        'cinema-modal': CinemaModal,
        'toast': toast
    },
    mounted: function() {
        this.fetchCinemas();
    },
    methods: {
        fetchCinemas() {
            CinemaService.fetch()
                .then((response) => {
                    if (response.status === httpStatus.OK) {
                        cinema.addCinemas(CinemaModel, response.data.cinema);
                    }
                }, (error) => {
                    console.log(error);
                })
        },
        editItem(item) {
            this.cinemaState.cinemas[this.cinemaState.cinemas.indexOf(item)].onedit = true;
        },
        updateItem(item) {
            CinemaService.update(item, item.id)
                .then((response) => {
                    this.cinemaState.cinemas[this.cinemaState.cinemas.indexOf(item)].onedit = false;
                    this.$refs.toast.setMessage('Edit Successful');
                    this.$refs.toast.show();
                }, (error) => {
                    this.$refs.toast.setMessage('Error');
                    this.$refs.toast.show();
                })
        }
    }
});