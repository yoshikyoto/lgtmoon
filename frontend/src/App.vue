<template>
  <div class="lgtmoon-app center">
    <Header/>
    <Bar/>
    <div class="menu">
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
    <Recent v-show="selected == 0"/>
    <Random v-show="selected == 1"/>
    <Help/>
  </div>
</template>

<script>
 import Header from '@/components/Header'
 import Bar from '@/components/Bar'
 import Recent from '@/components/Recent'
 import Random from '@/components/Random'
 import Help from '@/components/Help'

 export default {
     name: 'app',
     data() {
         return {
             selected: 0
         }
     },
     components: {
         Header,
         Bar,
         Recent,
         Random,
         Help
     },
     methods: {
         recent() {
             this.selected = 0
         },
         random() {
             this.selected = 1
         },
         help() {
             this.selected = 2
         }
     }
 }
</script>

<style>
 .lgtmoon-app {
     font-family: 'Avenir', Helvetica, Arial, sans-serif;
     -webkit-font-smoothing: antialiased;
     -moz-osx-font-smoothing: grayscale;
     width: 900px;
     position: relative;
 }
 .center {
    margin-left: auto;
    margin-right: auto;
 }
 .menu:after {
     clear: both;
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
</style>
