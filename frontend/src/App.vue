<template>
  <div>
    <Bar/>
    <div class="menu position-relative">
      <div v-on:click="recent()" class="menu-item"
        :class="{ 'menu-item-selected' : selected == 0 }">
        最近の画像
      </div>
      <div v-on:click="random()" class="menu-item"
        :class="{ 'menu-item-selected' : selected == 1 }">
        ランダム
      </div>
      <div v-on:click="help()" class="menu-item"
        :class="{ 'menu-item-selected' : selected == 2 }">
        使い方
      </div>
    </div>
    <section class="lgtmoon-section center recent-section position-relative image-section">
      <Images :items.sync="recentItems" @select="showDetail" v-show="selected == 0"/>
      <Images :items.sync="randomItems" @select="showDetail" v-show="selected == 1"/>
      <Help v-show="selected == 2"/>
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

  export default {
    name: 'app',
    data() {
      return {
        selected: 0,
        isShowingDetail: false,
        isLoading: false,
        recentItems: [],
        randomItems: [],
        image: null
      }
    },
    components: {
      Bar,
      Help,
      Images,
      ImageDetail,
      Loading
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
      help () {
        this.selected = 2
      },
      showDetail (image) {
        this.isShowingDetail = true
        this.image = image
      },
      closeDetail () {
        this.isShowingDetail = false;
      }
    }
  }
</script>

<style>
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
    width: 300px;
    padding: 5px 0px;
    text-align: center;
    vertical-align: middle;
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
  .image-section {
    min-height: 200px;
  }
</style>
