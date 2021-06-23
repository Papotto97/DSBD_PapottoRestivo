FROM jboss/base-jdk:8
EXPOSE 8080

# using jboss user as one java jar is started under
USER root
RUN mkdir /data \
  && chown -R jboss /data \
  && usermod -g root -G `id -g jboss` jboss \
  && chmod -R "g+rwX" /data \
  && chown -R jboss:root /data
RUN mkdir /deployments \
  && chown -R jboss /deployments \
  && chmod -R "g+rwX" /deployments \
  && chown -R jboss:root /deployments
USER jboss

ARG NARAYANA_VERSION="5.11.1.Final"
RUN test -n "$NARAYANA_VERSION" || (echo "NARAYANA_VERSION  not set" && false)

ADD narayana-full-${NARAYANA_VERSION}-bin.zip /deployments/

ARG NARAYANA_FULL_URL="https://www.jboss.org/jbosstm/downloads/${NARAYANA_VERSION}/binary/narayana-full-${NARAYANA_VERSION}-bin.zip"
RUN unzip -j /deployments/narayana-full-${NARAYANA_VERSION}-bin.zip narayana-full-${NARAYANA_VERSION}/rts/lra/lra-coordinator-runner.jar -d /deployments/ 

COPY run-lra-coordinator.sh /deployments

# expected values INFO, DEBUG, TRACE...
ENV LOG_LEVEL="INFO"
# path to the data directory (persistent volume)
ENV OBJECT_STORE_PATH="/data"

ENTRYPOINT ["/bin/sh", "/deployments/run-lra-coordinator.sh"]
CMD [ "/deployments/lra-coordinator-runner.jar" ]