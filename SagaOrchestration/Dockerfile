FROM maven:3-jdk-11 as builder
WORKDIR /app
COPY . .
RUN mvn package -DskipTests -Pdocker

FROM openjdk:11-jdk
COPY --from=builder /app/target/sagaorchestration-0.0.1-SNAPSHOT.jar ./sagaorchestration.jar
CMD java -jar sagaorchestration.jar

# to build/rebuild it:
# docker build --no-cache -t unict/sagaorchestration:0.0.1 -f Dockerfile .
# ALWAYS tag a build with a version!

# to run it:
# docker run -d --rm --name sagaorchestration -p 8081:8081 unict/sagaorchestration:0.0.1
# and ALWAYS include the desired tag!
