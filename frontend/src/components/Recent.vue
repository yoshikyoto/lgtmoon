<template>
  <section class="lgtmoon-section center result-section hidden">
    <h2>検索結果</h2>
    <div id="search-results" class="images-block">
      <div v-for="item in items" class="image-block">
        <img :src="item.url" v-on:click="post(item)">
      </div>
    </div>
    <div class="overlay hidden">
      <div class="loader">
        <img src="/assets/ajax-loader.gif">
      </div>
    </div>
  </section>
</template>

<script>
 import axios from 'axios'

 export default {
     name: 'Recent',
     mounted() {
         console.log(this.items)
         axios.get('/api/v1/images/recent.json').then((response) => {
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
