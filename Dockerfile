FROM hseeberger/scala-sbt:8u151-2.12.4-1.1.0

RUN apt update -y
RUN apt upgrade -y
RUN apt install -y postgresql-client

COPY . /opt/lgtmoon

WORKDIR /opt/lgtmoon
RUN sbt compile

CMD sbt run
