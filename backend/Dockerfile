FROM openjdk:21 as build
WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:21
WORKDIR /app

COPY --from=build /app/target/backend-1.0.jar backend-app.jar

ENTRYPOINT ["java","-jar","backend-app.jar"]

