<template>
  <div class="image-block" @mouseover="mouseover()" @mouseout="mouseout()">
    <img v-if="isShowStar" :src="starIcon" class="star" @mousedown="toggleStar()">
    <img :src="item.url" v-on:click="select()" class="image">
  </div>
</template>

<script>
  export default {
    name: "LgtmImage",
    data() {
      return {
        isMouseover: false,
        isStared: false,
      }
    },
    props: [
      'item'
    ],
    computed: {
      starIcon() {
        if (this.isStared) {
          return '/assets/star-on.png'
        }
        return '/assets/star-off.png'
      },
      isShowStar() {
        // すでにスターしている場合はスターを表示してやる
        if (this.isStared) {
          return true
        }
        // そうでない場合はマウスオーバー次第
        return this.isMouseover
      }
    },
    methods: {
      select() {
        this.$emit('select', this.item)
      },
      mouseover() {
        this.isMouseover = true
      },
      mouseout() {
        this.isMouseover = false
      },
      toggleStar() {
        if (this.isStared) {
          this.isStared = false
        } else {
          this.isStared = true
        }
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
