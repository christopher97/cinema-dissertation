import Operator from '../utils/model/operator/index.js';

export const censorRating = {
    state: {
        censorRatings: []
    },
    addRating(newRating) {
        this.state.censorRatings.push(newRating);
    },
    addRatings(model, newRatings) {
        this.state.censorRatings = Operator.map(model, newRatings);
    }
};