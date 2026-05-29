FROM maven:3.9.11-eclipse-temurin-17 AS build
WORKDIR /workspace

COPY pom.xml ./
COPY .mvn .mvn
COPY src src
COPY privateKey.pem publicKey.pem ./

RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /workspace/target/quarkus-app/lib/ /app/lib/
COPY --from=build /workspace/target/quarkus-app/*.jar /app/
COPY --from=build /workspace/target/quarkus-app/app/ /app/app/
COPY --from=build /workspace/target/quarkus-app/quarkus/ /app/quarkus/

ENV QUARKUS_HTTP_HOST=0.0.0.0
ENV JAVA_OPTS="-Djava.util.logging.manager=org.jboss.logmanager.LogManager"

EXPOSE 10000

CMD ["sh", "-c", "java $JAVA_OPTS -Dquarkus.http.port=${PORT:-10000} -jar /app/quarkus-run.jar"]
