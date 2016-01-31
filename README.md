# lgtmoon

## What's this

キーワードを入力するだけでLGTM画像を作成してくれるアプリです。

Generate LGTM image from keyword.

## URL

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

See Wiki.

## Deploy

### create Database

Execute sql/create.sql

Example

```
psql# create database lgtmoon owner postgres encoding 'UTF8';
$ psql lgtmoon < create.sql
```

### create application.conf

Create `conf/application.conf`
You can see `conf/application.conf.template` as referense.

## Tips

### Slick Codegen

sql以下がSlick codegen のために必要なコードとなっています。
実行するには以下のコマンドを叩けばOkです。

```
$ cd sql
$ sbt run
```

### First deploy for Heroku

```
$ heroku create <app-name>
$ git checkout -b deploy

# create application.conf for heroku here

$ git commit application.conf
$ git push heroku deploy:master
```
#### API Test Example with curl

```
$ curl http://host/image.json -X POST -H "content-type: application/json" -d '{"url":url}'
```
