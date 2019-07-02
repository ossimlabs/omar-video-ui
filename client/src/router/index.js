import Vue from 'vue'
import Router from 'vue-router'
import VideoPlayer from '@/pages/VideoPlayer'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '?filter=:filter',
      component: VideoPlayer
    }
  ]
})
