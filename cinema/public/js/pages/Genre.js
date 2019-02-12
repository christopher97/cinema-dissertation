import GenreModel from '../models/GenreModel.js';
import GenreModal from '../components/GenreModal.js'
import GenreService from '../services/GenreService.js';
import { genre } from '../states/genre.js';
import { httpStatus } from "../Constants.js";
import toast from "../components/toast.common.js"

const app = new Vue({
    el: '#root',
    data() {
        return {
            genreState: genre.state,
        }
    },
    components: {
        'genre-modal': GenreModal,
        'toast': toast
    },
    mounted: function() {
        this.fetchGenres();
    },
    methods: {
        fetchGenres() {
            GenreService.fetch()
                .then((response) => {
                    if (response.status === httpStatus.OK) {
                        genre.addGenres(GenreModel, response.data.genre);
                    }
                }, (error) => {
                    console.log(error);
                })
        },
        editItem(item) {
            this.genreState.genres[this.genreState.genres.indexOf(item)].onedit = true;
        },
        updateItem(item) {
            GenreService.update(item, item.id)
                .then((response) => {
                    this.genreState.genres[this.genreState.genres.indexOf(item)].onedit = false;
                    this.$refs.toast.setMessage('Edit Successful');
                    this.$refs.toast.show();
                }, (error) => {
                    this.$refs.toast.setMessage('Error');
                    this.$refs.toast.show();
                })
        }
    }
});