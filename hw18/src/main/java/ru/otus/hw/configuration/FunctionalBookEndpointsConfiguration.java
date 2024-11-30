package ru.otus.hw.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.hw.handlers.BookHandler;
import ru.otus.hw.repositories.BookRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * Пример описания RouterFunction
 * <a href="https://habr.com/ru/articles/565056/">Реактивное программирование со Spring, часть 3 WebFlux</a>
 */
@Configuration
@RequiredArgsConstructor
public class FunctionalBookEndpointsConfiguration {

    @Bean
    //public RouterFunction<ServerResponse> composedBookRoutes(BookRepository repository, BookHandler handler) {
    public RouterFunction<ServerResponse> composedBookRoutes(BookRepository repository) {
        return route()
                .GET("/route/v1/book/{bookId}", accept(APPLICATION_JSON),
                        request -> repository.findById(request.pathVariable("bookId"))
                                .flatMap(b -> ok().contentType(APPLICATION_JSON).body(fromValue(b)))
                                .switchIfEmpty(notFound().build())
                )
//                .POST("/api/v1/book/route", accept(APPLICATION_JSON), handler::createBook)
//                .PUT("/api/v1/book/route", accept(APPLICATION_JSON), handler::updateBook)
//                .DELETE("/api/v1/book/route/{bookId}", accept(APPLICATION_JSON),
//                        request -> repository.findById(request.pathVariable("bookId"))
//                                .flatMap(b -> ServerResponse.noContent().build(repository.delete(b)))
//                                .switchIfEmpty(notFound().build())
//                )
                .build();
    }
}
