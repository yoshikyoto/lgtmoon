<template>
  <div class="image-block" @mouseenter="mouseenter()" @mouseleave="mouseleave()">
    <img
      v-if="isShowButton"
      :src="starIcon"
      class="hover-button star-button"
      @mousedown="toggleStar()"
      title="Star this image">
    <CopyButton
      v-show="isShowButton"
      :text="githubMarkdown"
      class="hover-button copy-button"/>
    <img :src="item.url" v-on:click="select()" class="image">
  </div>
</template>

<script>
  import repository from '@/modules/Repository'
  import CopyButton from '@/components/CopyButton'

  export default {
    name: "LgtmImage",
    data() {
      return {
        isMouseOn: false,
        isFavorited: repository.isFavorited(this.item)
      }
    },
    components: {
      CopyButton
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
      isShowButton() {
        if (! this.item.isConverted) {
          // convert前の画像はボタンを出さない
          return false;
        }
        return this.isMouseOn
      },
      githubMarkdown() {
        return '![LGTM](' + this.item.url + ')';
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
  .hover-button {
    height: 30px;
    width: 30px;
    opacity: 0.9;
  }
  .star-button {
    cursor: pointer;
    position: absolute;
    top: 2px;
    right: 2px;
  }
  .copy-button {
    position: absolute;
    top: 2px;
    right: 36px;
  }
</style>
