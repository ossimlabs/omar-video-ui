<template>
  <div>
    <!-- mousedown/up are used to listen for slider interactions -->
    <!-- v-model two-way binds to video time, allowing for smooth immediate control -->
    <div class="slider-holder">
      <v-slider v-model="videoTime"
                @mousedown="pauseVideo()"
                @mouseup="applyNewSliderValue()"
                :max="videoDuration"
                :thumb-size="24"
                thumb-label="always">
      </v-slider>
    </div>
    <v-flex xs4 d-flex class="video-controls">

      <v-btn flat icon large color="" @click="rewindVideo(10)">
        <v-icon class="" color="success">fa-undo</v-icon>
        <span class="skip-duration">10</span>
      </v-btn>

      <v-btn flat icon large color="success" v-if="videoIsPlaying"@click="pauseVideo()">
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
      <v-btn flat icon large color="success" @click="maximizeVideo()">
        <v-icon class="" color="success">fa-expand</v-icon>
      </v-btn>

      <!-- Minimize -->
      <!--<v-btn flat icon large color="success" @click="ifMaximize = !ifMaximize" v-show="ifMaximize" class="minimize-button">
        <v-icon class="" color="success">fa-compress</v-icon>
      </v-btn>-->
    </v-flex>
  </div>
</template>

<script>
export default {
  name: 'VideoControls',
  props: {
  },
  data () {
    return {
      videoIsPlaying: true
    }
  },
  created () {},
  destroyed () {},
  mounted () {
    this.attachListenersToVideo()
    this.attachKeyStrokeListeners()
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
  .skip-duration {
    /*margin-left: -5em;*/
    font-size: .7em;
    top:6px;
    position: absolute;
  }
  /*Bumps controls up against video player*/
  .video-controls {
    margin-top: -2.2em;
  }
</style>
