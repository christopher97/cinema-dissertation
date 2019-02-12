import ActorModel from '../models/ActorModel.js';
import ActorModal from '../components/ActorModal.js'
import ActorService from '../services/ActorService.js';
import { actor } from '../states/actor.js';
import { httpStatus } from "../Constants.js";
import toast from "../components/toast.common.js"

const app = new Vue({
    el: '#root',
    data() {
        return {
            actorState: actor.state,
        }
    },
    components: {
        'actor-modal': ActorModal,
        'toast': toast
    },
    mounted: function() {
        this.fetchActors();
    },
    methods: {
        fetchActors() {
            ActorService.fetch()
                .then((response) => {
                    if (response.status === httpStatus.OK) {
                        actor.addActors(ActorModel, response.data.actor);
                    }
                }, (error) => {
                    console.log(error);
                })
        },
        editActor(actor) {
            this.actorState.actors[this.actorState.actors.indexOf(actor)].onedit = true;
        },
        updateActor(actor) {
            ActorService.update(actor, actor.id)
                .then((response) => {
                    this.actorState.actors[this.actorState.actors.indexOf(actor)].onedit = false;
                    this.$refs.toast.setMessage('Edit Successful');
                    this.$refs.toast.show();
                }, (error) => {
                    this.$refs.toast.setMessage('Error');
                    this.$refs.toast.show();
                })
        }
    }
});