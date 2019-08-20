<template>
  <div class="image-block" @mouseover="mouseover()" @mouseout="mouseout()">
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
        isMouseover: false,
        isFavorited: repository.isFavorited(this.item)
      }
    },
    props: [
      'item'
    ],
    beforeUpdate() {
      console.log("beforeUpdate");
      this.isFavorited = repository.isFavorited(this.item)
    },
    computed: {
      starIcon() {
        if (this.isFavorited) {
          return '/assets/star-on.png'
        }
        return '/assets/star-off.png'
      },
      isShowStar() {
        return this.isMouseover
      }
    },
    methods: {
      select() {
        this.$emit('select', this.item)
      },
      mouseover() {
        this.isMouseover = true
        // マウスオーバーされた時にfavorite情報を更新する
        this.isFavorited = repository.isFavorited(this.item)
      },
      mouseout() {
        this.isMouseover = false
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
