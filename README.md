# CGROUPS with JAVA

#### Setup

- build the Docker Image: https://github.com/petrbouda/openjdk-x-dbg-asyncprofiler

```
// Build the project and create docker images
mvn clean package

docker run -it --rm --name app --cpus="1" --memory="700m" --memory-swap="700m" --network host -v /var/run/docker.sock:/var/run/docker.sock -v /tmp/asyncprofiler:/tmp/asyncprofiler --security-opt seccomp=unconfined  jfr-test
docker exec -ti app profiler.sh 60 -e cpu -f /tmp/asyncprofiler/cpu.svg 1
```

#### Start Stressing

https://github.com/giltene/wrk2

```
wrk -t2 -c10 -d10m -R100 http://localhost:8080/persons/single
```

#### Recordings

- open Java Mission Control -> File -> Connect -> `localhost` and `9090`
- record JFR

#### Start the Load!

