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

export default {
  name: 'videoplayer',
  props: {
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
      // Params for window.open placement
      const strWindowFeatures = 'left=800,bottom=600,width=100,height=100,'

      const videoParams = {
        name: 'screeny',
        timestamp: '00:00:20',
        videoPath: 'http://localhost/videos/MISP-_42FB6DA1_21FEB03000019081saMISP-_HD000999.mp4'
      }

      // const apiUrlOld = 'http://localhost:8081/omar-services/videoStreaming/takeScreenshot'
      // const apiUrl = 'http://localhost:8080/screenshot/takeScreenshot'

      const wfsUrl = 'http://localhost:8080/proxy'
      const wfsParams = {
        url: 'https://omar-dev.ossim.io/omar-wfs/wfs',
        service: 'WFS',
        version: '1.1.0',
        request: 'GetFeature',
        typeName: 'omar:video_data_set',
        filter: 'filename%20like%20%27%25MISP-_42FB6D65_21FEB03000019071saMISP-_HD000999%25%27',
        resultType: 'results',
        outputFormat: 'JSON'
      }

      axios.get(wfsUrl, qs.stringify(wfsParams))
        .then(res => {
          console.log('res.data', res)
        })
        .catch(error => {
          console.log(error)
        })

      // const finalUrl = apiUrl + '?' + qs.stringify(screenshotParams, { encode: false })

      /* axios.post(apiUrl, qs.stringify(videoParams))
        .then(res => {
          console.log('res.data', res)
          window.open(
            'http://localhost:8080/screenshot/displayScreenshot?filePath=/opt/local/www/apache2/html/screenshots/00:00:17.jpg&timestamp=17',
            'downloadWindow',
            strWindowFeatures)
        })
        .catch(error => {
          console.log(error)
        }) */
    }
  }
}
</script>

<style scoped></style>
