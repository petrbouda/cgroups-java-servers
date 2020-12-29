package pbouda.cgroups.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
//        application.addInitializers(new CockroachInitializer());
        application.run(args);
    }
}