# CGROUPS with JAVA

#### Setup

```
// Build the project and create docker images
$ mvn clean package

// Start CockroachDB up
$ docker-compose up

// Import a schema and data
- project common and class Import 

// Start project
docker run -it --network host cgroups-java-flux:latest
- or
docker run -it --network host cgroups-java-mvc:latest
```

#### Recordings

- open Java Mission Control -> File -> Connect -> `localhost` and `9090`
- record JFR

#### Start the Load!

