<template>
  <v-system-bar fixed class="d-inline-flex"
    v-if="securityClassification"
    :style="{ backgroundColor: securityClassification.backgroundColor }"
  >
    <h4 class="text-xs-center"
        :style="{ color: securityClassification.textColor }"
    >
      {{ securityClassification.classification }}
    </h4>
  </v-system-bar>
</template>

<script>
export default {
  name: 'SecurityBanner',
  props: {
    securityClassification: {
      type: Object,
      required: false
    }
  },
  components: {},
  data () {
    return {}
  },
  created () {},
  destroyed () {},
  mounted () {},
  computed: {},
  watch: {},
  methods: {}
}
</script>

<style scoped>

</style>
