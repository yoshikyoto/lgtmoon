<template>
  <div class="overlay">
    <div class="loader">
      <img src="/assets/loader.gif">
    </div>
  </div>
</template>

<style>
  .overlay {
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
    height: 100%;
    background-color: #000;
    color: #fff;
    opacity: 0.5;
 }
 .loader {
    margin: 0px auto;
    width: 100px;
    height: 100px;
 }
</style>
