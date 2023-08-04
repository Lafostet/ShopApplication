FROM maven:3.8.3-openjdk-17 AS build
ARG APP_NAME=ShopApplication
COPY src /$APP_NAME/src
COPY pom.xml /$APP_NAME
RUN mvn -f /$APP_NAME/pom.xml clean package
EXPOSE 8080
ENTRYPOINT ["java","-jar","/$APP_NAME/target/shop.jar"]