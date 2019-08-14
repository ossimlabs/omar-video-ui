<template>
  <v-app id="inspire" dark>
    <!-- DRAWER -->
    <v-navigation-drawer v-model="drawer" fixed clipped app >
      <v-container class="mt-3">
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
    <!-- Banner -->
    <security-banner :security-classification=globalConfig.securityClassification></security-banner>

    <!-- Top Toolbar -->
    <v-toolbar color="#8a9196" class="mt-4" dense fixed clipped-left app>
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
      <v-spacer></v-spacer>
      <span v-if="globalConfig.userName">
        {{ globalConfig.userName ? globalConfig.userName : 'No PKI' }}
      </span>

        <v-btn icon>
          <v-icon>fa-user</v-icon>
        </v-btn>

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
import VideoPlayer from '@/components/VideoPlayer/VideoPlayer'
import SecurityBanner from "@/components/SecurityBanner/SecurityBanner";

// Libraries / Packages
import axios from 'axios'
import qs from 'qs'

export default {
  name: 'App',
  props: {},
  components: { SecurityBanner, VideoPlayer },
  data () {
    return {
      globalConfig: {},
      loading: false,
      drawer: null,
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
    this.fetchConfig()
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
    fetchConfig: function () {
      const pkiEndpoint = `https://pki-${location.hostname}`

      console.log('url', location.hostname)
      console.log('${process.env.SERVER_URL}', process.env.SERVER_URL)
      console.log('pkiEndpoint', pkiEndpoint)


      axios.get(pkiEndpoint)
        .then(res => {
          if (res.request.withCredentials){
            console.log('has creds!')
          }
          this.globalConfig = res.data
          console.log('res', res)
        })
      // let contentType = ''
      // fetch(`${process.env.SERVER_URL}/restApi`)
      //   .then((res) => {
      //     contentType = res.headers.get();
      //     return res.json()
      //   })
      //   .then(globalConfig => {
      //     this.globalConfig = globalConfig
      //     console.log('this.globalConfig', this.globalConfig)
      //     console.log('contentType', contentType)
      //   })
    },
    fetchData: function () {
      this.loading = true

      // grab the query parameters to get the search filter
      // Value used for http querystring to WFS
      let urlParams = new URLSearchParams(window.location.search)
      let filter = urlParams.get('filter')

      // WFS Redirect
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
          this.videoMetaData = res.data
        })
        .catch(error => {
          console.log('error', error)
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
