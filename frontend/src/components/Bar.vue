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
        <label class="form-button form-submit">
          <span v-text="submitButtonLabel" class=""></span>
          <input type="submit"
            class="hidden"
            @click.prevent="submit"
            value="Search"
            :disabled="isInputDisabled"/>
        </label>
        <label class="form-button form-upload">
          <span>画像アップロード</span>
          <input type="file" name="file"
                 class="hidden"
                 @change="uploadBinary">
        </label>
        <span id="message" class="form-message" v-text="message"></span>
      </form>
    </div>
    <div v-if="images.length != 0"
      class="search-result-block">
      <h2>画像検索結果からLGTMを作成</h2>
      <div>
        画像を選択するとその画像を元にLGTM画像を作成します
      </div>
      <div class="close" @click="close">x Close</div>
      <Images :items.sync="images" @select="select"/>
      <Loading v-if="isInputDisabled"/>
    </div>
  </div>
</template>

<script>
  import repository from '@/modules/Repository'
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
        repository.search(keyword).then((response) => {
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
        repository.generateByUrl(imageUrl).then((response) => {
          this.message = '生成完了までお待ちください'
          this.enableInputInSec(5)
        }).catch((error) => {
          this.message = "生成中にエラーが発生しました"
          this.enableInputInSec(5)
        });
      },
      uploadBinary (e) {
        const file = e.target.files[0]
        repository.generateByFile(file).then((response) => {
          this.message = '生成完了までお待ちください'
          this.enableInputInSec(5)
        }).catch((error) => {
          this.message = '生成中にエラーが発生しました'
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
  /** form の外にある div */
  .form-block {
    position: relative;
    background-color: #def;
    padding: 5px 5px;
    height: 30px;
  }
  .form-input-text {
    border: 0px solid #eef;
    box-sizing: border-box;
    float: left;
    margin: 0px 5px;
    height: 30px;
    font-size: 14px;
    width: 300px;
  }
  /** submit と file ボタンの label */
  .form-button {
    box-sizing: border-box;
    font-weight: bold;
    padding: 5px 10px;
    margin: 0px 5px;
    color: #fff;
    background: #009;
    display: inline-block;
    height: 30px;
    font-size: 15px;
  }
  .form-button:hover {
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

  @media screen and (max-width:480px){
    .form-block {
      height: 90px;
    }
    .form-input-text {
      /** 「検索」と「LGTM画像生成」が改行されないサイズ150px */
      width: calc(100% - 150px);
    }
    .form-upload {
      width: calc(100% - 10px);
      height: 46px;
      font-size: 24px;
      text-align: center;
      margin: 10px 5px;
    }
  }
</style>
