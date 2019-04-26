# lgtmoon

キーワードを入力するだけで簡単にLGTM画像を生成できるWebアプリです。

LGTMoon is the web service to generate LGTM Image.

## URL

https://lgtmoon.herokuapp.com/

## Dependency

* Scala 2.11
* Play Framwork 2.5
* Slick 3.2
* PostgreSQL 10
* imagemagick
* vue.js

## License

This application is licensed under the MIT License, see LICENSE.txt.

## API

[Wiki](https://github.com/yoshikyoto/lgtmoon/wiki) をご覧ください。

See [Wiki](https://github.com/yoshikyoto/lgtmoon/wiki).

## 動作環境作成手順

### PostgreSQL on docker for Local development

```
docker-compose up -d
psql -U postgres -h 127.0.0.1 lgtmoon --password
```

### Create Database

`sql/init/1_create.sql` をデータベースに流してください。

例:

```
psql# create database lgtmoon owner postgres encoding 'UTF8';
$ psql lgtmoon < create.sql
```

Tips: postgresデーモンの起動方法

```
$ postgres -D /usr/local/var/postgres
```

### application.conf

`conf/application.conf.sample` を参考に `conf/application.conf` を作成してください。

postgresのパスワードなどの設定

```
pg_database = {
  ...
}
```

imagemagickのPATHの設定

```
imagemagick.dir = /usr/:/usr/bin
```

画像配信のホストの設定

```
storage.hostName = "lgtmoon.herokuapp.com"
```

secretの値を変更

```
play.crypto.secret = "changeme"
```

## 注意点

Herokuにデプロイする際に楽なので、`node_modules`やコンパイル後のCSSもすべてリポジトリに含めています。


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
#### curl でAPIを叩く

```
$ curl http://host/image.json -X POST -H "content-type: application/json" -d '{"url":url}'
```
