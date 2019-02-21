<template>
  <section class="lgtmoon-section center recent-section" id="images">
    <Images :items.sync="items" @select="showDetail"/>
    <ImageDetail v-if="isShowingDetail" :url="image.url" @close="closeDetail"/>
  </section>
</template>

<script>
 import axios from 'axios'
 import Images from '@/components/Images'
 import ImageDetail from '@/components/ImageDetail'

 export default {
     name: 'Recent',
     components: {
         Images,
         ImageDetail
     },
     mounted() {
         axios.get('/api/v1/images/random.json').then((response) => {
             this.items = response.data.images
         });
     },
     data () {
         return {
             items: [],
             isShowingDetail: false,
             image: null
         }
     },
     methods: {
         showDetail (image) {
             this.isShowingDetail = true
             this.image = image
         },
         closeDetail () {
             this.isShowingDetail = false;
         }
     }
 }
</script>

<style>
</style>
