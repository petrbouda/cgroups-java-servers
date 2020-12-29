package pbouda.cgroups.mvc;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.testcontainers.containers.CockroachContainer;

import java.util.Map;

public class CockroachInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final CockroachContainer CONTAINER =
            new CockroachContainer("cockroachdb/cockroach")
                    .withInitScript("init.sql");

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        CONTAINER.start();

        System.out.printf("CockroachDB started! DB_PORT: %s, HTTP_PORT %s\n",
                CONTAINER.getMappedPort(26257),
                CONTAINER.getMappedPort(8080));

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();

        Map<String, Object> database = Map.of(
                "spring.datasource.url", CONTAINER.getJdbcUrl(),
                "spring.datasource.driverClassName", "org.postgresql.Driver",
                "spring.datasource.username", "root",
                "spring.datasource.password", "",
                "spring.jpa.database-platform", "org.hibernate.dialect.PostgreSQLDialect");

        propertySources.addFirst(new MapPropertySource("cockroach-map", database));
    }
}