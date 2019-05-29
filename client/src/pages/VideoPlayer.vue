<template>
  <div>
    {{ $route.params.id }}
    <br />
    <div class="video-wrapper">
      <video
        autoplay
        controls
        width="auto"
        height="auto"
        :src="require(`../assets/videos/${$route.params.id}`)"
      ></video>
      <!-- static link to video -->
      <!-- https://omar-dev.ossim.io/video/MISP-_42FB6D65_21FEB03000019071saMISP-_HD000999.mp4 -->
      <div class="controls">
        <v-layout row wrap align-center>
          <v-flex xs12 sm12>
            <div class="text-xs-center">
              <v-btn color="success" dark>
                Screenshot
                <v-icon class="ml-2">fa-camera</v-icon>
              </v-btn>

              <v-btn color="blue" dark>
                Download
                <v-icon class="ml-2">fa-download</v-icon>
              </v-btn>

              <v-btn color="green">
                Share
                <v-icon class="ml-2" color="white">fa-share-alt</v-icon>
              </v-btn>

              <v-btn color="green">
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
  // import Modal from '@/components/Modal';

  export default {
    name: 'videoplayer',
    props: {},
    components: { },
    data() {
      return {
        videoUrl: null
        // videos: [{ name: "vid1.mp4" }, { name: "vid2.mp4" }, { name: "vid3.mp4" }]
      };
    },
    created() {
      // this.pullVideos();
    },
    destroyed() {},
    mounted() {},
    computed: {},
    watch: {
      $route(to, from) {
        console.log("to", to, this.videoName);
        this.videoUrl = "../assets/videos/" + to.params.id;
      }
    },
    methods: {
      pullVideos: function() {
        console.log("pulling videos");
        const videoData = axios.create({
          // headers: {
          //   'Content-type': 'application/geo+json',
          //   'Accept': 'application/geo+json',
          //   'Access-Control-Allow-Origin': '*',
          //   'UserAgent': 'Project Bluefire',
          //   'Access-Control-Request-Headers': 'Content-Type',
          // },
          baseURL: "https://omar-dev.ossim.io/omar-stager/",
          params: {
            // status: 'actual',
            // area: 'PA'
          }
        });

        const videoUrl =
          "https://omar-dev.ossim.io/omar-stager/videoStreaming?id=2";
        axios.post(videoUrl).then(res => {
          console.log("res", res);
        });
      }
    }
  };
</script>

<style scoped></style>
