<template>
  <div>

    <v-alert
      :value="!this.videoUrl"
      type="error"
    >
      <h3>Video Not Found</h3>
      <span> video: {{ this.videoUrl }} </span>
    </v-alert>

    <div class="video-wrapper" v-if="this.videoUrl">
      <video
        autoplay
        controls
        width="auto"
        height="auto"
        ref="video"
        :src="this.videoUrl"
      ></video>
      <div class="controls">
        <v-layout row wrap align-center>
          <v-flex xs12 sm12>
            <div class="text-xs-center">
              <v-btn color="success" @click="takeScreenshot()">
                Screenshot
                <v-icon class="ml-2">fa-camera</v-icon>
              </v-btn>

              <v-btn color="blue" disabled>
                Download
                <v-icon class="ml-2">fa-download</v-icon>
              </v-btn>

              <v-btn color="success" disabled>
                Share
                <v-icon class="ml-2" color="white">fa-share-alt</v-icon>
              </v-btn>

              <v-btn color="success" disabled>
                Favorite
                <v-icon class="ml-2" color="white">fa-star</v-icon>
              </v-btn>

              <!--              <template v-slot:activator="{ on }">-->
              <!-- MODAL BUTTON -->
              <!--<v-btn color="red lighten-2" dark v-on="on">
                Click Me
              </v-btn>-->
              <!--              </template>-->
            </div>
          </v-flex>
        </v-layout>
      </div>
    </div>

    <!--    <Modal> </Modal>-->
  </div>
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
    return {}
  },
  created () {},
  destroyed () {},
  mounted () {},
  computed: {},
  watch: {},
  methods: {
    takeScreenshot: function () {
      console.log('env: ', process.env)

      const apiUrl = `${process.env.SERVER_URL}/screenshot/takeScreenshot`
      console.log('apiUrl', apiUrl)

      const videoParams = {
        timestamp: this.$refs.video.currentTime,
        videoPath: this.videoUrl
      }

      console.log('params', qs.stringify(videoParams))
      axios.post(apiUrl, qs.stringify(videoParams), { responseType: 'blob' })
        .then(res => {
          console.log('res', res)
          FileSaver.saveAs(res.data, 'omar-video_screenshot_' + this.videoName + '_' + this.$refs.video.currentTime + 's.jpeg')
        })
        .catch(error => {
          console.log(error)
        })
    }
  }
}
</script>

<style scoped></style>
