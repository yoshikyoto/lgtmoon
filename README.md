# lgtmoon

## Dependency

* Scala
* Play Framwork
* Slick 3.1
* PostgreSQL

## PostgreSQL

### Create Database

```
# create database lgtmoon owner postgres encoding 'UTF8';
$ psql lgtmoon < create.sql
```

### Slick Codegen

通常は必要ありません。Slick Codegenの参考にどうぞ。

```
$ cd sql
$ sbt run
```

