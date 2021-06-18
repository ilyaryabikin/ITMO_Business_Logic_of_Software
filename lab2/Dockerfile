FROM adoptopenjdk/openjdk16-openj9:alpine-jre

ARG APPLICATION_ARTIFACT_NAME
ARG APPLICATION_VERSION
ARG DATABASE_HOST
ARG DATABASE_PORT

RUN apk add --no-cache wget
RUN wget -t inf https://github.com/eficode/wait-for/releases/download/v2.1.2/wait-for
RUN chmod +x ./wait-for

COPY build/libs/${APPLICATION_ARTIFACT_NAME}-${APPLICATION_VERSION}.jar /${APPLICATION_ARTIFACT_NAME}.jar

ENV APP_ARTIFACT=${APPLICATION_ARTIFACT_NAME}
ENV DB_HOST=${DATABASE_HOST}
ENV DB_PORT=${DATABASE_PORT}

ENTRYPOINT exec ./wait-for ${DB_HOST}:${DB_PORT} -- java -jar /${APP_ARTIFACT}.jar
