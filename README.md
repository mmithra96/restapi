# restapi
A simple user management and authentication system
* Create, Update, Delete, List web service endpoints for a User object.
* User object should contain a name, email address, password and the date of their last login.
* Provide a login endpoint that validates the email address and password provided by the user
### Tech-stack
* OpenJDK 1.11
* Maven
* Amazon Web Services - DynamoDb
### Build the Application
To build the application navigate to the root directory and run
```shell
mvn clean install
```
### Run the Application
To start the application navigate to the root directory and run
```shell
mvn spring-boot:run
```
Once the Springboot Application Starts

```shell
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.6.6)

21:37:03.141 [main] INFO  com.mithra.demo.RestapiApplication - Starting RestapiApplication using Java 17.0.2 on Mitmani with PID 26800 (C:\Users\maniv\eclipse-workspace\mithra\restapi\target\classes started by mit in C:\Users\maniv\eclipse-workspace\mithra\restapi)
21:37:03.147 [main] INFO  com.mithra.demo.RestapiApplication - No active profile set, falling back to 1 default profile: "default"
21:37:06.096 [main] INFO  o.a.catalina.core.StandardService - Starting service [Tomcat]
21:37:06.096 [main] INFO  o.a.catalina.core.StandardEngine - Starting Servlet engine: [Apache Tomcat/9.0.60]
21:37:06.428 [main] INFO  o.a.c.c.C.[Tomcat].[localhost].[/] - Initializing Spring embedded WebApplicationContext
21:37:07.822 [main] INFO  com.mithra.demo.RestapiApplication - Started RestapiApplication in 6.849 seconds (JVM running for 9.238)
```
Navigate to the browser and open http://localhost:8082/api/health for the API health Check. This returns
```shell
Health is good
```
### Endpoints
Below are the endpoints of the api and sample onSucess and OnFailure messages

| Endpoint(s)  |Method       |  Request Body/Path variables      |   OnSuccess   | OnFailure   | Description   | 
|--------------|-------------|----------------------------- | -------------   |------------|------------|
|  /api/health  |GET  | None  | Health is good   |N/A   | Health of API
| /api/v1/user/auth/login  |POST| User object  | User Login Successful   | Unauthorized!: User Not Found  | Allows user to login   |
|  /api/v1/user/createUser|POST|User object  | User created Successfully   | User Creation Failure : User does not Exist  | Creates new User   |
|  /api/v1/user/updateUser|PUT|User object  | User updated Successfully   | User Update Failure : User does not Exist  | Updates existing User   |
|  /api/v1/user/deleteUser|DELETE|emailId  | User deleted Successfully   | User deletion Failure : User does not Exist  | Delted existing User   |
|  /api/v1/user/Users|GET|emailId  | {List of Users}  | None  | List all users   |
|  /api/user/logout|GET|User Object  |User Logged out Successfully.  | User does not exist  | Allows user to logout |



