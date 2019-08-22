<template>
  <section class="lgtmoon-section center">
    <h2 class="help-section-title">お気に入り機能</h2>
    <h3>お気に入り機能の使い方</h3>
    <p>
      画像の上にマウスを移動させると右上に☆が出て来ます。
    </p>
    <img src="/assets/help/favorite.png" class="favorite-help-cat">
    <p>
      ☆をクリックするとお気に入り登録されます。
      もう一度クリックするとお気に入りから外れます。
    </p>
    <p>
      お気に入りの上限はありません。<br>
      お気に入りはお使いのPCブラウザに保存されますので、
      PCを変えたりブラウザを変えたりしますとお気に入りは消えてしまいます。
    </p>
    <!--
    <h3>どうしてもお気に入りを他のPCに移行したい</h3>
    <p>どうしてもお気に入り移行したい場合は以下の手順を実行してください。</p>
    <p>1. 移行前のPCで開発者ツールからコンソールを開き、以下のコマンドを実行してください</p>
    <code>
      localStorage.getItem("favorites")
    </code>
    <p>2. 文字列が出てきます</p>
    <code>
      "[{\"url\":\"https://lgtmoon.herokuapp.com/images/23196\",\"isConverted\":true}]"
    </code>
    <p>3. 移行先のPCで開発者ツールを開き、先ほどの文字列をlocalStorageにSetしてください。</p>
    <code>
      localStorage.setItem("favorites", "[{\"url\":\"https://lgtmoon.herokuapp.com/images/23196\",\"isConverted\":true}]")
    </code>
    -->
  </section>
</template>

<style scoped>
  .favorite-help-cat {
    width: 320px;
  }
</style>
