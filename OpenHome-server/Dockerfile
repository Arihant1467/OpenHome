FROM tomcat:9.0.30-jdk11-openjdk

LABEL maintainer="arihant95@gmail.com"
WORKDIR /usr/local/tomcat/webapps

COPY ./target/*.war .

EXPOSE 8080

CMD ["catalina.sh", "run"]