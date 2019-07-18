<template>
  <v-app id="inspire" dark>
    <!-- DRAWER -->
    <v-navigation-drawer v-model="drawer" fixed clipped app >
      <v-container >
        <v-layout>
          <v-flex xs12>
            <v-btn
              block
              disabled
              color="transparent"
              v-for="button in buttons"
              :key="button.text"
            >
              <v-icon class="mr-2">{{ button.icon }} </v-icon> {{ button.text }}
            </v-btn>
          </v-flex>
        </v-layout>
      </v-container>

      <v-list dense v-if="videoMetaData" >
        <!--<v-list-tile v-for="button in buttons" :key="button.text" @click="">
          <v-list-tile-action>
            <v-icon disabled>{{ button.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title >
              {{ button.text }}
            </v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>-->

        <!-- META DATA -->
        <v-subheader class="mt-3 grey--text text--darken-1">
          Meta Data
        </v-subheader>

        <!-- Will not work until videoDataSet is array -->
        <!--<v-data-iterator
          :items="videoResp.videoDataSet"
          row
          wrap
        >
        </v-data-iterator>-->

        <v-list-tile ripple v-for="(entry, key) in videoMetaData.features[0].properties" :key="key">
          <v-list-tile-title>
            {{ key }} :
          </v-list-tile-title>

          <v-list-tile-title class="green--text">
          {{ entry }}
          </v-list-tile-title>
        </v-list-tile>
      </v-list>
    </v-navigation-drawer>

    <!-- HEADER -->
    <v-toolbar color="#8a9196" dense fixed clipped-left app>
      <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
      <img
        src="./assets/images/o2-logo.png"
        class="ml-1 logo"
        @click="$router.push('/')"
      />

      <v-toolbar-title class="align-self-end mb-1">
        <span class="title align-baseline">Video</span>
        <span class="font-italic caption ml-2"> The youtube of imagery</span>
      </v-toolbar-title>
    </v-toolbar>

    <!-- MAIN CONTENT AREA -->
    <v-content app>
      <v-container fill-height>
        <v-layout justify-center align-center>
          <v-flex shrink>
            <video-player
            :videoUrl="videoUrl"
            :videoName="videoName"
            >

            </video-player>
          <!-- <router-view></router-view>-->
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
// Components
import VideoPlayer from '@/pages/VideoPlayer'

// Libraries / Packages
import axios from 'axios'
import qs from 'qs'

export default {
  name: 'App',
  props: {},
  components: {VideoPlayer},
  data () {
    return {
      loading: false,
      drawer: null,
      videoResp: {},
      videoMetaData: null,
      videoUrl: null,
      videoName: null,
      buttons: [
        { icon: 'fa-fire', text: 'Most Popular' },
        { icon: 'fa-history', text: 'History' },
        { icon: 'fa-save', text: 'Saved' },
        { icon: 'fa-share', text: 'Shared' }
      ]
    }
  },
  created () {
    this.fetchData()
  },
  destroyed () {},
  mounted () {},
  computed: {},
  watch: {
    '$route' (to, from) {
      // react to route changes...
    }
  },
  methods: {
    fetchData: function () {
      // needed because of axios scope
      let self = this
      this.loading = true

      // grab the query parameters to get the search filter
      // Value used for http querystring to WFS

      let urlParams = new URLSearchParams(window.location.search)
      let filter = urlParams.get('filter')


      // WFS Redirect
      // const proxy = 'http://localhost:8080/proxy'
      const wfsUrl = 'https://omar-dev.ossim.io/omar-wfs/wfs?'
      const wfsParams = {
        service: 'WFS',
        version: '1.1.0',
        request: 'GetFeature',
        typeName: 'omar:video_data_set',
        filter: filter,
        resultType: 'results',
        outputFormat: 'JSON'
      }

      const url = wfsUrl + qs.stringify(wfsParams)

      axios.get(url)
        .then(res => {
          // Strip everything away leaving filename
          // Because regex is the devil and this is cleaner
          // split divides url by /, pop returns last, replace modifies filetype
          const videoNameMp4 = res.data.features[0].properties.filename.split('/').pop().replace(/mpg/i, 'mp4')

          // Create a short file name (no file extension)
          // used for screenshot naming
          this.videoName = videoNameMp4.split('.').slice(0, -1).join('.')

          // Build final url and append to response keeping unified object intact
          res.data.features[0].properties.videoUrl = this.videoUrl = 'https://omar-dev.ossim.io/videos/' + videoNameMp4
          self.videoResp = this.videoMetaData = res.data

        })
        .catch(error => {
          console.log(error)
        })
    }
  }

}
</script>

<style>
  #app {
    font-family: "Avenir", Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }
  #nav {
    padding: 30px;
  }

  #nav a {
    font-weight: bold;
    color: #2c3e50;
  }

  #nav a.router-link-exact-active {
    color: #42b983;
  }
  .logo {
    width: 40px;
    height: 40px;
  }
</style>
