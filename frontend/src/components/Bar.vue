<template>
  <div>
    <div class="form-block">
      <form id="lgtmform" class="form">
        <input type="text"
               name="keyword"
               class="form-input-text"
               placeholder="キーワードで画像検索/URLで直接生成 【例】ラブライブ"
               v-model="keyword"
               :disabled="isInputDisabled"/>
        <input type="submit"
               class="form-submit"
               @click.prevent="submit()"
               value="検索/画像生成"
               :disabled="isInputDisabled"></input>
        <span id="message" class="form-message" v-text="message"></span>
      </form>
    </div>
    <div v-if="images.length != 0">
      <h2>検索結果</h2>
      <Images :items.sync="images"/>
      <div class="overlay" v-if="isInputDisabled">
        <div class="loader">
          <img src="/assets/loader.gif">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
 import axios from 'axios'
 import Images from '@/components/Images'

 export default {
     name: 'bar',
     components: {
         Images
     },
     data() {
         return {
             keyword: '',
             message: '',
             images: [],
             isInputDisabled: false
         }
     },
     methods: {
         submit(event) {
             if(this.isUrl(this.keyword)) {
                 this.generate(this.keyword)
             } else {
                 this.search(this.keyword)
             }
         },
         search(keyword) {
             this.isInputDisabled = true
             axios.get('/search.json', {
                 params: {
                     keyword: keyword
                 }
             }).then((response) => {
                 this.images = response.data.images
                 this.isInputDisabled = false
             }).catch((error) => {
                 this.message = "画像検索でエラーが発生しました"
                 this.isInputDisabled = false
             });
         },
         generate(imageUrl) {
             this.isInputDisabled = true
             axios.post('/image.json', {
                 url: imageUrl
             }).then((response) => {
                 this.message = '生成中 ' + response.data.url
                 this.isInputDisabled = false
             }).catch((error) => {
                 this.message = "生成中にエラーが発生しました"
                 this.isInputDisabled = false
             });
         },
         isUrl(url) {
             return !(url.match(/^https?:\/\//) === null)
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
