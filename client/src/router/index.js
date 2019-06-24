import Vue from 'vue'
import Router from 'vue-router'
import Welcome from '@/components/Welcome'
import VideoPlayer from '@/pages/VideoPlayer'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '?filter=:filter',
      name: 'Welcome',
      component: Welcome,
      props: true
    },
    {
      path: '/videoPlayer/:id',
      name: 'videoplayer',
      component: VideoPlayer
      // component: () =>
      //  import(/* webpackChunkName: "about" */ "./pages/VideoPlayer.vue")
    }
  ]
})
