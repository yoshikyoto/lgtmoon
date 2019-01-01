<template>
  <div class="form-block">
    <form id="lgtmform" class="form">
      <input type="text"
             name="keyword"
             class="form-input-text"
             placeholder="キーワードで画像検索/URLで直接生成 【例】ラブライブ"
             v-model="keyword" />
      <input type="button" class="form-submit" @click="submit()" value="検索/画像生成"></input>
      <span id="message" class="form-message"></span>
    </form>
  </div>
</template>

<script>
 import axios from 'axios'

 export default {
     name: 'bar',
     data() {
         return {
             keyword: ''
         }
     },
     methods: {
         submit(event) {
             console.log(this.keyword)
             // this.$emit('search');
         }
     }
 }
</script>

<style>
 .form-block {
     position: relative;
     background-color: #def;
     padding: 10px;
 }
 .form-input-text {
     border: 1px solid #eef;
     float: left;
     margin: -2px 0px 0px 8px;
     border-radius: 3px;
     height: 25px;
     width: 300px;
     font-size: 13px;
 }
 .form-submit {
     height: 25px;
 }
 .form-message {
     margin-left: 10px;
 }
</style>
