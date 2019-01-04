<template>
  <div class="lgtmoon-header">
    <h1 class="title">
      <a href="/" class="no-decoration">
        LGTMoon
      </a>
    </h1>
  </div>
</template>

<script>
</script>

<style>
 .lgtmoon-header {
     background-image: url(/assets/ink/ink002.png);
     background-size: 150px;
     background-repeat: no-repeat;
 }
 .no-decoration {
     text-decoration: none;
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
</style>
