import Operator from '../utils/model/operator/index.js';

export const movieDetails = {
    state: {
        genres: [],
        actors: [],
        directors: [],
        playtimes: [],
    },
    addGenre(item) {
        this.state.genres.push(item);
    },
    addActors(item) {
        this.state.actors.push(item);
    },
    addDirectors(item) {
        this.state.directors.push(item);
    },
    addPlaytime(item) {
        this.state.playtimes.push(item);
    },
    removeGenre(itemID) {
        this.state.genres.splice(itemID, 1);
    },
    removeActor(itemID) {
        this.state.actors.splice(itemID, 1);
    },
    removeDirector(itemID) {
        this.state.directors.splice(itemID, 1);
    },
    removePlaytime(itemID) {
        this.state.playtimes.splice(itemID, 1);
    },
};