<template>
  <div class="lgtmoon-app">
    <Header/>
    <Bar/>
    <Recent/>
  </div>
</template>

<script>
 import Header from '@/components/Header'
 import Bar from '@/components/Bar'
 import Recent from '@/components/Recent'

 export default {
     name: 'app',
     components: {
         Header,
         Bar,
         Recent
     }
 }
</script>

<style>
 .lgtmoon-app {
     font-family: 'Avenir', Helvetica, Arial, sans-serif;
     -webkit-font-smoothing: antialiased;
     -moz-osx-font-smoothing: grayscale;
 }
</style>
