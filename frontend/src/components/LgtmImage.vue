<template>
  <div class="image-block" @mouseenter="mouseenter()" @mouseleave="mouseleave()">
    <img v-if="isShowStar" :src="starIcon" class="star" @mousedown="toggleStar()">
    <img :src="item.url" v-on:click="select()" class="image">
  </div>
</template>

<script>
  import repository from '@/modules/Repository'

  export default {
    name: "LgtmImage",
    data() {
      return {
        isMouseOn: false,
        isFavorited: repository.isFavorited(this.item)
      }
    },
    props: [
      'item'
    ],
    computed: {
      starIcon() {
        if (this.isFavorited) {
          return '/assets/star-on.png'
        }
        return '/assets/star-off.png'
      },
      isShowStar() {
        if (! this.item.isConverted) {
          // convert前の画像はお気に入りできない
          return false;
        }
        return this.isMouseOn
      }
    },
    methods: {
      select() {
        this.$emit('select', this.item)
      },
      mouseenter() {
        this.isMouseOn = true
        // マウスオーバーされた時にfavorite情報を更新する
        this.isFavorited = repository.isFavorited(this.item)
      },
      mouseleave() {
        this.isMouseOn = false
      },
      toggleStar() {
        if (this.isFavorited) {
          repository.unfavorite(this.item)
          this.isFavorited = false
        } else {
          repository.favorite(this.item)
          this.isFavorited = true
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
    opacity: 0.9;
  }
</style>
