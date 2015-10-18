# lgtmoon

## What's this

Generate LGTM image from keyword.

## Dependency

* Scala
* Play Framwork
* Slick 3.1
* PostgreSQL
* imagemagick

## License

This software is licensed under the MIT License, see LICENSE.txt.

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

None

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
