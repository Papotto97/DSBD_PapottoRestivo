FROM maven:3-jdk-11 as builder
WORKDIR /app
COPY . .
RUN mvn package -DskipTests -Pdocker

FROM openjdk:11-jdk
COPY --from=builder /app/target/walletmanager-0.0.1-SNAPSHOT.jar ./walletmanager.jar
CMD java -jar walletmanager.jar

# to build/rebuild it:
# docker build --no-cache -t unict/walletmanager:0.0.1 -f Dockerfile .
# ALWAYS tag a build with a version!

# to run it:
# docker run -d --rm --name walletmanager -p 8084:8084 unict/walletmanager:0.0.1
# and ALWAYS include the desired tag!
