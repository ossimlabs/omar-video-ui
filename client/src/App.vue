<template>
  <v-app id="inspire" dark>
    <!-- DRAWER -->
    <v-navigation-drawer v-model="drawer" fixed clipped app>
      <v-list dense class="pt-3">
        <v-list-tile v-for="button in buttons" :key="button.text" @click="">
          <v-list-tile-action>
            <v-icon>{{ button.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>
              {{ button.text }}
            </v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>

        <!-- META DATA -->
        <v-subheader class="mt-3 grey--text text--darken-1">
          Meta Data
        </v-subheader>

        <v-list v-model="videoMetaData">
          {{ this.videoMetaData }}
        </v-list>


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
        height="40px"
        width="40px"
        class="ml-1"
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
            <router-view></router-view>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
  export default {
    name: "App",
    components: {},
    data() {
      return {
        drawer: null,
        videoName: 'this is the videoName',
        videos: [
          { name: "vid1.mp4" },
          { name: "vid2.mp4" },
          { name: "vid3.mp4" }
        ],
        buttons: [
          { icon: "fa-fire", text: "Most Popular" },
          { icon: "fa-history", text: "History" },
          { icon: "fa-save", text: "Saved" },
          { icon: "fa-share", text: "Shared" }
        ],
        thumbnails: [
          {
            username: "erondu",
            text: "Video about a thing following a thing and then more things."
          },
          { username: "jeremybishop", text: "Some goats." },
          {
            username: "turner_imagery",
            text: "Not so grainy video but mostly not good."
          },
          { username: "eberhardgross", text: "Best memes of 2019" },
          { username: "00_shots", text: "Top 10 reason why Vue.js beats React!" }
        ]
      };
    },
    props: {
      source: String
    }
  };
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
</style>
