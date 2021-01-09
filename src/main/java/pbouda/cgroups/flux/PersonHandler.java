package pbouda.cgroups.flux;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Queue;

public class PersonHandler {

    private final Repository repository;
    private final Queue<String> queue;

    public PersonHandler(Repository repository, Queue<String> queue) {
        this.repository = repository;
        this.queue = queue;
    }

    public Mono<ServerResponse> single() {
        return repository.single(Helpers.generateId())
                .doOnNext(person -> queue.offer(person.getPoliticalOpinion()))
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
