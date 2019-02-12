import Modal from './Modal.js';
import Operator from '../utils/model/operator/index.js';
import DirectorService from '../services/DirectorService.js';
import DirectorModel from '../models/DirectorModel.js';
import { director } from '../states/director.js';

export default {
    name: 'director-modal',
    extends: Modal,
    data() {
        return {
            item: DirectorModel,
        }
    },
    methods: {
        submit() {
            DirectorService.create(this.item)
                .then((response) => {
                    this.item = Operator.single(DirectorModel, response.data.director);
                    director.addDirector(this.item);
                    this.item = Operator.reset(DirectorModel);
                    this.showSuccess = true;
                }, (error) => {
                    console.log(error);
                })
        }
    },
}