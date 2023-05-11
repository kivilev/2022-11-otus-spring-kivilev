FROM bellsoft/liberica-openjdk-alpine:11

RUN addgroup -S appuser-group
RUN adduser -D -G appuser-group -h /home/appuser appuser

USER appuser
WORKDIR /home/appuser

COPY /build/libs/app.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]