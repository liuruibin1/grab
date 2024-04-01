FROM openjdk:11-jdk

ENV PARAMS=""

ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD target/$APP_NAME*.jar /app.jar

ENTRYPOINT ["sh","-c","java -jar -Drun.nacos.host=$RUN_NACOS_HOST -Drun.nacos.port=$RUN_NACOS_PORT -Drun.nacos.namespace=$RUN_NACOS_NAMESPACE -Drun.service.group=$RUN_SERVICE_GROUP -Drun.spring.profile=$RUN_SPRING_PROFILE $JAVA_OPTS /app.jar"]