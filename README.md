# lgtmoon

## What's this

キーワードを入力するだけでLGTM画像を作成してくれるアプリです。

Generate LGTM image from keyword.

## URL

http://lgtmoon.herokuapp.com/

## Usage from Command Line

```
$ wget https://raw.githubusercontent.com/yoshikyoto/lgtmoon/master/bin/lgtmoon
$ chmod 755 lgtmoon
$ ./lgtmoon ニコニコテレビちゃん
```

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

## Usage

### create Database

Execute sql/create.sql

Example

```
psql# create database lgtmoon owner postgres encoding 'UTF8';
$ psql lgtmoon < create.sql
```

### create application.conf 

TBA

## API

### GET /recent.json

Get 20 images recently generated.

#### Request Parameters

Create `conf/application.conf` like an example `conf/application.conf.template`

#### Sample Response

```json
{
  "images": [
    {"url":"http://host/images/1"},
    {"url":"http://host/images/2"}
  ]
}
```

### POST /keyword.json

Return the URL for LGTM image.
And generate LGTM image from keyword in background.

#### Request Parameters

* kayword: keyword

#### Sample Response

```json
{"url":"http://host/images/1"}
```

#### Example for curl

```
$ curl http://host/keyword.json -X POST -H "content-type: application/json" -d '{"keyword":"splatoon"}'
```

## Tips

### Slick Codegen

sql以下がSlick codegen のために必要なコードとなっています。
実行するには以下のコマンドを叩けばOkです。

```
$ cd sql
$ sbt run
```

### Herokuへのデプロイ方法

```
$ heroku create <app-name>
$ git checkout -b deploy
# ここでHerokuでの設定に合わせてapplication.conf を用意
$ git commit application.conf
$ git push heroku deploy:master
```
