FROM openjdk:17-alpine

ADD ./ /pagamento-service

ENV REGISTRY_PREFER_IP true
ENV APPLICATION_NAME pagamento-service
ENV SPRING_PROFILES_ACTIVE prod

RUN cd /pagamento-service && ./mvnw clean && ./mvnw install -Dmaven.test.skip=true

ENTRYPOINT ["java", "-Duser.timezone=GMT-03:00", "-Xmx256m", "-Xmx184m", "-jar", "/pagamento-service/target/pagamento-service.jar"]