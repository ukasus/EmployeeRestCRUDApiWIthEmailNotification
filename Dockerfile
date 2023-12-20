FROM bellsoft/liberica-runtime-container:jdk-17-stream-musl as builder
WORKDIR /home/myapp
ADD employeeCrud /home/myapp/employeeCrud
RUN cd employeeCrud && ./mvnw package
FROM bellsoft/alpaquita-linux-base:stream-musl-230404
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
VOLUME /tmp
WORKDIR /home/myapp
COPY --from=builder /home/myapp/employeeCrud/target .
EXPOSE 8080
CMD ["java", "-jar", "employeeCrud-0.0.1-SNAPSHOT.jar"]