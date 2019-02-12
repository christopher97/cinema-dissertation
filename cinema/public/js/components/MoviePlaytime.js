import { movieDetails } from "../states/movieDetails.js"

export default {
    name: 'movie-playtime',
    template: `
        <div class="box box-info">
            <!-- box header -->
            <div class="box-header with-border">
                <h3 class="box-title">Movie Playtime(s)</h3>
            </div>

            <!-- Form Start -->
            <form role="form">
                <div class="box-body">
                    Starts at 10.00 and ends at 23.59
                </div>
                <div class="box-body" v-for="(line, index) in playtimes" v-bind:key="index">
                    <div class="input-group">
                        <input type="time" class="form-control" v-model="playtimes[index]">
                        <span class="input-group-btn">
                            <button class="btn" id="playtime-add" type="button" @click="addLine">
                                <i class="fa fa-plus"></i>
                            </button>
                        </span>
                        <span class="input-group-btn">
                            <button class="btn" id="playtime-remove" type="button" @click="removeLine(index)">
                                <i class="fa fa-trash"></i>
                            </button>
                        </span>
                    </div>
                </div>
            </form>
        </div>
    `,
    data() {
        return {
            blockRemoval: true,
            playtimes: movieDetails.state.playtimes,
        }
    },
    mounted: function() {
        this.addLine();
    },
    methods: {
        addLine() {
            let checkEmptyLines = this.playtimes.filter(playtime => playtime === null);

            if (checkEmptyLines.length >=1 && this.playtimes.length > 0)
                return;

            this.playtimes.push(null);

            this.updateBlockRemoval();
        },
        removeLine(id) {
            if (!this.blockRemoval) {
                this.playtimes.splice(id, 1);
                this.updateBlockRemoval();
            }
        },
        updateBlockRemoval() {
            this.blockRemoval = this.playtimes.length <= 1;
        },
    }
}