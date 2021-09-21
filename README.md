# lgtmoon

キーワードを入力するだけで簡単にLGTM画像を生成できるWebアプリです。

LGTMoon is the web service to generate LGTM Image.

## URL

https://lgtmoon.dev/

## Dependency

* sbt のバージョンは `project/build.properties` で確認できます
* Scala のバージョンは `build.sbt` で確認できます
* Play Framework のバージョンは `project/plugins.sbt` で確認できます
* その他、Scala のライブラリ依存は `build.sbt` で確認できます
* PostgreSQL 10.15
* imagemagick
* yarn
* Vue.js

## License

This application is licensed under the MIT License, see LICENSE.txt.

## 開発環境作成手順

### Docker を使う場合

- S3 を利用するため、AWS の認証情報を設定してください。方法は2つあります
  - PC の ~/.aws/credentials に lgtmoon_dev という provider を設定する
  - docker-compose.yml で、環境変数として `AWS_ACCESS_KEY_ID` と `AWS_SECRET_ACCESS_KEY` を指定する
    - 詳細は `conf/application.conf` を確認する
    - TODO: S3 環境も docker で用意する？
- TODO: Google 検索について

```
# Docker のビルドをする
docker build -t lgtmoon .

# Docker を立ち上げる
# 立ち上がるまでに少し（数分）時間がかかります
docker-compose up -d
```

### Dockerを使ったPostgreSQL環境の構築

```sh
docker-compose up -d

# 接続確認
# localhost の 5432 が docker の 5432 にプロキシされる設定になっています
# 詳しくは docker-compose.yml を見てください
psql -U postgres -h 127.0.0.1 lgtmoon --password
```

### Dockerを使わない場合

利用したいデータベースに `sql/init/1_create.sql` を流してください。

例:

```
psql# create database lgtmoon owner postgres encoding 'UTF8';
$ psql lgtmoon < create.sql
```

PostgreSQLの操作などについてはwikiなどを見てください。

### application.conf

基本的に `conf/application.conf` にはデフォルト値が設定されており、
ローカルでの開発では値を変更しなくて良いようになっています。

ただし、以下の設定だけ行ってください。

Google 検索の動作確認をする場合は、 Google の API を叩くのに必要な
`google.key` と `google.cx` を設定してください。

* TODO: この Google の処理をモックに置き換えられないか

LGTM 画像アップロードの動作確認をしたい場合、
S3 が利用できる AWS アカウントを設定する必要があります。

`aws.provider` に設定されている provider に対応した、
`~/.aws/credentials` の設定が利用されます。  

`~/.aws/credentials` の例

```
[lgtmoon_dev]
aws_access_key_id = XXXXX
aws_secret_access_key = YYYYYYYYYY
region = ap-northeast-1
```

## Contribute

このコードはクリーンアーキテクチャーを意識して書かれています。
（[参考](https://scrapbox.io/kadoyau/DDD%E3%81%AB%E9%96%A2%E3%82%8F%E3%82%8B%E3%82%A2%E3%83%BC%E3%82%AD%E3%83%86%E3%82%AF%E3%83%81%E3%83%A3)）

矢印のような依存関係になっています。例えば、Infra層はDomain層に依存しますが、
Domain層はInfra層に依存してはいけません。

![クリーンアーキテクチャーのイメージ図](https://i.gyazo.com/thumb/1000/4df38ec2d5f7e302e56f7c87ee4052c9-png.png)

### LGTMoonのクリーンアーキテクチャ構造

Presentation層/UseCase層
* LGTMoonは小さなアプリケーションで、ログイン機能が無いため、Presentation層とUseCase層は区別されていません
* `app/controllers` 以下がこの層にあたります
* `app/views` も一応この層と言えます
* `app/actor` も UseCase層のものですが、現状は `app/actor` の整備が進んでいないためどこの層にも属さないような立ち位置になっています

Domain層
* `app/image` 以下がこの層にあたります
* アプリケーションの核となる部分で、「このアプリケーションは何が可能なのか」といったことを表します

Infra層
* `app/storage`, `app/database`, `app/command`, `app/external`, あたりがこの層といえます
* ストレージやデータベースにアクセスする部分です
* OSやサーバー環境などに依存する部分です 

## Tips

### Slick Codegen

sql直下がSlick codegen のために必要なコードとなっています。
実行するには以下のコマンドを叩けばOkです。

```
$ cd sql
$ sbt run
```

### Deploy to Heroku

```
$ heroku create <app-name>
$ cd frontend
$ yarn run build
$ yarn run release
$ cd ..
$ git add public
$ git commit

$ git checkout -b deploy

# create application.conf for heroku here

$ git commit application.conf
$ git push heroku deploy:master
```
