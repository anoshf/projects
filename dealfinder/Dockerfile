FROM java:8
# RUN apt-get update && apt-get install && apt-get clean && \
RUN mkdir -p /data/

#WORKDIR target/
ADD distribution/scripts/run.sh /data/run.sh
ADD target/dealfinder*.jar dealfinder.jar
EXPOSE 8080

#CMD java - jar dealfinder.jar
ENTRYPOINT ["/data/run.sh"]
