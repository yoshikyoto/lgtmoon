# lgtmoon

## このアプリケーションは？

キーワードを入力するだけで、
画像を検索してLGTM画像を生成してくれるアプリケーションです。

## Dependency

* Scala
* Play Framwork
* Slick 3.1
* PostgreSQL
* imagemagick

## 起動方法

### データベースの作成

sql/create.sql を実行してください。

```
# create database lgtmoon owner postgres encoding 'UTF8';
$ psql lgtmoon < create.sql
```

### application.conf の作成

データベースの設定などに合わせて
`api/conf/application.conf.template` から
`api/conf/application.conf` を作成してください。

### Slick Codegen

通常は必要ありません。Slick Codegenの参考にどうぞ。

```
$ cd sql
$ sbt run
```

## API

### 新着画像取得

#### 概要

変換済みの画像を最新から20件取得します。

### URL

GET /recent.json

#### リクエストパラメーター

なし

#### レスポンス例

```json
{
  "images": [
    {"url":"http://host/images/1"},
    {"url":"http://host/images/2"}
  ]
}
```

### キーワード投稿

#### 概要

キーワードを投稿すると、そのキーワードにマッチしたLGTM画像を生成します。

キーワードを投稿すると、URLを生成して返します。
画像の生成はバックグラウンドで行われます。

#### リクエストパラメーター

* kayword: キーワード

#### レスポンス例

```json
{"url":"http://host/images/1"}
```

#### ターミナルからcurlでPOSTする例

```
$ curl http://localhost:9000/keyword.json -X POST -H "content-type: application/json" -d '{"keyword":"splatoon"}'
```
