FROM golang:1.19

RUN apt update -y \
 && apt upgrade -y \
 && apt install -y imagemagick

# ライブリロードのためのライブラリ gin を入れる
# （Web フレームワークの gin とは別物）
# 開発でしか使わないので、バージョンは特に気にせず latest をインストールする
RUN go install github.com/codegangsta/gin@latest