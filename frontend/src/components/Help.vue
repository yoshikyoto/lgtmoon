<template>
  <div>
    <section class="lgtmoon-section center">
      <h2 class="help-section-title">{{ $t('LGTMoonとは') }}</h2>
      {{ $t('LGTMoonはLGTM画像をカンタンに生成・作成できるWebサイト、「LGTM画像ジェネレーター」です。') }}
    </section>

    <section class="lgtmoon-section center">
      <h2 class="help-section-title">{{ $t('つかいかた') }}</h2>
      {{ $t('LGTM画像を生成する方法はいくつかあります。') }}


      <h3 class="help-section-title">
        {{ $t('キーワード検索からLGTM画像生成をする方法') }}
      </h3>
      <ol>
        <li>
          {{ $t('検索欄にキーワードを入力して画像検索をします。') }}<br>
          <img class="help-img" :src="$t('/assets/help/search.png')">
        </li>
        <li>
          {{ $t('検索結果から画像をクリックします。') }}
        </li>
        <li>
          {{ $t('しばらく待つと「最近の画像」に生成されたLGTM画像が表示されます。') }}
        </li>
      </ol>

      <h3>{{ $t('画像URLからLGTM画像を生成する方法') }}</h3>
      <ol>
        <li>
          {{ $t('httpまたはhttpsから始まるURLを検索欄に入力すると、ボタンが「LGTM画像生成」に変化します。') }}<br>
          <img class="help-img" :src="$t('/assets/help/url.png')">
        </li>
        <li>
          {{ $t('「LGTM画像生成」をクリックします') }}
        </li>
        <li>
          {{ $t('しばらく待つと「最近の画像」に生成されたLGTM画像が表示されます。') }}
        </li>
      </ol>

      <h3>{{ $t('画像ファイルをアップロードして生成する方法') }}</h3>
      <ol>
        <li>{{ $t('「画像をアップロード」をクリックし、画像を選択します。') }}</li>
        <li>{{ $t('しばらく待つと「最近の画像」に生成されたLGTM画像が表示されます。') }}</li>
      </ol>
    </section>

    <section class="lgtmoon-section center">
      <h2 class="help-section-title">{{ $t('注意') }}</h2>

      <h3>{{ $t('画像生成時の注意') }}</h3>
      <ul>
        <li>{{ $t('画像生成には数秒〜数分かかりますのでしばらくお待ち下さい。連打しないようにお願いします。') }}</li>
        <li>{{ $t('生成に成功すると最近の画像一覧に画像が表示されます。') }}</li>
        <li>{{ $t('画像生成に失敗する可能性もあります。しばらくたっても生成されない場合は、もう一度お試しいただくか、別の画像でお試しください。') }}</li>
        <li>{{ $t('LGTM画像生成の際は、元画像の著作権などに注意してください。</li>
        <li>{{ $t('公序良俗に反する画像や違法な画像を作成しないでください。これらの画像が見つかった場合は予告なく画像を削除することがあります。') }}</li>
      </ul>

      <h3 class="help-section-title">{{ $t('画像検索のヒント') }}</h3>
      <ul>
        <li>{{ $t('GIFアニメーションからもLGTM画像を生成できます。') }}</li>
        <li>{{ $t('縦長の画像より横長の画像の方がクールなLGTM画像ができます。') }}</li>
        <li>{{ $t('白が多い画像ですとLGTMの文字が見づらくなります。画面の中心あたりにLGTMの文字がかさなりますので、それを意識して画像を選択すると良いでしょう。') }}</li>
      </ul>

      <h3>{{ $t('プライバシーポリシー') }}</h3>
      <ul>
        <li>{{ $t('このサイトでは、Googleを含む第三者配信事業者はCookieを使用して、当ウェブサイトへの過去のアクセス情報に基づいて広告を配信します。') }}</li>
        <li>{{ $t('Google が広告 Cookie を使用することにより、ユーザーがそのサイトや他のサイトにアクセスした際の情報に基づいて、Google やそのパートナーが適切な広告をユーザーに表示することができます。') }}</li>
        <li>
          <a href="https://www.google.com/settings/ads" target="_blank">{{ $t('広告設定') }}</a>
          {{ $t('でパーソナライズ広告を無効にしたり、') }}
          <a href="https://www.aboutads.info/" target="_blank">www.aboutads.info</a>
          {{ $t('で、パーソナライズ広告に使われる第三者配信事業者の Cookie を無効にすることができます。') }}
        </li>
      </ul>
    </section>
    <FavoriteHelp />

    <section class="lgtmoon-section center">
      <h2 class="help-section-title">{{ $t('LGTMとは') }}</h2>
      {{ $t('GitHubなどでプルリク（プルリクエスト/Pull Request）をレビューした時、「Looks Good To Me」（自分がレビューした限りはよいと思います、マージしていいですよ、くらいの意味）という言葉を略して「LGTM」と書くことがあります。') }}
      {{ $t('このLGTMをちょっと楽しい画像にすることで、レビューすることやプルリクを出すことがちょっと楽しくなります。') }}<br>
      <img src="https://qiita-image-store.s3.amazonaws.com/0/29024/373aad85-d1aa-96d7-026c-66b3587ae536.png" class="center">
    </section>

    <section class="lgtmoon-section center">
      <h2 class="help-section-title">Contact</h2>
      {{ $t('何か要望・ご連絡がございましたら下記にご連絡ください。') }}
      <ul>
        <li>
          twitter
          <a href="https://twitter.com/yoshiki_utakata" target="_blank">
            yoshiki_utakata
          </a>
        </li>
        <li>
          GitHub
          <a href="https://github.com/yoshikyoto/lgtmoon" target="_blank">
            yoshikyoto/lgtmoon
          </a>
        </li>
        <li>
          {{ $t('ブログ') }}
          <a href="https://www.utakata.work/" target="_blank">https://www.utakata.work/</a>
        </li>
        <li>{{ $t('その他関連記事') }}
          <ul>
            <li>
              <a href="http://qiita.com/yoshikyoto/items/4910b1a4bcefe7f5ab8c" target="_blank">
                LGTM画像を驚くほど簡単に作れるWebサービス with Scala
              </a>
            </li>
            <li>
              <a href="https://www.utakata.work/archive/category/LGTMoon" target="_blank">
                ブログのLGTMoon関連記事
              </a>
            </li>
          </ul>
        </li>
      </ul>
    </section>

    <section class="lgtmoon-section center">
      <h2 class="help-section-title">{{ $t('LGTMoonは無料で利用できます</h2>
      <div>
        <ul>
          <li>
            <a href="http://amzn.asia/0UX34TT" target="_blank">
              {{ $t('Amazonほしいものリスト') }}
            </a>
          </li>
        </ul>
      </div>
    </section>
  </div>
</template>


<script>
  import FavoriteHelp from '@/components/FavoriteHelp'

  export default {
    name: 'Help',
    components: {
      FavoriteHelp
    }
  }
</script>


<style scoped>
  .help-img {
    width: 320px;
    border: 1px solid;
  }
</style>
