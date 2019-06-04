<template>
  <div>


    <v-alert
      :value="!this.videoMetaData.videoURL"
      type="error"
    >
      <h3>Video Not Found</h3>
      <span> video URL: {{ this.videoMetaData.videoURL }} </span>
    </v-alert>

    <div class="video-wrapper" v-if="this.videoMetaData.videoURL">
      <video
        autoplay
        controls
        width="auto"
        height="auto"
        :src="this.videoMetaData.videoURL"
      ></video>
      <div class="controls">
        <v-layout row wrap align-center>
          <v-flex xs12 sm12>
            <div class="text-xs-center">
              <v-btn color="success" disabled>
                Screenshot
                <v-icon class="ml-2">fa-camera</v-icon>
              </v-btn>

              <v-btn color="blue" dark>
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
  import axios from "axios";

  export default {
    name: 'videoplayer',
    props: {
      videoMetaData: Object
    },
    components: { },
    data() {
      return {};
    },
    created() {},
    destroyed() {},
    mounted() {},
    computed: {},
    watch: {},
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
