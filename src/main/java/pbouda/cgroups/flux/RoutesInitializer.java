package pbouda.cgroups.flux;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.function.BiFunction;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

public class RoutesInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

    private static final BiFunction<? super Throwable, ServerRequest, Mono<ServerResponse>> ERROR_HANDLER =
            (throwable, request) -> {
                throwable.printStackTrace();
                return ServerResponse.status(INTERNAL_SERVER_ERROR)
                        .contentType(APPLICATION_JSON)
                        .build();
            };

    private static RouterFunction<ServerResponse> persons(GenericApplicationContext ctx) {
        DatabaseClient databaseClient = ctx.getBean(DatabaseClient.class);

        Integer cacheSize = Integer.getInteger("cacheSize");
        // It's not properly synchronized but I don't care :)
        // I just need to keep some data in Old Generation
        Queue<String> queue = cacheSize == null
                ? NOOP_QUEUE
                : new CircularFifoQueue<>(cacheSize);

        System.out.println("Created a Person Cache with a size: " + cacheSize);
        PersonHandler personHandler = new PersonHandler(new Repository(databaseClient), queue);

        return RouterFunctions
                .nest(path("/persons"), RouterFunctions.route()
                        .GET("/single", req -> personHandler.single())
                        .GET("/multi/{count}", personHandler::multi)
                        .onError(Throwable.class, ERROR_HANDLER)
                        .build()
                );
    }

    @Override
    public void initialize(GenericApplicationContext ctx) {
        ctx.registerBean("persons", RouterFunction.class, () -> persons(ctx));
    }

    private static final Queue<String> NOOP_QUEUE = new Queue<>() {
        @Override
        public boolean add(String s) {
            return false;
        }

        @Override
        public boolean offer(String s) {
            return false;
        }

        @Override
        public String remove() {
            return null;
        }

        @Override
        public String poll() {
            return null;
        }

        @Override
        public String element() {
            return null;
        }

        @Override
        public String peek() {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<String> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends String> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {
        }
    };
}
