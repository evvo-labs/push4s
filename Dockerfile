FROM openjdk:latest

ENV SCALA_VERSION 2.13.1

WORKDIR /opt/docker/

ADD target/scala-2.13/push4s_2.13-0.1.jar push.jar
ADD lib/evvo_2.13-0.1.0-jar-with-dependencies.jar evvo.jar
ADD src/main/resources/small-or-large.json benchmark.json

# Install Scala
#RUN touch /usr/lib/jvm/java-8-openjdk-amd64/release
## Piping curl directly in tar
RUN \
  curl -fsL http://downloads.typesafe.com/scala/$SCALA_VERSION/scala-$SCALA_VERSION.tgz | tar xfz - -C /root/

# Put this last because it changes most frequently, build as little as possible
ADD docker_entrypoint.sh docker_entrypoint.sh
ENTRYPOINT ["./docker_entrypoint.sh"]
