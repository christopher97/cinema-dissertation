import Operator from '../utils/model/operator/index.js';

export const actor = {
    state: {
        actors: []
    },
    addActor(newActor) {
        this.state.actors.push(newActor);
    },
    addActors(model, newActors) {
        this.state.actors = Operator.map(model, newActors);
    }
};