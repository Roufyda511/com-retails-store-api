FROM ibm-semeru-runtimes:open-17.0.8.1_1-jre
# Set the server's time zone for the container
ENV TZ=Europe/Berlin
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# update sources
RUN apt-get update
# You can install some packages
#RUN apt-get install -y curl
# run under a user. This makes the whole thing more secure
RUN groupadd normalgroup
RUN useradd -G normalgroup normaluser
USER normaluser:normalgroup

# run app
ARG JAR_FILE=target/com-retails-store-api-*.jar
COPY ${JAR_FILE} com-retails-store-api-VERSION.jar
ENTRYPOINT ["java","-jar","com-retails-store-api-VERSION.jar"]
