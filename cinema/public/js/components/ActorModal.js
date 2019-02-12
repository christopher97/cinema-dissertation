import Modal from './Modal.js';
import Operator from '../utils/model/operator/index.js';
import ActorService from '../services/ActorService.js';
import ActorModel from '../models/ActorModel.js';
import { actor } from '../states/actor.js';

export default {
    name: 'actor-modal',
    extends: Modal,
    data() {
        return {
            item: ActorModel,
        }
    },
    methods: {
        submit() {
            ActorService.create(this.item)
                .then((response) => {
                    this.item = Operator.single(ActorModel, response.data.actor);
                    actor.addActor(this.item);
                    this.item = Operator.reset(ActorModel);
                    this.showSuccess = true;
                }, (error) => {
                    console.log(error);
                })
        }
    },
}