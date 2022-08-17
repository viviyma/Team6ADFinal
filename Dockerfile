FROM maven:3.8.2-jdk-11
WORKDIR /Team6ADFinal
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run
