<template>
  <div>
    <img class="copy-image"
         :data-clipboard-text="text"
         :src="icon"
         :class="[id]"
         title="Copy GitHub Markdown">
    <div class="copied-text" v-if="isCopied">Copied!</div>
  </div>
</template>

<script>
  import Clipboard from 'clipboard'

  export default {
    name: 'CopyButton',
    props: [
      'text'
    ],
    data() {
      return {
        isCopied: false,
        id: null
      }
    },
    mounted() {
      this.id = 'copy-' + this._uid;
      const mdClipboard = new Clipboard('.' + this.id);
      mdClipboard.on('success', (event) => {
        this.copied();
      })
    },
    computed: {
      icon() {
        return '/assets/copy.png';
      }
    },
    methods: {
      copied() {
        const thisVue = this;
        this.isCopied = true;
        setTimeout(() => {
          thisVue.isCopied = false;
        }, 2000)
      }
    }
  }
</script>


<style scoped>
  .copy-image {
    width: 100%;
    height: 100%;
    cursor: pointer;
    position: relative;
  }
  .copied-text {
    width: 80px;
    font-weight: bold;
    background-color: #28a745;
    color: #fff;
    text-align: center;
    position: absolute;
  }
</style>
