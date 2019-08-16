<template>
  <div class="image-block">
    <img :src="starIcon" class="star">
    <img :src="item.url" v-on:click="select()" class="image">
  </div>
</template>

<script>
  export default {
    name: "LgtmImage",
    props: [
      'item'
    ],
    computed: {
      starIcon() {
        return '/assets/star-off.png'
      }
    },
    methods: {
      select() {
        this.$emit('select', this.item)
      }
    }
  }
</script>

<style scoped>
  .image-block {
    position: relative;
    display: inline-block;
    box-sizing: border-box;
    border: 1px solid #ccc;
  }
  .image {
    width: 100%;
    height: auto;
  }
  .star {
    height: 30px;
    width: 30px;
    position: absolute;
    top: 2px;
    right: 2px;
  }
</style>
