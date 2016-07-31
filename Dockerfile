FROM java:openjdk-8-alpine
MAINTAINER vchalupa@redhat.com

ENV PORT=80

ENV LOG_LEVEL=INFO
ENV MONGO_HOST=mongo
ENV MONGO_PORT=27017
ENV DB_SEED=yes

EXPOSE 80

COPY target/mobile-screener-api-1.0.0-SNAPSHOT.jar /usr/src/mobile-screener/
COPY configuration.yml /usr/src/mobile-screener/

WORKDIR /usr/src/mobile-screener
CMD ["java", "-jar", "mobile-screener-api-1.0.0-SNAPSHOT.jar", "server", "configuration.yml"]
