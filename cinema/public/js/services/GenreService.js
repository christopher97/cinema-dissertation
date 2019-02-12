import api from './config.js'

export default {
    url: api + '/genre',
    fetch () {
        return axios.get(this.url);
    },
    find (id) {
        return axios.get(this.url + '/' + id);
    },
    create (params) {
        return axios.post(this.url, params);
    },
    update (params, id) {
        return axios.put(this.url + '/' + id, params);
    }
}