<template>
  <div>
    <Bar/>
    <div class="menu position-relative">
      <div v-on:click="recent()" class="menu-item"
        :class="{ 'menu-item-selected' : isRecentSelected }">
        {{ $t('recent') }}
      </div>
      <div v-on:click="random()" class="menu-item pconly"
        :class="{ 'menu-item-selected' : isRandomSelected }">
        ランダム
      </div>
      <div v-on:click="fav()" class="menu-item pconly"
        :class="{ 'menu-item-selected' : isFavoriteSelected }">
        お気に入り
      </div>
      <div v-on:click="help()" class="menu-item pconly"
        :class="{ 'menu-item-selected' : isHelpSelected }">
        使い方
      </div>
    </div>
    <section class="lgtmoon-section center recent-section position-relative image-section">
      <Images :items.sync="recentItems" @select="showDetail" v-show="isRecentSelected"/>
      <Images :items.sync="randomItems" @select="showDetail" v-show="isRandomSelected"/>
      <Images :items.sync="favoritedItems" @select="showDetail" v-show="isFavoriteSelected"/>
      <FavoriteHelp v-show="showFavoriteHelp" />
      <Help v-show="isHelpSelected"/>
      <ImageDetail v-if="isShowingDetail" :url="image.url" @close="closeDetail"/>
      <Loading v-if="isLoading"/>
    </section>
  </div>
</template>

<script>
  import repository from '@/modules/Repository'
  import Bar from '@/components/Bar'
  import Images from '@/components/Images'
  import ImageDetail from '@/components/ImageDetail'
  import Help from '@/components/Help'
  import Loading from '@/components/Loading'
  import FavoriteHelp from '@/components/FavoriteHelp'

  export default {
    name: 'app',
    data() {
      return {
        selected: 0,
        isShowingDetail: false,
        isLoading: false,
        recentItems: [],
        randomItems: [],
        favoritedItems: [],
        image: null
      }
    },
    components: {
      Bar,
      Help,
      Images,
      ImageDetail,
      Loading,
      FavoriteHelp
    },
    mounted() {
      // 最新の画像を読み込む
      const thisVue = this
      repository.recent().then((response) => {
        thisVue.recentItems = response.data.images
      });
      const pollingIntervalSeconds = 30
      setInterval(function () {
        repository.recent().then((response) => {
          thisVue.recentItems = response.data.images
        });
      }, pollingIntervalSeconds * 1000);
    },
    computed: {
      isRecentSelected() {
        return this.selected == 0
      },
      isRandomSelected() {
        return this.selected == 1
      },
      isFavoriteSelected() {
        return this.selected == 2
      },
      isHelpSelected() {
        return this.selected == 3
      },
      showFavoriteHelp() {
        let hasFavorited = this.favoritedItems.length > 0
        return this.isFavoriteSelected && !hasFavorited
      }
    },
    methods: {
      recent () {
        this.selected = 0
      },
      random () {
        // 連打対策
        if (this.isLoading) {
          return;
        }
        this.selected = 1
        this.isLoading = true
        repository.random().then((response) => {
          this.randomItems = response.data.images
          this.isLoading = false
        });
      },
      fav () {
        this.selected = 2
        this.favoritedItems = repository.favorited()
      },
      help () {
        this.selected = 3
      },
      showDetail (image) {
        this.isShowingDetail = true
        this.image = image
      },
      closeDetail () {
        this.isShowingDetail = false
      }
    }
  }
</script>

<style>
  .lgtmoon-app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    position: relative;
  }
  @media screen and (min-width: 641px) {
    .lgtmoon-app {
      width: 960px;
    }
  }
  @media screen and (max-width: 640px) {
    .lgtmoon-app {
      width: 100%;
    }
  }

  @font-face {
    font-family: 'ikafont';
    src: url('/assets/fonts/ikafont.otf') format('opentype');
  }
  .title {
    font-family: 'ikafont', Impact;
    font-size: 4em;
    margin-top: 0px;
    margin-bottom: 0px;
  }
  .title a {
    color: #003;
  }

  /** タイトル */
  .lgtmoon-header {
    background-image: url(/assets/ink/ink002.png);
    background-size: 150px;
    background-repeat: no-repeat;
  }
  .center {
    margin-left: auto;
    margin-right: auto;
  }
  .menu:after {
    clear: both;
  }
  .menu {
    height: 50px;
  }
  .menu-item {
    width: 240px;
    padding: 5px 0px;
    text-align: center;
    float: left;
    cursor: pointer;
    font-size: 1.5em;
    font-weight: bold;
    color: #300;
  }
  .menu-item:hover {
    background: #ccf;
    color: #600;
  }
  .menu-item-selected {
    background: #009;
    color: #ffffff;
  }
  @media screen and (max-width: 640px) {
    .menu-item {
      text-align: left;
    }
    .menu-item-selected {
      background: #fff;
      color: #000;
    }
  }

  .image-section {
    min-height: 200px;
  }
</style>

