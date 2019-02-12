import Operator from '../utils/model/operator/index.js';

export const director = {
    state: {
        directors: []
    },
    addDirector(newDirector) {
        this.state.directors.push(newDirector);
    },
    addDirectors(model, newDirectors) {
        this.state.directors = Operator.map(model, newDirectors);
    }
};