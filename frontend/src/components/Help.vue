<template>
  <div>
    <div class="lgtmoon-section center">
      <h2 class="help-section-title">使い方</h2>

      キーワード検索を使ってLGTM画像を生成する方法と、画像URLからLGTM画像を生成する方法があります。

      <h3 class="help-section-title">キーワード検索からLGTM画像生成をする方法</h3>
      <ol>
        <li>検索欄にキーワードを入力すると画像検索結果一覧が表示されます。</li>
        <li>検索結果一覧から画像をクリックすると、その画像を元にLGTM画像が生成されます。</li>
      </ol>

      <h3>画像URLからLGTM画像を生成する方法</h3>
      <ol>
        <li>httpまたはhttpsから始まる画像URLを検索欄に入力すると、その画像をもとにLGTM画像が生成されます。</li>
      </ol>
    </div>

    <FavoriteHelp />

    <div class="lgtmoon-section center">
      <h2 class="help-section-title">LGTMとは</h2>
      GitHubなどでプルリク（プルリクエスト/Pull Request）をレビューした時、「Looks Good To Me」
      （自分がレビューした限りはよいと思います、マージしていいですよ、くらいの意味）
      という言葉を略して「LGTM」と書くことがあります。
      このLGTMをちょっと楽しい画像にすることで、レビューすることやプルリクを出すことがちょっと楽しくなります。<br>
      <img src="https://qiita-image-store.s3.amazonaws.com/0/29024/373aad85-d1aa-96d7-026c-66b3587ae536.png" class="center">
    </div>

    <div class="lgtmoon-section center">
      <h2 class="help-section-title">LGTMoonとは</h2>
      LGTMoonはLGTM画像をカンタンに生成・作成できるWebサイト、「LGTM画像ジェネレーター」です。
      なるべくシンプルに、カンタンに、手間を掛けずLGTM画像を作ることのできるアプリです。
      静止画だけでなく、GIFアニメーションなんかからもLGTM画像を生成できます。
    </div>

    <div class="lgtmoon-section center">
      <h2 class="help-section-title">注意</h2>

      <h3>画像生成時の注意</h3>
      <ul>
        <li>画像生成には数秒〜数分かかりますのでしばらくお待ち下さい。連打しないようにお願いします。</li>
        <li>生成に成功すると最近の画像一覧に画像が表示されます。</li>
        <li>画像生成に失敗する可能性もあります。しばらくたっても生成されない場合は、もう一度お試しいただくか、別の画像でお試しください。</li>
        <li>LGTM画像生成の際は、元画像の著作権などに注意してください。</li>
      </ul>

      <h3 class="help-section-title">画像検索のヒント</h3>
      <ul>
        <li>「かっこいい gif」のように検索するとgifアニメーションからLGTM画像を作成できたりします。</li>
        <li>縦長の画像より横長の画像の方がクールなLGTM画像ができます。</li>
        <li>白が多い画像ですとLGTMの文字が見づらくなります。画面の中心あたりにLGTMの文字がかさなりますので、それを意識して画像を選択すると良いでしょう。</li>
      </ul>

      <h3>プライバシーポリシー</h3>
      <ul>
        <li>このサイトでは、Googleを含む第三者配信事業者はCookieを使用して、当ウェブサイトへの過去のアクセス情報に基づいて広告を配信します。</li>
        <li>Google が広告 Cookie を使用することにより、ユーザーがそのサイトや他のサイトにアクセスした際の情報に基づいて、Google やそのパートナーが適切な広告をユーザーに表示することができます。</li>
        <li>
          <a href="https://www.google.com/settings/ads" target="_blank">広告設定</a>
          でパーソナライズ広告を無効にしたり、
          <a href="http://www.aboutads.info/" target="_blank">www.aboutads.info</a>
          で、パーソナライズ広告に使われる第三者配信事業者の Cookie を無効にすることができます。
        </li>
      </ul>
    </div>

    <div class="lgtmoon-section center">
      <h2 class="help-section-title">Contact</h2>
      何か要望・ご連絡がございましたら下記twitterなどにご連絡ください。
      GitHubにプルリクエストを出して頂いても構いません。
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
          ブログ
          <a href="https://www.utakata.work/" target="_blank">https://www.utakata.work/</a>
        </li>
        <li>その他関連記事
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
    </div>

    <div class="lgtmoon-section center">
      <h2 class="help-section-title">LGTMoonは無料で利用できます</h2>
      <div>
        <ul>
          <li>
            <a href="http://amzn.asia/0UX34TT" target="_blank">
              Amazonほしいものリスト
            </a>
          </li>
        </ul>
      </div>
    </div>
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
