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

      <v-list dense class="" >
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

        <v-list-tile ripple v-for="(entry, key) in videoResp.videoDataSet" :key="key">
          <v-list-tile-title>
            {{ key }} :
          </v-list-tile-title>

          <v-list-tile-title class="green--text">
          {{ entry }}
          </v-list-tile-title>

        </v-list-tile>

        <!--<v-subheader class="mt-3 grey&#45;&#45;text text&#45;&#45;darken-1">
          Demo/Test Videos
        </v-subheader>
        <v-list>
          <v-list-tile
            class="ma-4"
            v-for="video in videos"
            :key="video.name"
            avatar
          >
            <v-list-tile-avatar class="mr-3">
              <video
                autoplay
                width="85px"
                height="85px"
                :src="require(`./assets/videos/${video.name}`)"
              ></video>
            </v-list-tile-avatar>

            <v-list-tile-title
              v-text="video.name"
              class="green&#45;&#45;text"
            ></v-list-tile-title>
          </v-list-tile>
        </v-list>-->
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
    <v-content>
      <v-container fill-height>
        <v-layout justify-center align-center>
          <v-flex shrink>
            <video-player
            :videoMetaData="videoMetaData"
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
import axios from 'axios'
import VideoPlayer from '@/pages/VideoPlayer'

export default {
  name: 'App',
  props: {},
  components: {VideoPlayer},
  data () {
    return {
      loading: false,
      drawer: null,
      videoResp: {},
      videoMetaData: {},
      videos: [
        { name: 'vid1.mp4' },
        { name: 'vid2.mp4' },
        { name: 'vid3.mp4' }
      ],
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
  watch: {},
  methods: {
    fetchData: function () {
      // needed because of axios scope
      let self = this

      // this.error = this.videoResp = null
      this.loading = true

      const param = this.$route.params.id
      console.log('param', param)
      // const apiUrlProd = 'https://omar-dev.ossim.io/omar-services/videoStreaming?id=' + param
      const apiUrl = 'http://localhost:8081/omar-services/videoStreaming?id=' + param

      axios.post(apiUrl)
        .then(res => {
          this.loading = false
          self.videoResp = this.videoMetaData = res.data
          console.log('this.videoMetaData', this.videoMetaData)
        })
        .catch(error => {
          console.log('Error', error)
        })
        .finally(function () {

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
