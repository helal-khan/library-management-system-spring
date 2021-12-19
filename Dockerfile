FROM openjdk:11 as mysqldoc
EXPOSE 8080
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .

COPY ./src ./src
COPY ./pom.xml ./pom.xml

RUN chmod 755 /app/mvnw

RUN ./mvnw dependency:go-offline -B

RUN ./mvnw package -DskipTests
RUN ls -al
ENTRYPOINT ["java","-jar","target/library-management-system-spring-0.0.1-SNAPSHOT.jar"]