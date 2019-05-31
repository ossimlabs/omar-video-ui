<template>
  <div>
    {{ $route.params.id }} <br />
    video URL: {{ this.videoResp }} <br />

    <div v-if="loading" class="loading">
      Loading...
    </div>

    <div class="video-wrapper">
      <video
        v-if="videoResp"
        autoplay
        controls
        width="auto"
        height="auto"
        :src="this.videoResp.videoURL"
      ></video>

      <!--<video
        autoplay
        controls
        width="auto"
        height="auto"
        :src="require(`../assets/videos/${$route.params.id}`)"
      ></video>-->
      <!-- static link to video -->
      <!-- https://omar-dev.ossim.io/video/MISP-_42FB6D65_21FEB03000019071saMISP-_HD000999.mp4 -->
      <div class="controls">
        <v-layout row wrap align-center>
          <v-flex xs12 sm12>
            <div class="text-xs-center">
              <v-btn color="success" disable>
                Screenshot
                <v-icon class="ml-2">fa-camera</v-icon>
              </v-btn>

              <v-btn color="blue" dark>
                Download
                <v-icon class="ml-2">fa-download</v-icon>
              </v-btn>

              <v-btn color="success" disable>
                Share
                <v-icon class="ml-2" color="white">fa-share-alt</v-icon>
              </v-btn>

              <v-btn color="success" disable>
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
  import axios from "axios";

  export default {
    name: 'videoplayer',
    props: {},
    components: { },
    data() {
      return {
        loading: false,
        error: null,
        videoUrl: null,
        videoResp: null,
      };
    },
    created() {
      this.fetchData();
    },
    destroyed() {},
    mounted() {},
    computed: {},
    watch: {
      // call again the method if the route changes
      '$route': 'fetchData'
      // $route(to, from) {
      //   console.log("to", to, this.videoName);
      //   this.videoUrl = "../assets/videos/" + to.params.id;
      // }
    },
    methods: {
      fetchData: function() {
        // needed because of axios scope
        let self = this;

        // this.error = this.videoResp = null
        this.loading = true

        const param = this.$route.params.id
        const apiUrl = 'http://localhost:8081/omar-services/videoStreaming?id=' + param ;

        axios.post(apiUrl)
          .then(res => {
            this.loading = false
            self.videoResp = res.data
            this.$emit.videoMetaData = res.data
            console.log('res.data', res.data)
          })
          .catch(error => {
              console.log(error);
          })
      }
    }
  };
</script>

<style scoped></style>
