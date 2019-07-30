<template>
  <v-flex xs1>
    <v-layout row wrap d-block class="video-tools" pr-1>
      <!-- video tools -->
      <v-flex>
        <v-btn flat icon color="success" @click="takeScreenshot()">
          <v-icon class="">fa-camera</v-icon>
        </v-btn>
      </v-flex>

      <v-flex>
        <v-btn flat icon color="blue" disabled>
          <v-icon class="">fa-download</v-icon>
        </v-btn>
      </v-flex>

      <v-flex>
        <v-btn flat icon color="success" disabled>
          <v-icon class="" color="white">fa-share-alt</v-icon>
        </v-btn>
      </v-flex>

      <v-flex>
        <v-btn flat icon color="success" disabled>
          <v-icon class="" color="white">fa-star</v-icon>
        </v-btn>
      </v-flex>

    </v-layout>
  </v-flex>
</template>

<script>
import axios from 'axios'
import qs from 'qs'
import FileSaver from 'file-saver'

export default {
  name: 'VideoTools',
  props: {
    videoTime: Number,
    videoUrl: String
  },
  components: { },
  data () {
    return {}
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
        timestamp: this.videoTime,
        videoPath: this.videoUrl
      }

      axios.post(apiUrl, qs.stringify(videoParams), { responseType: 'blob' })
        .then(res => {
          FileSaver.saveAs(res.data, 'omar-video_screenshot_' + this.videoName + '_' + this.videoTime + 's.jpeg')
        })
        .catch(error => {
          console.log('error', error)
        })
    }
  }
}
</script>

<style scoped>
  .video-tools {
    border:2px solid #424242;
    border-radius: 12px 0px 0px 12px;
    margin-right:-.50em
  }
</style>
