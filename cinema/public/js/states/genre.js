import Operator from '../utils/model/operator/index.js';

export const genre = {
    state: {
        genres: []
    },
    addGenre(newGenre) {
        this.state.genres.push(newGenre);
    },
    addGenres(model, newGenres) {
        this.state.genres = Operator.map(model, newGenres);
    }
};