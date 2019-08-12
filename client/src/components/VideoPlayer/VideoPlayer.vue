<template>
  <div>
    <v-container fluid>
      <v-layout row no-wrap >
        <video-tools
          :video-url="videoUrl"
          :video-time="videoTime"
        >
        </video-tools>

        <div class="video-area">
          <v-layout row wrap justify-center pa-0>
            <!-- Alert for no video found -->
            <v-alert
              :value="!this.videoUrl"
              type="error"
            >
              <h3>Video Not Found</h3>
              <span> video: {{ this.videoUrl }} </span>
            </v-alert>

            <!-- video area -->
            <v-flex xs12 v-show="this.videoUrl">
              <video
                autoplay
                width="auto"
                height="auto"
                ref="video"
                id="video"
                :src="this.videoUrl"
                class="custom-video-styles"
                :class="{ 'maximize-video': ifMaximize}"
              ></video>
            </v-flex>
            <!-- Replaced because WFS keeps dying
                       :src="this.videoUrl"
                       src="../assets/videos/vid1.mp4"
            -->
            <!-- mousedown/up are used to listen for slider interactions -->
            <!-- v-model two-way binds to video time, allowing for smooth immediate control -->
            <div class="slider-holder">
              <v-slider
                v-model="videoTime"
                @mousedown="pauseVideo()"
                @mouseup="applyNewSliderValue()"
                :max="videoDuration"
                :thumb-size="24"
                thumb-label="always"
                :step=".001"
              >
              </v-slider>
            </div>

            <!-- video controls -->
            <v-flex xs4 d-flex class="video-controls" :class="{ 'maximize-controls': ifMaximize}">

              <v-btn flat icon large color="" @click="rewindVideo(10)">
                <v-icon class="" color="success">fa-undo</v-icon>
                <span class="skip-duration">10</span>
              </v-btn>

              <v-btn flat icon large color="success" v-if="videoIsPlaying" @click="pauseVideo()">
                <v-icon  class="">fa-pause</v-icon>
              </v-btn>

              <v-btn flat icon large color="success" v-if="!videoIsPlaying" @click="playVideo()">
                <v-icon  class="">fa-play</v-icon>
              </v-btn>

              <v-btn flat icon large color="" @click="fastForwardVideo(10)">
                <v-icon class="" color="success">fa-redo</v-icon>
                <span class="skip-duration">10</span>
              </v-btn>

              <!-- Maximize -->
              <v-btn flat icon large color="success" @click="maximizeVideo()" v-show="!ifMaximize">
                <v-icon class="" color="success">fa-expand</v-icon>
              </v-btn>
            </v-flex>
          </v-layout>
        </div>
      </v-layout>
    </v-container>
</div>

    <!--    <Modal> </Modal>-->
</template>

<script>
import VideoTools from '@/components/VideoPlayer/VideoTools'

export default {
  name: 'VideoPlayer',
  props: {
    videoUrl: String,
    videoName: String
  },
  components: { VideoTools },
  data () {
    return {
      ifMaximize: false,
      videoTime: null,
      videoDuration: null,
      videoIsPlaying: true
    }
  },
  created () {},
  destroyed () {},
  mounted () {
    // Currently only one listener is attached for watching time.
    // Required for slider, ff, and rewind functions to work.
    this.attachListenersToVideo()
    this.attachKeyStrokeListeners()

    // get this now.  Referenced throughout app.
    this.videoElement = document.getElementById('video')
  },
  computed: {},
  watch: {},
  methods: {
    attachKeyStrokeListeners: function () {
      window.addEventListener('keydown', (e) => {
        switch (e.key) {
          case ' ':
            this.videoIsPlaying ? this.pauseVideo() : this.playVideo(); break
          case 'ArrowRight':
            this.fastForwardVideo(10); break
          case 'ArrowLeft':
            this.rewindVideo(10); break
        }
      })
    },
    attachListenersToVideo: function () {
      // Listener can't use this.videoElement due to DOM render timing and digest loops.
      let vid = document.getElementById('video')
      // Use arrow function because this refers to the videoPlayer
      // Basically anonymous scope
      let videoTimerFunction = () => {
        this.videoTime = vid.currentTime

        // This has to be in the listener but we're going to force it to only happen once
        // this.videoDuration = null on init
        if (this.videoDuration === null) {
          this.videoDuration = vid.duration
        }
      }
      vid.ontimeupdate = function () { videoTimerFunction() }
    },
    applyNewSliderValue: function () {
      this.videoElement.currentTime = this.videoTime
      this.playVideo()
    },
    maximizeVideo: function () {
      this.videoElement.requestFullscreen()
    },
    pauseVideo: function () {
      this.videoElement.pause()
      this.videoIsPlaying = !this.videoIsPlaying
    },
    playVideo: function () {
      this.videoElement.play()
      this.videoIsPlaying = !this.videoIsPlaying
    },
    rewindVideo: function (length) {
      const curTime = this.$refs.video.currentTime
      this.videoElement.currentTime = curTime - length
    },
    fastForwardVideo: function (length) {
      const curTime = this.$refs.video.currentTime
      // Flips pause to play
      // Also restarts slider and video to 0:00 and autoplays video
      if (curTime === this.videoDuration) {
        this.videoIsPlaying = false
      }
      this.videoElement.currentTime = this.$refs.video.currentTime + length
    }
  }
}
</script>

<style scoped>
  .video-area {
    width:720px;
    padding:0;
    margin: 0;
  }
  .skip-duration {
    /*margin-left: -5em;*/
    font-size: .7em;
    top:6px;
    position: absolute;
  }
  /*Bumps controls up against video player*/
  .video-controls {
    margin-top: -2.2em;
    /*border:2px solid #424242;*/
    /*border-radius: 0px 0px 12px 12px;*/
  }
  .slider-holder {
    min-width:720px;
    margin-top: -2.1em;
  }
  .v-input--slider {
    margin: 0px !important
  }
  .v-input__slot {
    margin-bottom: 0px !important;
  }
  .cust_v-list {
    padding:0px !important;;
  }
</style>
