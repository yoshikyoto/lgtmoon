# lgtmoon

キーワードを入力するだけで簡単にLGTM画像を生成できるWebアプリです。

LGTMoon is the web service to generate LGTM Image.

## URL

https://lgtmoon.herokuapp.com/

## Dependency

* Scala 2.11
* Play Framwork 2.6
* Slick 3.2
* PostgreSQL 10
* imagemagick
* yarn
* Vue.js

## License

This application is licensed under the MIT License, see LICENSE.txt.

## 動作環境作成手順

### Dockerを使ったPostgreSQL環境の構築

```sh
docker-compose up -d

# 接続確認
# localhost の 5432 が docker の 5432 にプロキシされる設定になっています
# 詳しくは docker-compose.yml を見てください
psql -U postgres -h 127.0.0.1 lgtmoon --password
```

### Dockerを使わない場合

`sql/init/1_create.sql` をデータベースに流してください。
（Dockerの場合はこのSQLが自動的に流れる設定になっているはずです）

例:

```
psql# create database lgtmoon owner postgres encoding 'UTF8';
$ psql lgtmoon < create.sql
```

PostgreSQLの操作などについてはwikiなどを見てください。

### application.conf

`conf/application.conf.sample` を参考に `conf/application.conf` を作成し、
下記を設定してください。

secretの値を変更

```
play.crypto.secret = "changeme"
```

PostgreSQLの接続情報の設定

```
pg_database = {
  url = "jdbc:postgresql://127.0.0.1/lgtmoon?user=postgres&password=postgres"
  driver = org.postgresql.Driver
  connectionPool = disabled
  keepAliveConnection = true
}
```

imagemagickのPATHの設定

```
imagemagick.dir = /usr/:/usr/bin
```

storageの設定

```
storage.image.src.dir = "/tmp"
storage.image.dest.dir = "/tmp"
```

image.baseUrl の設定

```
image.baseUrl = "https://yourhost:9000/images"
```

## 注意点

Herokuにデプロイする際に楽なので、`node_modules`やコンパイル後のCSSもすべてリポジトリに含めています。

## Contribute

このコードはクリーンアーキテクチャーを意識して書かれています。
（[参考](https://scrapbox.io/kadoyau/DDD%E3%81%AB%E9%96%A2%E3%82%8F%E3%82%8B%E3%82%A2%E3%83%BC%E3%82%AD%E3%83%86%E3%82%AF%E3%83%81%E3%83%A3)）

矢印のような依存関係になっています。例えば、Infra層はDomain層に依存しますが、
Domain層はInfra層に依存してはいけません。

![クリーンアーキテクチャーのイメージ図](https://i.gyazo.com/thumb/1000/4df38ec2d5f7e302e56f7c87ee4052c9-png.png)

### LGTMoonのクリーンアーキテクチャ構造

Presentation層/UseCase層
* LGTMoonは小さなアプリケーションで、ログイン機構が無いため、Presentation層とUseCase層は区別されていません
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
