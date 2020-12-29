package pbouda.cgroups.flux;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pbouda.cgroups.common.Helpers;
import reactor.core.publisher.Mono;

public class PersonHandler {

    private final Repository repository;

    public PersonHandler(Repository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> single() {
        return repository.single(Helpers.generateId())
                .flatMap(PersonHandler::ok);
    }

    public Mono<ServerResponse> multi(ServerRequest request) {
        int count = Integer.parseInt(request.pathVariable("count"));

        return repository.multi(Helpers.generateIds(count))
                .collectList()
                .flatMap(PersonHandler::ok);
    }

    private static Mono<ServerResponse> ok(Object response) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(response), Person.class);
    }
}
