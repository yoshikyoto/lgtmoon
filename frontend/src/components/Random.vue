<template>
  <section class="lgtmoon-section center recent-section" id="images">
    <Images :items.sync="items"/>
  </section>
</template>

<script>
 import axios from 'axios'
 import Images from '@/components/Images'

 export default {
     name: 'Recent',
     components: {
         Images
     },
     mounted() {
         axios.get('/api/v1/images/random.json').then((response) => {
             this.items = response.data.images
         });
     },
     data () {
         return {
             items: []
         }
     }
 }
</script>

<style>
</style>
