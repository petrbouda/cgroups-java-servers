package pbouda.cgroups.flux;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

public class RoutesInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

    private static final BiFunction<? super Throwable, ServerRequest, Mono<ServerResponse>> ERROR_HANDLER =
            (req, resp) -> ServerResponse.status(INTERNAL_SERVER_ERROR)
                    .contentType(APPLICATION_JSON)
                    .build();

    private static RouterFunction<ServerResponse> persons(GenericApplicationContext ctx) {
        DatabaseClient databaseClient = ctx.getBean(DatabaseClient.class);
        PersonHandler personHandler = new PersonHandler(new Repository(databaseClient));

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
}
