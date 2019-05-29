import "vuetify/dist/vuetify.min.css"; // Ensure you are using css-loader
import "@fortawesome/fontawesome-free/css/all.css"; // Ensure you are using css-loader

// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Vuetify from "vuetify";

import * as uiv from 'uiv'
import './assets/css/bootstrap.css'
import './assets/css/grails.css'
import './assets/css/main.css'

Vue.config.productionTip = false

// Vue.use(uiv)
Vue.use(Vuetify, {
  iconfont: "fa"
});

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  render: h => h(App),
  components: { App },
  template: '<App/>'
})
