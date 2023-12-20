This is a basic springboot application which offers rest api to perform curd operations.

To Run the project,there are some environment requirements.
1. Java 11 should be installed.
2. Mvn should be installed and set at environment path.

Now to run the application first follow the below steps-
1. We are using the mongodb atlas as our nosql db , we can provide the database url in the applicaiton.properties file at /src/main/resources/application.properties file.
2. In the same application.properties file we need to provide the emailaddress(along with its password) which we want to use to send email notifications on employee creation.

3. Now execute command "mvn clean install" inside employeeCrud directory to build and pkg the project as jar.
4. Finally, to start the application execute "java -jar .\target\employeeCrud-0.0.1-SNAPSHOT.jar". After this command the project will be started and you can see the application logs on the caller console.

5. To use the application api routes, check the EmployeeCrudApis.pdf file which have description regarding the available api endpoints along with their payload and other attributes structure.