<template>
  <div>
    <div class="image-detail-full-overlay" @click="close">
    </div>
    <div class="image-detail">
      <a :href="url">
        <img :src="url" class="image-detail-image">
      </a>
      <h3 class="image-detail-headding">Image URL</h3>
      <input class="image-detail-text" type="text" :value="getImageUrl()" />
      <span class="image-text-copy-button" v-on:click="copy">
        Copy
      </span>
      <h3 class="image-detail-headding">GitHub Markdown</h3>
      <input class="image-detail-text" type="text" :value="getGithubMarkdown()" />
      <span class="image-text-copy-button">Copy</span>
    </div>
  </div>
</template>

<script>
  export default {
    props: [
      'url'
    ],
    methods: {
      close() {
        this.$emit('close');
      },
      getImageUrl(){
        return this.url;
      },
      getGithubMarkdown() {
        return '![LGTM](' + this.url + ')';
      },
      copy() {

      }
    }
  }
</script>

<style>
 .image-detail-full-overlay {
    position: fixed;
    top: 0px;
    left: 0px;
    width: 100%;
    height: 100%;
    background-color: #000;
    color: #fff;
    opacity: 0.5;
    z-index: 100;
 }
 .image-detail {
     z-index: 200;
     position: fixed;
     background-color: #fff;
     border: 1px solid #eef;
     overflow: scroll;
     top: 5%;
     height: 80%;
     width: 520px;
     /* leftとrightを固定にしてmarginするとセンタリングされる */
     left: 0px;
     right: 0px;
     margin: 0px auto;
     padding: 10px 0px;
     text-align: center;
 }
 .image-detail-image {
     margin: 2px;
     border: 1px solid #eef;
     padding: 2px;
 }
 .image-detail-headding {
     margin-top: 5px;
     margin-bottom: 2px;
     font-size: 1.2em;
 }
 .image-detail-text {
   width: 380px;
   border: 1px solid #eef;
   box-sizing: border-box;
   height: 26px;
 }
 .image-text-copy-button {
   background-color: #28a745;
   display: inline-block;
   font-weight: bold;
   color: #fff;
   padding: 2px 10px;
   margin: 0px;
   height: 22px;
   cursor: pointer;
 }
</style>
