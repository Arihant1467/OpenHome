FROM tomcat:9.0.27-jdk8-openjdk

LABEL maintainer="arihant95@gmail.com"
WORKDIR /usr/local/tomcat/webapps

COPY ./target/*.war .

EXPOSE 8080

CMD ["catalina.sh", "run"]