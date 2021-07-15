FROM maven:3-jdk-11 as builder
WORKDIR /app
COPY . .
RUN mvn package -DskipTests -Pdocker

FROM openjdk:11-jdk
COPY --from=builder /app/target/gateway-service-0.0.1-SNAPSHOT.jar ./gateway-service.jar
CMD java -jar gateway-service.jar

# to build/rebuild it:
# docker build --no-cache -t gateway-service:1.0 -f Dockerfile .
# ALWAYS tag a build with a version!

# to run it:
# docker run -d --rm --name gateway-service -p 8088:8088 gateway-service:1.0
# and ALWAYS include the desired tag!
