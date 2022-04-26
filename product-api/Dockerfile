FROM maven:3.8.5-jdk-11
COPY . .
WORKDIR .
RUN mvn package -DskipTests
EXPOSE 8081
CMD [ "java", "-jar", "target/product-api-0.0.1-SNAPSHOT.jar" ]