FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ADD ./target/jptechcatalog-backend-0.0.1-SNAPSHOT.jar jptech-catalog.jar
ENTRYPOINT ["java","-jar","/jptech-catalog.jar"]
