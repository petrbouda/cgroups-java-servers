package pbouda.cgroups.flux;

import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Function;

public class Repository {

    private static final String SINGLE_QUERY = """
            SELECT * FROM person WHERE id = :id
            """;

    private static final String MULTI_QUERY = """
            SELECT * FROM person WHERE id IN (:ids)
            """;
    private final DatabaseClient databaseClient;

    public Repository(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    public Mono<Person> single(long id) {
        return databaseClient.sql(SINGLE_QUERY)
                .bind("id", id)
                .fetch()
                .one()
                .as(params -> params.map(PERSON_MAPPER));
    }

    public Flux<Person> multi(Collection<Long> ids) {
        StringJoiner joiner = new StringJoiner(", ");
        for (long id : ids) {
            joiner.add(Long.toString(id));
        }

        return databaseClient.sql(MULTI_QUERY)
                .bind("ids", ids)
                .fetch()
                .all()
                .map(PERSON_MAPPER);
    }

    private static final Function<Map<String, Object>, Person> PERSON_MAPPER =
            params -> new Person(
                    (long) params.get("id"),
                    (String) params.get("firstname"),
                    (String) params.get("lastname"),
                    (String) params.get("city"),
                    (String) params.get("country"),
                    (String) params.get("phone"),
                    (String) params.get("political_opinion"));
}
