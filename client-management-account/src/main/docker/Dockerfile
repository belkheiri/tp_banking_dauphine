FROM lwieske/java-8

VOLUME /tmp

EXPOSE 9500

RUN sh -c "mkdir -p /artifacts"
ADD client-management-account.jar /artifacts/app.jar
RUN sh -c "ls -ltr /artifacts"
RUN sh -c "cd /artifacts && df -hP"
ENTRYPOINT ["sh", "-c", "java -jar /artifacts/app.jar"]