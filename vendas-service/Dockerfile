FROM openjdk:17-alpine

ADD ./ /vendas-service

ENV REGISTRY_PREFER_IP true
ENV APPLICATION_NAME vendas-service
ENV SPRING_PROFILES_ACTIVE prod

RUN cd /vendas-service && ./mvnw clean && ./mvnw install -Dmaven.test.skip=true

ENTRYPOINT ["java", "-Duser.timezone=GMT-03:00", "-Xmx256m", "-Xmx184m", "-jar", "/vendas-service/target/vendas-service.jar"]