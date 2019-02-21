<template>
  <div class="images-block">
    <div v-for="item in items" class="image-block">
      <img :src="item.url" v-on:click="select(item)" class="image">
    </div>
  </div>
</template>

<script>
 export default {
     name: 'Images',
     props: [
         'items'
     ],
     methods: {
         select(item) {
             this.$emit('select', item)
         }
     }
 }
</script>

<style>
 .images-block {
     width: 100%;
     -moz-column-count: 4;
     -webkit-column-count: 4;
     -o-column-count: 4;
     -ms-column-count: 4;
     -moz-column-gap: 4px;
     -webkit-column-gap: 4px;
     -o-column-gap: 4px;
     -ms-column-gap: 4px;
     box-sizing: border-box;
 }
 .image-block {
    display: inline-block;
    box-sizing: border-box;
    border: 1px solid #ccc;
 }
 .image {
    width: 100%;
    height: auto;
 }
</style>
