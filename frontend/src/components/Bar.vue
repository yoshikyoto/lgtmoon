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
               :disabled="isInputDisabled"/>
        <span id="message" class="form-message" v-text="message"></span>
      </form>
    </div>
    <div v-if="images.length != 0"
         class="search-result-block">
      <h2>検索結果</h2>
      <Images :items.sync="images" @select="select"/>
      <Loading v-if="isInputDisabled"/>
    </div>
  </div>
</template>

<script>
 import axios from 'axios'
 import Images from '@/components/Images'
 import Loading from '@/components/Loading'

 export default {
     name: 'bar',
     components: {
         Images,
         Loading
     },
     data () {
         return {
             keyword: '',
             message: '',
             images: [],
             isInputDisabled: false
         }
     },
     methods: {
         submit (event) {
             if(this.isUrl(this.keyword)) {
                 this.generate(this.keyword)
             } else {
                 this.search(this.keyword)
             }
         },
         search (keyword) {
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
         generate (imageUrl) {
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
         isUrl (url) {
             return !(url.match(/^https?:\/\//) === null)
         },
         select (image) {
             this.generate(image.url)
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
 .search-result-block {
     position: relative;
 }
</style>
