Below actions are to be performed via command prompt to run the application
----------------------------------------------------------------------------
mvn dependency:tree
mvn spring-boot:run
mvn package
java -jar target\SpringBootTodoApplication-1.0.0.jar  --spring.profiles.active=prod (production profile)
java -jar target\SpringBootTodoApplication-1.0.0.jar  --spring.profiles.active=local (local/default profile)