package pbouda.cgroups.flux;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.client.SSLMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.netty.http.HttpResources;
import reactor.netty.resources.LoopResources;
import reactor.netty.tcp.TcpResources;

import java.time.Duration;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        TcpResources tcpResources = TcpResources.get();
        HttpResources.set((LoopResources) tcpResources);

        new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.REACTIVE)
                .bannerMode(Banner.Mode.OFF)
                .initializers(
                        // new CockroachInitializer(),
                        new RoutesInitializer()
                ).run(args);
    }

    @Bean
    public NettyServerCustomizer nettyServerCustomizer() {
        return server -> server.metrics(true, JfrHttpServerMetricsRecorder::new);
    }

    @Bean(destroyMethod = "dispose")
    public ConnectionPool connectionFactory(
            @Value("${database.host:localhost}") String host,
            @Value("${database.port:26257}") int port,
            @Value("${database.username:root}") String username,
            @Value("${database.password:}") String password,
            @Value("${database.name:postgres}") String name,
            @Value("${database.initConnections:5}") int initConnections,
            @Value("${database.maxConnections:10}") int maxConnections,
            @Value("${database.maxIdleInSec:60}") int maxIdleInSec,
            @Value("${database.registerJmx:false}") boolean registerJmx) {

        PostgresqlConnectionConfiguration configuration =
                PostgresqlConnectionConfiguration.builder()
                        .applicationName("testapp")
                        .sslMode(SSLMode.DISABLE)
                        .host(host)
                        .port(port)
                        .username(username)
                        .password(password)
                        .database(name)
                        .build();

        ConnectionPoolConfiguration poolConfiguration =
                ConnectionPoolConfiguration.builder(new PostgresqlConnectionFactory(configuration))
                        .maxIdleTime(Duration.ofSeconds(maxIdleInSec))
                        .initialSize(initConnections)
                        .maxSize(maxConnections)
                        .name("kyc")
                        .registerJmx(registerJmx)
                        .build();

        return new ConnectionPool(poolConfiguration);
    }

    @Bean
    public DatabaseClient databaseClient(ConnectionPool connectionPool) {
        return DatabaseClient.builder()
                .connectionFactory(connectionPool)
                .build();
    }
}