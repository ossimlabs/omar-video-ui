import Vue from 'vue'
import Router from 'vue-router'
import Welcome from '@/components/Welcome'
import VideoPlayer from '@/pages/VideoPlayer'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '?videoname=:videoName'
    }
  ]
})
