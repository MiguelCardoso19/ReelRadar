FROM openjdk:21

COPY target/reelradar-0.0.1-SNAPSHOT.jar /app/reelradarapi.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "reelradarapi.jar"]