FROM gradle:jdk8-alpine as build
COPY --chown=gradle:gradle ./ /home/gradle/
WORKDIR /home/gradle/
RUN gradle clean build -x test

FROM openjdk:8-jre-alpine as docker
COPY --from=build /home/gradle/build/libs/se09-cert-service-*-all.jar se09-cert-service.jar
EXPOSE 7878
CMD java -Dcom.sun.management.jmxremote -noverify ${JAVA_OPTS} -jar se09-cert-service.jar
