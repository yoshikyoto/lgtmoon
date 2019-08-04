<template>
  <div>
    <div class="form-block">
      <form id="lgtmform" class="form">
        <input type="text"
          name="keyword"
          class="form-input-text"
          placeholder="キーワードで画像検索 or URLを直接入力"
          v-model="keyword"
          :disabled="isInputDisabled"/>
        <label class="form-submit">
          <span v-text="submitButtonLabel"></span>
          <input type="submit"
            class="hidden"
            @click.prevent="submit"
            value="Search"
            :disabled="isInputDisabled"/>
        </label>
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
        isInputDisabled: false,
      }
    },
    computed: {
      /** 入力された文字列がURLかどうか */
      isInputUrl () {
        return this.isUrl(this.keyword)
      },
      submitButtonLabel () {
        if (this.isInputUrl) {
          return 'LGTM画像生成'
        }
        return '検索/生成'
      },
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
        axios.get('/api/v1/search', {
          params: {
            keyword: keyword
          }
        }).then((response) => {
          this.images = response.data.images
          this.enableInputInSec(5)
        }).catch((error) => {
          this.message = "画像検索でエラーが発生しました"
          this.enableInputInSec(5)
        });
      },
      /** 画像URLを渡すとLGTM生成APIを叩く */
      generate (imageUrl) {
        this.isInputDisabled = true
        axios.post('/api/v1/images/url', {
          url: imageUrl
        }).then((response) => {
          this.message = '生成中 ' + response.data.url
          this.enableInputInSec(5)
        }).catch((error) => {
          this.message = "生成中にエラーが発生しました"
          this.enableInputInSec(5)
        });
      },
      /** second秒後にinputをenabledにする */
      enableInputInSec (second) {
        const thisVue = this
        setTimeout(function () {
          thisVue.isInputDisabled = false
        }, second * 1000);
      },
      /** URLかどうかを判定する */
      isUrl (url) {
        return !(url.match(/^https?:\/\//) === null)
      },
      /** 画像がクリックされたときの処理 */
      select (image) {
        this.generate(image.url)
      },
      /** 検索結果を閉じる */
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
    height: 26px;
    width: 300px;
    font-size: 13px;
    border-radius: 5px 0px 0px 5px;
  }
  .form-submit {
    height: 25px;
    width: 100px;
    font-weight: bold;
    padding: 0.25em 0.5em;
    color: #fff;
    background: #009;
    border-radius: 0 5px 5px 0;
  }
  .form-submit:hover {
    background-color: #ccf;
    color: #300;
  }
  .form-message {
    margin-left: 10px;
  }
  .search-result-block {
    position: relative;
  }
  /* 検索結果を閉じるボタン */
  .close {
    position: absolute;
    top: 10px;
    right: 5px;
    color: #238;
  }
  .close:hover {
    text-decoration: underline;
    cursor: pointer;
  }
</style>
