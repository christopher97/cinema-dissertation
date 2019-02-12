import Operator from '../utils/model/operator/index.js';

export const cinema = {
    state: {
        cinemas: []
    },
    addCinema(newCinema) {
        this.state.cinemas.push(newCinema);
    },
    addCinemas(model, newCinemas) {
        this.state.cinemas = Operator.map(model, newCinemas);
    }
};