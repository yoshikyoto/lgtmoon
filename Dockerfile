FROM golang:1.19

RUN apt update -y \
 && apt upgrade -y \
 && apt install -y imagemagick

