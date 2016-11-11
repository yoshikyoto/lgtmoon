# lgtmoon

キーワードを入力するだけで簡単にLGTM画像を生成できるWevアプリです。

## 動作URL

http://lgtmoon.herokuapp.com/

## Dependency

* Scala
* Play Framwork
* Slick 3.1
* PostgreSQL
* imagemagick
* jQuery
* vue.js

## License

This application is licensed under the MIT License, see LICENSE.txt.

## API

[Wiki](https://github.com/yoshikyoto/lgtmoon/wiki) を御覧ください。

## 動作環境作成手順

### データベースの作成

`sql/create.sql` をデータベースに流してください。

例: 

```
psql# create database lgtmoon owner postgres encoding 'UTF8';
$ psql lgtmoon < create.sql
```

Tips: postgresデーモンの起動方法

```
$ postgres -D /usr/local/var/postgres
```

### application.conf の作成

`conf/application.conf.template` を参考に `conf/application.conf` を作成してください。

TODO: どこを設定すればいいか書く

## Tips

### Slick Codegen

sql以下がSlick codegen のために必要なコードとなっています。
実行するには以下のコマンドを叩けばOkです。

```
$ cd sql
$ sbt run
```

### Herokuにデプロイする例

```
$ heroku create <app-name>
$ git checkout -b deploy

# create application.conf for heroku here

$ git commit application.conf
$ git push heroku deploy:master
```
#### curl でAPIを叩く

```
$ curl http://host/image.json -X POST -H "content-type: application/json" -d '{"url":url}'
```
