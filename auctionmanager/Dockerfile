FROM maven:3-jdk-11 as builder
WORKDIR /app
COPY . .
RUN mvn package -DskipTests

FROM openjdk:11-jdk
COPY --from=builder /app/target/auctionmanager-0.0.1-SNAPSHOT.jar ./auctionmanager.jar
CMD java -jar auctionmanager.jar

# to build/rebuild it:
# docker build --no-cache -t unict/auctionmanager:0.0.1 -f Dockerfile .
# ALWAYS tag a build with a version!

# to run it:
# docker run -d --rm --name auctionmanager -p 8083:8083 unict/auctionmanager:0.0.1
# and ALWAYS include the desired tag!
