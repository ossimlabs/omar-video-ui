<template>
  <div>
    <v-container >
      <v-layout row wrap align-center align-content-center justify-center>

        <!-- Alert for no video found -->
        <v-alert
          :value="!this.videoUrl"
          type="error"
        >
          <h3>Video Not Found</h3>
          <span> video: {{ this.videoUrl }} </span>
        </v-alert>

        <!-- video area -->
        <v-flex xs12 sm12 class="video-wrapper text-xs-center" v-if="this.videoUrl">
          <video
            autoplay
            width="auto"
            height="auto"
            ref="video"
            :src="this.videoUrl"
            :class="{ 'maximize-video': ifMaximize}"
          ></video>
        </v-flex>

        <div class="slider-holder">
          <v-slider v-model="slider"
                    :thumb-size="24"
                    thumb-label="always">

          </v-slider>
        </div>

        <!-- video controls -->
        <v-flex xs12 shrink text-xs-center class="video-controls" :class="{ 'maximize-controls': ifMaximize}">
            <v-btn flat icon large color="" @click="">
              <v-icon  class="">fa-pause</v-icon>
            </v-btn>

            <v-btn flat icon large color="">
              <v-icon class="">fa-backward</v-icon>
            </v-btn>

            <v-btn flat icon large color="">
              <v-icon class="" color="white">fa-forward</v-icon>
            </v-btn>

            <!-- Maximize -->
            <v-btn icon large color="" @click="ifMaximize = !ifMaximize" v-show="!ifMaximize">
              <v-icon class="" color="white">fa-expand</v-icon>
            </v-btn>

            <!-- Minimize -->
            <v-btn icon large color="" @click="ifMaximize = !ifMaximize" v-show="ifMaximize" class="minimize-button">
              <v-icon class="" color="white">fa-compress</v-icon>
            </v-btn>
        </v-flex>

        <!-- video tools -->
        <v-flex xs12 sm12 class="video-tools text-xs-center ma-0 pa-0">
          <v-btn flat icon color="success" @click="takeScreenshot()">
            <v-icon class="">fa-camera</v-icon>
          </v-btn>

          <v-btn flat icon color="blue" disabled>
            <v-icon class="">fa-download</v-icon>
          </v-btn>

          <v-btn flat icon color="success" disabled>
            <v-icon class="" color="white">fa-share-alt</v-icon>
          </v-btn>

          <v-btn flat icon color="success" disabled>
            <v-icon class="" color="white">fa-star</v-icon>
          </v-btn>
        </v-flex>

      </v-layout>


    </v-container>
</div>

    <!--    <Modal> </Modal>-->
</template>

<script>
import axios from 'axios'
import qs from 'qs'
import FileSaver from 'file-saver'

export default {
  name: 'videoplayer',
  props: {
    videoUrl: String,
    videoName: String
  },
  components: { },
  data () {
    return {
      ifMaximize: false
    }
  },
  created () {},
  destroyed () {},
  mounted () {},
  computed: {},
  watch: {},
  methods: {
    takeScreenshot: function () {
      const apiUrl = `${process.env.SERVER_URL}/screenshot`

      const videoParams = {
        timestamp: this.$refs.video.currentTime,
        videoPath: this.videoUrl
      }

      axios.post(apiUrl, qs.stringify(videoParams), { responseType: 'blob' })
        .then(res => {
          FileSaver.saveAs(res.data, 'omar-video_screenshot_' + this.videoName + '_' + this.$refs.video.currentTime + 's.jpeg')
        })
        .catch(error => {
          console.log('error', error)
        })
    }
  }
}
</script>

<style scoped>
  /*.minimize-button {*/
  /*  z-index: 101;*/
  /*  background-color: white;*/
  /*}*/

  /*Bumps controls up against video player*/
  .video-controls {
    margin-top: -3em;
  }
  .maximize-controls {
    position:fixed;
    bottom: 0;
    z-index: 3;
    left: 50%;
    transform: translate(-50%, 0);
  }
  .maximize-video {
    position: fixed; right: 0; bottom: 0;
    min-width: 100%; min-height: 100%;
    width: auto; height: auto; z-index: 2;
  }
  .slider-holder {
    width:720px;
    margin-top: -2em;
  }
  .v-input--slider {
    margin: 0px !important
  }
  .v-input__slot {
    margin-bottom: 0px !important;
  }
</style>
