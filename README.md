# CGROUPS with JAVA

#### Setup

- build the Docker Image: https://github.com/petrbouda/openjdk-x-dbg-asyncprofiler

```
// Build the project and create docker images
$ mvn clean package

$ docker run -it --cpus="1" --memory="700m" --memory-swap="700m" --network host -v /var/run/docker.sock:/var/run/docker.sock  jfr-test
```

#### Start Stressing

```
wrk -t2 -c10 -d10m -R200 http://localhost:8080/persons/single
```

#### Recordings

- open Java Mission Control -> File -> Connect -> `localhost` and `9090`
- record JFR

#### Start the Load!

