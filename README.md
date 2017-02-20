# SimpleMovieDB
Simple Demo using movie data to show off Spring Data JPA

## Tools you'll need
1. [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
   - Note: You don't have to use Oracle Java if you don't want, could be OpenJDK
1. [Docker](https://docs.docker.com/engine/installation/)
1. [SQuirrel SQL](http://www.squirrelsql.org)

## Running the application
To run tests and generate the "uber" Spring Boot Jar
./gradlew clean build

## Application description
Example Project provides 4 possible modes via 3 of which require Docker
1. embedded
   - uses H2 an embeddable native Java database that is useful for testing
   - starts and stops with the application
   - configuration
     * application.yaml -> default when not ran with --profile this one will be picked
     * configures the JDBC driver for H2
   - To run
     * java -jar ./build/libs/SimpleMovieDB.jar --profile=local --tmdb.api.key=${TMDB_API_KEY}
   - The H2 web console has been enabled it can be reached by going to [H2-Console](http://localhost:8080/h2-console)
     * just use the default provided credentials and click connect
1. mysql
   - uses MySQL 5.7
   - requires Docker
   - can be run with mysql-docker-start.sh and stopped with mysql-docker-stop.sh
   - configuration
     * application-mysql.yaml
     * configures the JDBC driver for MySQL 5.7
     * runs when --profile=mysql
   - To run
     * java -jar ./build/libs/SimpleMovieDB.jar --profile=mysql --tmdb.api.key=${TMDB_API_KEY}
   - Connect to the running database using a SQL database access tool such as [SQuirrel SQL](http://www.squirrelsql.org)
     * url: jdbc:mysql://localhost:3306/dev
     * username: developer
     * password: password
     * driver: com.mysql.jdbc.Driver
1. postgres
   - uses Postgres version 9.6.1
   - requires Docker
   - can be run with postgres-docker-start.sh and stopped with postgres-docker-stop.sh
   - configuration
     * application-postgres.yaml
     * configures the JDBC driver for Postgres
     * runs when --profile=postgres
   - To run
     * java -jar ./build/libs/SimpleMovieDB.jar --profile=postgres --tmdb.api.key=${TMDB_API_KEY}
   - Connect to the running database using a SQL database access tool such as [SQuirrel SQL](http://www.squirrelsql.org)
     * url: jdbc:postgresql://localhost:5432/dev
     * username: developer
     * password: password
     * driver: org.postgresql.Driver
1. MS SQL Server
   - uses SQL Server 2016 (which is in beta)
   - requires Docker
   - can be run with sqlserver-docker-start.sh and stopped with sqlserver-docker-stop.sh
   - configuration
     * application-sqlserver.yaml
     * configures the JDBC driver for SQL Server
     * runs when --profile=sqlserver
   - To run
     * java -jar ./build/libs/SimpleMovieDB.jar --profile=sqlserver --tmdb.api.key=${TMDB_API_KEY}
   - Connect to the running database using a SQL database access tool such as [SQuirrel SQL](http://www.squirrelsql.org)
     * url: jdbc:sqlserver://localhost:1433
     * username: sa
     * password: yourStrong(!)Password
     * driver: com.microsoft.sqlserver.jdbc.SQLServerDriver