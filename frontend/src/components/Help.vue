<template>
  <section class="lgtmoon-section center">
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

    <h2 class="help-section-title">LGTMとは</h2>

    GitHubなどでプルリク（プルリクエスト/Pull Request）をレビューした時、「Looks Good To Me」
    （自分がレビューした限りはよいと思います、マージしていいですよ、くらいの意味）
    という言葉を略して「LGTM」と書くことがあります。
    このLGTMをちょっと楽しい画像にすることで、レビューすることやプルリクを出すことがちょっと楽しくなります。<br>
    <img src="https://qiita-image-store.s3.amazonaws.com/0/29024/373aad85-d1aa-96d7-026c-66b3587ae536.png" class="center">

    <h2 class="help-section-title">LGTMoonとは</h2>

    LGTMoonはLGTM画像をカンタンに生成・作成できるWebサイト、「LGTM画像ジェネレーター」です。
    なるべくシンプルに、カンタンに、手間を掛けずLGTM画像を作ることのできるアプリです。
    静止画だけでなく、GIFアニメーションなんかからもLGTM画像を生成できます。

    <h2 class="help-section-title">注意点</h2>

    <ul>
      <li>画像生成には数秒〜数分かかりますのでしばらくお待ち下さい。連打しないようにお願いします。</li>
      <li>生成に成功すると最近の画像一覧に画像が表示されます。</li>
      <li>画像生成に失敗する可能性もあります。しばらくたっても生成されない場合は、もう一度お試しいただくか、別の画像でお試しください。</li>
      <li>LGTM画像生成の際は、元画像の著作権などに注意してください。</li>
    </ul>

    <h2>今後のアップデートの予定</h2>
    <ul>
      <li>手元の画像をアップロードしてLGTM画像が生成できるようにする予定です。</li>
      <li>スマホから画像をアップロードしてLGTM画像が生成できるようにする予定です。</li>
      <li>過去に生成された画像から検索できるようにする予定です。</li>
    </ul>

    何か要望がございましたら下記twitterなどにご連絡ください。
    GitHubにプルリクエストを出して頂いても構いません。

    <h2 class="help-section-title">画像検索のヒント</h2>

    <ul>
      <li>「かっこいい gif」のように検索するとgifアニメーションからLGTM画像を作成できたりします。</li>
      <li>縦長の画像より横長の画像の方がクールなLGTM画像ができます。</li>
      <li>白が多い画像ですとLGTMの文字が見づらくなります。画面の中心あたりにLGTMの文字がかさなりますので、それを意識して画像を選択すると良いでしょう。</li>
    </ul>

    <h2 class="help-section-title">リンク</h2>

    <ul>
      <li>製作者twitter
        <a href="https://twitter.com/yoshiki_utakata" target="_blank">
          yoshiki_utakata
        </a>
      </li>
      <li>GitHub
        <a href="https://github.com/yoshikyoto/lgtmoon" target="_blank">
          yoshikyoto/lgtmoon
        </a>
      </li>
      <li>
        <a href="http://yoshiki-utakata.hatenablog.com/" target="_blank">製作者ブログ</a>
      </li>
      <li>Qiita記事
        <a href="http://qiita.com/yoshikyoto/items/4910b1a4bcefe7f5ab8c" target="_blank">
          LGTM画像を驚くほど簡単に作れるWebサービス with Scala
        </a>
      </li>
    </ul>

    <h2 class="help-section-title">技術的な話</h2>

    LGTMoonは、Scala, Play Framework を利用しており、非同期画像処理部分は Scala の Akka Actor を利用しています（RabbitMQなどは利用していません）。
    また、フロントエンドには Vue.js 、画像処理部分には ImageMagick を利用しています。

    <h2 class="help-section-title">参考</h2>

    <ul>
      <li><a href="http://amzn.to/2m8aD54" target="_blank">Scalaスケーラブルプログラミング第2版</a>: Scalaで大規模なコードを書くなら読んでおきたい本です</li>
      <li><a href="http://amzn.to/2AGdMhL" target="_blank">Scala関数型デザイン&プログラミング―Scalazコントリビューターによる関数型徹底ガイド</a>: 関数型プログラミングを意識してScalaを書きたいのであれば読んでおきたい本です</li>
      <li><a href="http://amzn.to/2mcXYyi" target="_blank">Akka実践バイブル アクターモデルによる並行・分散システムの実現</a>: Akka Actor などを利用した並行処理などを理解するためのバイブルです</li>
      <li><a href="https://www.playframework.com/documentation/ja/2.4.x/Home" target="_blank">Scala Play 2.4.x ドキュメント</a></li>
      <li><a href="https://jp.vuejs.org/index.html" target="_blank">Vue.js</a></li>
    </ul>
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
  </section>
</template>
