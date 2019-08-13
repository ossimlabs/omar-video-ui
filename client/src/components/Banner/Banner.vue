<template>
  <v-system-bar class="d-inline-flex"
    v-if="securityInfo"
    :style="{ backgroundColor: securityInfo.securityClassification.backgroundColor }"
  >
    <h4 class="text-xs-center"
        :style="{ color: securityInfo.securityClassification.textColor }"
    >
      {{ securityInfo.securityClassification.classification }}
    </h4>
  </v-system-bar>
</template>

<script>
export default {
  name: 'Banner',
  props: {},
  components: {},
  data () {
    return {
      securityInfo: false
    }
  },
  created () {
    this.getClassification()
  },
  destroyed () {},
  mounted () {},
  computed: {},
  watch: {},
  methods: {
    getClassification: function () {
      fetch(`${process.env.SERVER_URL}/restApi`)
        .then(res => res.json())
        .then(securityInfo => {
          this.securityInfo = securityInfo
        })
    }
  }
}
</script>

<style scoped>

</style>
