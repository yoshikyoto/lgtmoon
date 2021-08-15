FROM hseeberger/scala-sbt:8u302_1.5.5_2.13.6

RUN apt update -y \
 && apt upgrade -y \
 && apt install -y postgresql-client imagemagick

COPY . /lgtmoon

WORKDIR /lgtmoon
RUN sbt compile

CMD sbt run
