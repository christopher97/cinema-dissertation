import Modal from './Modal.js';
import Operator from '../utils/model/operator/index.js';
import GenreService from '../services/GenreService.js';
import GenreModel from '../models/GenreModel.js';
import { genre } from '../states/genre.js';

export default {
    name: 'genre-modal',
    extends: Modal,
    data() {
        return {
            item: GenreModel,
        }
    },
    methods: {
        submit() {
            GenreService.create(this.item)
                .then((response) => {
                    this.item = Operator.single(GenreModel, response.data.genre);
                    genre.addGenre(this.item);
                    this.item = Operator.reset(GenreModel);
                    this.showSuccess = true;
                }, (error) => {
                    console.log(error);
                })
        }
    },
}