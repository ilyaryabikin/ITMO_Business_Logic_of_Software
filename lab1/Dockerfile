FROM bellsoft/liberica-openjre-alpine:11

ARG APPLICATION_ARTIFACT_NAME
ARG APPLICATION_VERSION
ARG DATABASE_HOST
ARG DATABASE_PORT

RUN apk add --no-cache bash git
RUN git clone https://github.com/vishnubob/wait-for-it.git
RUN chmod +x /wait-for-it/wait-for-it.sh

COPY target/${APPLICATION_ARTIFACT_NAME}-${APPLICATION_VERSION}.jar /${APPLICATION_ARTIFACT_NAME}.jar

ENV APP_ARTIFACT=${APPLICATION_ARTIFACT_NAME}
ENV DB_HOST=${DATABASE_HOST}
ENV DB_PORT=${DATABASE_PORT}

ENTRYPOINT exec wait-for-it ${DB_HOST}:${DB_PORT} -- java -jar /${APP_ARTIFACT}.jar
