<template>
  <div class="images-block">
    <LgtmImage v-for="item in items" :item="item" @select="select">
    </LgtmImage>
  </div>
</template>

<script>
  import LgtmImage from '@/components/LgtmImage'

  export default {
    name: 'Images',
    props: [
      'items'
    ],
    components: {
      LgtmImage
    },
    methods: {
      select(item) {
        this.$emit('select', item)
      }
    }
  }
</script>

<style scoped>
  .images-block {
    width: 100%;
    column-count: 4;
    column-gap: 4px;
    box-sizing: border-box;

    -moz-column-count: 4;
    -webkit-column-count: 4;
    -o-column-count: 4;
    -ms-column-count: 4;
    -moz-column-gap: 4px;
    -webkit-column-gap: 4px;
    -o-column-gap: 4px;
    -ms-column-gap: 4px;
  }

  @media screen and (max-width:480px) {
    .images-block {
      column-count: 2;
      -moz-column-count: 2;
      -webkit-column-count: 2;
      -o-column-count: 2;
      -ms-column-count: 2;
    }
  }
</style>
