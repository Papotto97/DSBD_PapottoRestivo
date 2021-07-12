FROM maven:3-jdk-11 as builder
WORKDIR /app
COPY . .
RUN mvn package -DskipTests

FROM openjdk:11-jdk
COPY --from=builder /app/target/discovery-service-0.0.1-SNAPSHOT.jar ./discovery-service.jar
CMD java -jar discovery-service.jar

# to build/rebuild it:
# docker build --no-cache -t discovery-service:1.0 -f Dockerfile .
# ALWAYS tag a build with a version!

# to run it:
# docker run -d --rm --name discovery-service -p 8761:8761 discovery-service:1.0
# and ALWAYS include the desired tag!
