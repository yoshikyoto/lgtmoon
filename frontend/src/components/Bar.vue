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
          @click.prevent="submit"
          value="検索/画像生成"
          :disabled="isInputDisabled"/>
        <span id="message" class="form-message" v-text="message"></span>
      </form>
    </div>
    <div v-if="images.length != 0"
      class="search-result-block">
      <h2>画像検索からLGTMを作成</h2>
      <div class="close" @click="close">x Close</div>
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
    name: 'Bar',
    components: {
      Images,
      Loading
    },
    data () {
      return {
        /** 検索キーワード */
        keyword: '',
        /** 検索欄横に出るメッセージ */
        message: '',
        /** 検索結果の画像 */
        images: [],
        /** 検索窓とボタンを無効化するかどうか（連打対策） */
        isInputDisabled: false
      }
    },
    methods: {
      /** 入力内容に応じて処理をする */
      submit () {
        if(this.isUrl(this.keyword)) {
          // URLが入力された場合は画像生成
          this.generate(this.keyword)
        } else {
          // それ以外の場合はキーワード検索
          this.search(this.keyword)
        }
      },
      /** 画像検索を行う */
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
      /** 画像URLを渡すとLGTM生成APIを叩く */
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
      /** URLかどうかを判定する */
      isUrl (url) {
        return !(url.match(/^https?:\/\//) === null)
      },
      /** 画像がクリックされたときの処理 */
      select (image) {
        this.generate(image.url)
      },
      close () {
        this.images = []
      }
    }
  }
</script>

<style scoped>
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
  .close {
    position: absolute;
    top: 10px;
    right: 5px;
    font-color: #238;
  }
  .close:hover {
    text-decoration: underline;
    cursor: pointer;
  }
</style>
