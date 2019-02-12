import DirectorModel from '../models/DirectorModel.js';
import DirectorModal from '../components/DirectorModal.js'
import DirectorService from '../services/DirectorService.js';
import { director } from '../states/director.js';
import { httpStatus } from "../Constants.js";
import toast from "../components/toast.common.js"

const app = new Vue({
    el: '#root',
    data() {
        return {
            directorState: director.state,
        }
    },
    components: {
        'director-modal': DirectorModal,
        'toast': toast
    },
    mounted: function() {
        this.fetchDirectors();
    },
    methods: {
        fetchDirectors() {
            DirectorService.fetch()
                .then((response) => {
                    if (response.status === httpStatus.OK) {
                        director.addDirectors(DirectorModel, response.data.director);
                    }
                }, (error) => {
                    console.log(error);
                })
        },
        editItem(item) {
            this.directorState.directors[this.directorState.directors.indexOf(item)].onedit = true;
        },
        updateItem(item) {
            DirectorService.update(item, item.id)
                .then((response) => {
                    this.directorState.directors[this.directorState.directors.indexOf(item)].onedit = false;
                    this.$refs.toast.setMessage('Edit Successful');
                    this.$refs.toast.show();
                }, (error) => {
                    this.$refs.toast.setMessage('Error');
                    this.$refs.toast.show();
                })
        }
    }
});